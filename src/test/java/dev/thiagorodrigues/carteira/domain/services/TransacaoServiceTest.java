package dev.thiagorodrigues.carteira.domain.services;

import dev.thiagorodrigues.carteira.application.dtos.TransacaoFormDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoResponseDto;
import dev.thiagorodrigues.carteira.domain.entities.TipoTransacao;
import dev.thiagorodrigues.carteira.infra.repositories.TransacaoRepository;
import dev.thiagorodrigues.carteira.infra.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private TransacaoService transacaoService;

    private TransacaoFormDto createTransacaoFormDto() {
        return new TransacaoFormDto("ITSA4", new BigDecimal(10), 10, LocalDate.now(), TipoTransacao.COMPRA, 1L);
    }

    @Test
    void deveriaCadastrarUmaTransacao() {
        TransacaoFormDto transacaoFormDto = createTransacaoFormDto();
        TransacaoResponseDto dto = transacaoService.createTransacao(transacaoFormDto);

        assertEquals(transacaoFormDto.getTicker(), dto.getTicker());
        assertEquals(transacaoFormDto.getPreco(), dto.getPreco());
        assertEquals(transacaoFormDto.getQuantidade(), dto.getQuantidade());
        assertEquals(transacaoFormDto.getTipo(), dto.getTipo());
        Mockito.verify(usuarioRepository, Mockito.times(1)).getById(Mockito.anyLong());
        Mockito.verify(transacaoRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void naoDeveriaCadastrarUmaTransacaoComUsuarioInexistente() {
        var transacaoFormDto = createTransacaoFormDto();
        Mockito.when(usuarioRepository.getById(transacaoFormDto.getUsuarioId()))
                .thenThrow(EntityNotFoundException.class);

        assertThrows(IllegalArgumentException.class, () -> {
            transacaoService.createTransacao(transacaoFormDto);
        });
    }

}
