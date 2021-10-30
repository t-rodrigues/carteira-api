package dev.thiagorodrigues.carteira.domain.mocks;

import dev.thiagorodrigues.carteira.application.dtos.TransacaoDetalhadaResponseDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoFormDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoResponseDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoUpdateFormDto;
import dev.thiagorodrigues.carteira.domain.entities.TipoTransacao;
import dev.thiagorodrigues.carteira.domain.entities.Transacao;
import dev.thiagorodrigues.carteira.domain.entities.Usuario;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransacaoFactory {

    private static ModelMapper modelMapper = new ModelMapper();

    public static Transacao criarTransacao() {
        return new Transacao(1L, "ITSA4", BigDecimal.valueOf(10.00), 10, LocalDate.now(), TipoTransacao.COMPRA,
                UsuarioFactory.criarUsuario());
    }

    public static Transacao criarTransacao(BigDecimal preco, Integer quantidade, TipoTransacao tipo) {
        return new Transacao(1L, "ITSA4", preco, quantidade, LocalDate.now(), tipo, UsuarioFactory.criarUsuario());
    }

    public static Transacao criarTransacao(String ticker, BigDecimal preco, Integer quantidade, TipoTransacao tipo,
            Usuario usuario) {
        return new Transacao(null, ticker, preco, quantidade, LocalDate.now(), tipo, usuario);
    }

    public static TransacaoFormDto criarTransacaoFormDto() {
        return modelMapper.map(criarTransacao(), TransacaoFormDto.class);
    }

    public static TransacaoResponseDto criarTransacaoResponseDto() {
        return modelMapper.map(criarTransacao(), TransacaoResponseDto.class);
    }

    public static TransacaoResponseDto criarTransacaoAtualizadaResponseDto() {
        return modelMapper.map(criarTransacaoUpdateFormDto(), TransacaoResponseDto.class);
    }

    public static TransacaoDetalhadaResponseDto criarTransacaoDetalhadaResponseDto() {
        return modelMapper.map(criarTransacao(), TransacaoDetalhadaResponseDto.class);
    }

    public static TransacaoUpdateFormDto criarTransacaoUpdateFormDto() {
        return TransacaoUpdateFormDto.builder().id(1L).ticker("XPTO1").preco(BigDecimal.valueOf(10.00)).quantidade(10)
                .data(LocalDate.now()).tipo(TipoTransacao.VENDA).build();
    }

    public static TransacaoUpdateFormDto criarTransacaoUpdateFormDtoComIdInvalido() {
        return TransacaoUpdateFormDto.builder().id(200L).build();
    }

}
