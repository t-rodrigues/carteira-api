package dev.thiagorodrigues.carteira.domain.services;

import dev.thiagorodrigues.carteira.application.dtos.UsuarioFormDto;
import dev.thiagorodrigues.carteira.application.dtos.UsuarioResponseDto;
import dev.thiagorodrigues.carteira.application.dtos.UsuarioUpdateFormDto;
import dev.thiagorodrigues.carteira.application.exceptions.ResourceNotFoundException;
import dev.thiagorodrigues.carteira.domain.entities.Usuario;
import dev.thiagorodrigues.carteira.domain.exceptions.DomainException;
import dev.thiagorodrigues.carteira.domain.mocks.UsuarioFactory;
import dev.thiagorodrigues.carteira.infra.mail.MailService;
import dev.thiagorodrigues.carteira.infra.repositories.PerfilRepository;
import dev.thiagorodrigues.carteira.infra.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

    @Mock
    private PerfilRepository perfilRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private MailService mailService;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private UsuarioFormDto usuarioFormDto;
    private UsuarioUpdateFormDto usuarioUpdateFormComEmailDiferenteDto;
    private UsuarioUpdateFormDto usuarioUpdateFormComMesmoEmailDto;
    private UsuarioResponseDto usuarioResponseDto;
    private UsuarioResponseDto usuarioAtualizadoResponseDto;
    private UsuarioResponseDto usuarioAtualizadoEmailDiferenteResponseDto;

    @BeforeEach
    void setUp() {
        usuario = UsuarioFactory.criarUsuario();
        usuarioFormDto = UsuarioFactory.criarUsuarioFormDto();
        usuarioUpdateFormComEmailDiferenteDto = UsuarioFactory.criarUsuarioUpdateFormComEmailDiferenteDto();
        usuarioUpdateFormComMesmoEmailDto = UsuarioFactory.criarUsuarioUpdateFormComMesmoEmailDto();
        usuarioResponseDto = UsuarioFactory.criarUsuarioResponseDto();
        usuarioAtualizadoResponseDto = UsuarioFactory.criarUsuarioAtualizadoComMesmoEmailResponseDto();
        usuarioAtualizadoEmailDiferenteResponseDto = UsuarioFactory
                .criarUsuarioAtualizadoComEmailDiferenteResponseDto();
    }

    @Test
    void detalharDeveriaLancarResourceNotFoundExceptionQuandoIdInvalido() {
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.detalhar(1L));
    }

    @Test
    void detalharShouldReturnUserWhenValidId() {
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(modelMapper.map(usuario, UsuarioResponseDto.class)).thenReturn(usuarioResponseDto);

        var usuarioResponseDto = usuarioService.detalhar(usuario.getId());

        assertEquals(usuario.getId(), usuarioResponseDto.getId());
    }

    @Test
    void criarDeveriaLancarDomainExceptionQuandoEmailJaEstiverRegistrado() {
        doThrow(DomainException.class).when(usuarioRepository).findByEmail(anyString());

        assertThrows(DomainException.class, () -> usuarioService.criar(usuarioFormDto));
    }

    @Test
    void criarDeveriaRetornarUsuarioQuandoEmailNaoEstiverRegistrado() {
        when(usuarioRepository.findByEmail(usuarioFormDto.getEmail())).thenReturn(Optional.empty());
        when(modelMapper.map(usuarioFormDto, Usuario.class)).thenReturn(usuario);
        when(modelMapper.map(usuario, UsuarioResponseDto.class)).thenReturn(usuarioResponseDto);

        var usuarioResponseDto = usuarioService.criar(usuarioFormDto);

        assertEquals(usuarioFormDto.getNome(), usuarioResponseDto.getNome());
        assertEquals(usuarioFormDto.getEmail(), usuarioResponseDto.getEmail());
        verify(usuarioRepository, times(1)).save(any());
    }

    @Test
    void atualizarDeveriaLancarResourceNotFoundExceptionQuandoIdInvalido() {
        doThrow(EntityNotFoundException.class).when(usuarioRepository).getById(any());

        assertThrows(ResourceNotFoundException.class,
                () -> usuarioService.atualizar(usuarioUpdateFormComEmailDiferenteDto, usuario));
    }

    @Test
    void atualizarDeveriaLancarDomainExceptionQuandoUsuarioQuiserUtilizarEmailRegistrado() {
        when(usuarioRepository.getById(anyLong())).thenReturn(usuario);
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuario));

        assertThrows(DomainException.class,
                () -> usuarioService.atualizar(usuarioUpdateFormComEmailDiferenteDto, usuario));
        verify(usuarioRepository, times(0)).save(any());
    }

    @Test
    void atualizarDeveriaRetornarUsuarioAtualizadoComEmailDiferente() {
        when(usuarioRepository.getById(anyLong())).thenReturn(usuario);
        when(modelMapper.map(usuario, UsuarioResponseDto.class)).thenReturn(usuarioAtualizadoEmailDiferenteResponseDto);

        var usuarioAtualizado = usuarioService.atualizar(usuarioUpdateFormComEmailDiferenteDto, usuario);

        assertEquals(usuarioAtualizado.getEmail(), usuarioUpdateFormComEmailDiferenteDto.getEmail());
        verify(usuarioRepository, times(1)).save(any());
    }

    @Test
    void atualizarDeveRetornarUsuarioAtualizadoComMesmoEmail() {
        when(usuarioRepository.getById(anyLong())).thenReturn(usuario);
        when(modelMapper.map(usuario, UsuarioResponseDto.class)).thenReturn(usuarioAtualizadoResponseDto);

        var usuarioAtualizado = usuarioService.atualizar(usuarioUpdateFormComMesmoEmailDto, usuario);

        assertEquals(usuarioAtualizado.getNome(), usuarioUpdateFormComMesmoEmailDto.getNome());
        verify(usuarioRepository, times(1)).save(any());
    }

    @Test
    void deletarDeveriaLancarResourceNotFoundExceptionQuandoIdInvalido() {
        doThrow(EmptyResultDataAccessException.class).when(usuarioRepository).deleteById(1L);

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.deletar(1l));
        verify(usuarioRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deletarDeveriaLancarDomainExceptionQuandoUsuarioNaoPodeSerExcluido() {
        doThrow(DataIntegrityViolationException.class).when(usuarioRepository).deleteById(1L);

        assertThrows(DomainException.class, () -> usuarioService.deletar(1l));
        verify(usuarioRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deletarNaoDeveriaTerRetornarQuandoIdExistir() {
        long validId = 1l;

        assertDoesNotThrow(() -> usuarioRepository.deleteById(validId));

        verify(usuarioRepository, times(1)).deleteById(validId);
    }

}
