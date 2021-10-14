package dev.thiagorodrigues.carteira.domain.services;

import dev.thiagorodrigues.carteira.application.dtos.TransacaoFormDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoUpdateFormDto;
import dev.thiagorodrigues.carteira.application.exceptions.ResourceNotFoundException;
import dev.thiagorodrigues.carteira.domain.entities.Transacao;
import dev.thiagorodrigues.carteira.domain.exceptions.DomainException;
import dev.thiagorodrigues.carteira.domain.mocks.TransacaoFactory;
import dev.thiagorodrigues.carteira.infra.repositories.TransacaoRepository;
import dev.thiagorodrigues.carteira.infra.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.EntityNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private TransacaoService transacaoService;

    private Transacao transacao = TransacaoFactory.criarTransacao();
    private TransacaoFormDto transacaoFormDto = TransacaoFactory.criarTransacaoFormDto();
    private TransacaoUpdateFormDto transacaoUpdateFormDto = TransacaoFactory.criarTransacaoUpdateFormDtoComIdInvalido();

    @Test
    void cadastrarDeveLancarResourceNotFoundExceptionQuandoUsuarioIdInvalido() {
        when(usuarioRepository.getById(transacaoFormDto.getUsuarioId())).thenThrow(EntityNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> {
            transacaoService.add(transacaoFormDto);
        });
    }

    @Test
    void cadastrarDeveLancarDomainExceptionQuandoUsuarioIdInvalido() {
        when(transacaoRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DomainException.class, () -> {
            transacaoService.add(transacaoFormDto);
        });
    }

    @Test
    void criarDeveRetornarUmaTransacaoQuandoDadosValidos() {
        var dto = transacaoService.add(transacaoFormDto);

        assertEquals(transacaoFormDto.getTicker(), dto.getTicker());
        assertEquals(transacaoFormDto.getPreco(), dto.getPreco());
        assertEquals(transacaoFormDto.getQuantidade(), dto.getQuantidade());
        assertEquals(transacaoFormDto.getTipo(), dto.getTipo());
        verify(usuarioRepository, times(1)).getById(anyLong());
        verify(transacaoRepository, times(1)).save(any());
    }

    @Test
    void mostrarDeveLancarResouceNotFoundQuandoIdTransacaoInvalido() {
        assertThrows(ResourceNotFoundException.class, () -> transacaoService.mostrar(1l));
    }

    @Test
    void mostrarDeveRetornarTransacaoQuandoIdValido() {
        when(transacaoRepository.findById(anyLong())).thenReturn(Optional.of(transacao));
        var transacaoResponseDto = transacaoService.mostrar(1l);

        assertEquals(transacao.getId(), transacaoResponseDto.getId());
        assertEquals(transacao.getTicker(), transacaoResponseDto.getTicker());
        assertEquals(transacao.getQuantidade(), transacaoResponseDto.getQuantidade());
        assertEquals(transacao.getPreco(), transacaoResponseDto.getPreco());
        verify(transacaoRepository, times(1)).findById(anyLong());
    }

    @Test
    void atualizarDeveLancarResourceNotFoundQuandoTransacaoIdInvalido() {
        when(transacaoRepository.getById(anyLong())).thenThrow(EntityNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> transacaoService.atualizar(transacaoUpdateFormDto));
    }

    @Test
    void atualizarDeveRetornarTransacaoAtualizada() {
        when(transacaoRepository.getById(transacaoUpdateFormDto.getId())).thenReturn(transacao);
        var transacaoResponseDto = transacaoService.atualizar(transacaoUpdateFormDto);

        assertEquals(transacaoUpdateFormDto.getTicker(), transacaoResponseDto.getTicker());
        assertEquals(transacaoUpdateFormDto.getTipo(), transacaoResponseDto.getTipo());
        verify(transacaoRepository, times(1)).save(any());
    }

    @Test
    void removerDeveriaLancarResourceNotFoundQuandoIdInvalido() {
        doThrow(EmptyResultDataAccessException.class).when(transacaoRepository).deleteById(anyLong());

        assertThrows(ResourceNotFoundException.class, () -> transacaoService.remover(100L));
    }

    @Test
    void removerNaoDeveTerRetornoComIdValido() {
        var validId = 1l;

        transacaoService.remover(validId);

        verify(transacaoRepository, times(1)).deleteById(1l);
    }

}
