package dev.thiagorodrigues.carteira.domain.services;

import dev.thiagorodrigues.carteira.domain.entities.TipoTransacao;
import dev.thiagorodrigues.carteira.domain.entities.Transacao;
import dev.thiagorodrigues.carteira.domain.entities.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculadorDeImpostoServiceTest {

    private CalculadorDeImpostoService calculadora;

    private Transacao createTransaction(BigDecimal preco, Integer quantidade, TipoTransacao tipo) {
        return new Transacao("ITSA4", preco, quantidade, LocalDate.now(), tipo, new Usuario());
    }

    @BeforeEach
    void setUp() {
        calculadora = new CalculadorDeImpostoService();
    }

    @Test
    void transacaoDoTipoCompraNaoDeveriaTerImposto() {
        Transacao transacao = createTransaction(BigDecimal.valueOf(30.0), 10, TipoTransacao.COMPRA);
        BigDecimal imposto = calculadora.calcular(transacao);

        assertEquals(BigDecimal.ZERO, imposto);
    }

    @Test
    void transacaoDoTipoVendaComValorMenorDoQueVinteMilNaoDeveriaTerImposto() {
        Transacao transacao = createTransaction(BigDecimal.valueOf(30.0), 10, TipoTransacao.VENDA);
        BigDecimal imposto = calculadora.calcular(transacao);

        assertEquals(BigDecimal.ZERO, imposto);
    }

    @Test
    void deveriaCalcularImpostDeTransacaoDoTipoVendaComValorMaiorQueVinteMil() {
        Transacao transacao = createTransaction(BigDecimal.valueOf(20), 1000, TipoTransacao.VENDA);
        BigDecimal imposto = calculadora.calcular(transacao);

        assertEquals(BigDecimal.valueOf(3000).setScale(2, RoundingMode.HALF_UP), imposto);
    }

}
