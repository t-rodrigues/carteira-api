package dev.thiagorodrigues.carteira.application.dtos;

import java.math.BigDecimal;

public interface ItemCarteiraProjection {

    String getTicker();

    Long getQuantidade();

    BigDecimal getPercentual();

}
