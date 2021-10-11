package dev.thiagorodrigues.carteira.application.dtos;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
public class ItemCarteiraDto {

    private String ticker;
    private Long quantidade;
    private BigDecimal percentual;

    public ItemCarteiraDto(String ticker, Long quantidade, Long quantidadeTotal) {
        this.ticker = ticker;
        this.quantidade = quantidade;
        this.percentual = BigDecimal.valueOf(quantidade)
                .divide(BigDecimal.valueOf(quantidadeTotal), 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100))
                .setScale(2);
    }

}
