package dev.thiagorodrigues.carteira.application.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import dev.thiagorodrigues.carteira.domain.entities.enums.TipoTransacao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoFormDto {

    private String ticker;
    private BigDecimal preco;
    private int quantidade;
    private LocalDate data;
    private TipoTransacao tipo;

}
