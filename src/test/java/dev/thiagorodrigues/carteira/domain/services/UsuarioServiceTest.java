package dev.thiagorodrigues.carteira.domain.services;

import dev.thiagorodrigues.carteira.application.dtos.UsuarioFormDto;
import dev.thiagorodrigues.carteira.application.dtos.UsuarioUpdateFormDto;
import dev.thiagorodrigues.carteira.application.exceptions.ResourceNotFoundException;
import dev.thiagorodrigues.carteira.domain.entities.Usuario;
import dev.thiagorodrigues.carteira.domain.exceptions.DomainException;
import dev.thiagorodrigues.carteira.domain.mocks.UsuarioFactory;
import dev.thiagorodrigues.carteira.infra.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario = UsuarioFactory.criarUsuario();
    private UsuarioFormDto usuarioFormDto = UsuarioFactory.criarUsuarioFormDto();
    private UsuarioUpdateFormDto usuarioUpdateFormComEmailDiferenteDto = UsuarioFactory
            .criarUsuarioUpdateFormComEmailDiferenteDto();
    private UsuarioUpdateFormDto usuarioUpdateFormComMesmoEmailDto = UsuarioFactory
            .criarUsuarioUpdateFormComMesmoEmailDto();

    @Test
    void detalharDeveRetornarUsuarioDetalhado() {
        long validId = 1L;
        when(usuarioRepository.getById(anyLong())).thenReturn(UsuarioFactory.criarUsuario());
        var usuarioResponseDto = usuarioService.detalhar(validId);

        assertEquals(validId, usuarioResponseDto.getId());
        verify(usuarioRepository, times(1)).getById(validId);
    }

    @Test
    void detalharDeveLancarResourceNotFoundExceptionQuandoIdInvalido() {
        doThrow(EntityNotFoundException.class).when(usuarioRepository).getById(anyLong());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.detalhar(1L));
    }

    @Test
    void criarDeveLancarDomainExceptionQuandoEmailJaEstiverRegistrado() {
        doThrow(DomainException.class).when(usuarioRepository).findByEmail(anyString());

        assertThrows(DomainException.class, () -> usuarioService.criar(usuarioFormDto));
    }

    @Test
    void criarDeveCriarUsuarioQuandoEmailNaoEstiverEmRegistrado() {
        var usuarioResponseDto = usuarioService.criar(usuarioFormDto);

        assertEquals(usuarioFormDto.getNome(), usuarioResponseDto.getNome());
        assertEquals(usuarioFormDto.getEmail(), usuarioResponseDto.getEmail());
        verify(usuarioRepository, times(1)).save(any());
    }

    @Test
    void atualizarDeveLancarResourceNotFoundExceptionQuandoIdInvalido() {
        doThrow(EntityNotFoundException.class).when(usuarioRepository).getById(anyLong());

        assertThrows(ResourceNotFoundException.class,
                () -> usuarioService.atualizar(usuarioUpdateFormComEmailDiferenteDto));
    }

    @Test
    void atualizarDeveLancarDomainExceptionQuandoUsuarioQuiserUtilizarEmailRegistrado() {
        when(usuarioRepository.getById(anyLong())).thenReturn(usuario);
        when(usuarioRepository.findByEmail(anyString())).thenReturn(usuario);

        assertThrows(DomainException.class, () -> usuarioService.atualizar(usuarioUpdateFormComEmailDiferenteDto));
        verify(usuarioRepository, times(0)).save(any());
    }

    @Test
    void atualizarDeveRetornarUsuarioAtualizadoComEmailDiferente() {
        when(usuarioRepository.getById(anyLong())).thenReturn(usuario);
        var usuarioAtualizado = usuarioService.atualizar(usuarioUpdateFormComEmailDiferenteDto);

        assertEquals(usuarioAtualizado.getEmail(), usuarioUpdateFormComEmailDiferenteDto.getEmail());
        verify(usuarioRepository, times(1)).save(any());
    }

    @Test
    void atualizarDeveRetornarUsuarioAtualizadoComMesmoEmail() {
        when(usuarioRepository.getById(anyLong())).thenReturn(usuario);
        var usuarioAtualizado = usuarioService.atualizar(usuarioUpdateFormComMesmoEmailDto);

        assertEquals(usuarioAtualizado.getNome(), usuarioUpdateFormComMesmoEmailDto.getNome());
        verify(usuarioRepository, times(1)).save(any());
    }

    @Test
    void deletarDeveLancarResourceNotFoundExceptionQuandoIdInvalido() {
        doThrow(EmptyResultDataAccessException.class).when(usuarioRepository).deleteById(1L);

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.deletar(1l));
        verify(usuarioRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deletarDeveLancarDomainExceptionQuandoUsuarioNaoPodeSerExcluido() {
        doThrow(DataIntegrityViolationException.class).when(usuarioRepository).deleteById(1L);

        assertThrows(DomainException.class, () -> usuarioService.deletar(1l));
        verify(usuarioRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deletarNaoDeveRetornarNadaQuandoIdExistir() {
        long validId = 1l;
        usuarioRepository.deleteById(validId);

        verify(usuarioRepository, times(1)).deleteById(validId);
    }

}
