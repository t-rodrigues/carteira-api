package dev.thiagorodrigues.carteira.domain.services;

import dev.thiagorodrigues.carteira.domain.entities.TipoTransacao;
import dev.thiagorodrigues.carteira.domain.entities.Transacao;
import dev.thiagorodrigues.carteira.domain.mocks.TransacaoFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculadorDeImpostoServiceTest {

    private CalculadorDeImpostoService calculadora;

    @BeforeEach
    void setUp() {
        calculadora = new CalculadorDeImpostoService();
    }

    @Test
    void transacaoDoTipoCompraNaoDeveriaTerImposto() {
        Transacao transacao = TransacaoFactory.criarTransacao(BigDecimal.valueOf(30.0), 10, TipoTransacao.COMPRA);
        BigDecimal imposto = calculadora.calcular(transacao);

        assertEquals(BigDecimal.ZERO, imposto);
    }

    @Test
    void transacaoDoTipoVendaComValorMenorDoQueVinteMilNaoDeveriaTerImposto() {
        Transacao transacao = TransacaoFactory.criarTransacao(BigDecimal.valueOf(30.0), 10, TipoTransacao.VENDA);
        BigDecimal imposto = calculadora.calcular(transacao);

        assertEquals(BigDecimal.ZERO, imposto);
    }

    @Test
    void deveriaCalcularImpostDeTransacaoDoTipoVendaComValorMaiorQueVinteMil() {
        Transacao transacao = TransacaoFactory.criarTransacao(BigDecimal.valueOf(20), 1000, TipoTransacao.VENDA);
        BigDecimal imposto = calculadora.calcular(transacao);

        assertEquals(BigDecimal.valueOf(3000).setScale(2, RoundingMode.HALF_UP), imposto);
    }

}
