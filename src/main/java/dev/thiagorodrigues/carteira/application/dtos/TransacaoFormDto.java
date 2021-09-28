package dev.thiagorodrigues.carteira.application.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import dev.thiagorodrigues.carteira.domain.entities.TipoTransacao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoFormDto {

    @NotBlank
    @Size(min = 5, max = 6)
    private String ticker;

    @Positive
    private BigDecimal preco;

    @Positive
    private Integer quantidade;

    @PastOrPresent
    private LocalDate data;

    @NotBlank
    private TipoTransacao tipo;

}
