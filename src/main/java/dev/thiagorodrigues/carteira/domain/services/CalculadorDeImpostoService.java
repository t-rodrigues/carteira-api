package dev.thiagorodrigues.carteira.domain.services;

import dev.thiagorodrigues.carteira.domain.entities.TipoTransacao;
import dev.thiagorodrigues.carteira.domain.entities.Transacao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalculadorDeImpostoService {

    public BigDecimal calcular(Transacao transacao) {
        if (transacao.getTipo() == TipoTransacao.COMPRA) {
            return BigDecimal.ZERO;
        }

        BigDecimal valorTransacao = transacao.getPreco().multiply(BigDecimal.valueOf(transacao.getQuantidade()));

        if (valorTransacao.compareTo(BigDecimal.valueOf(20000)) < 0) {
            return BigDecimal.ZERO;
        }

        return valorTransacao.multiply(BigDecimal.valueOf(0.15)).setScale(2, RoundingMode.HALF_UP);
    }

}
