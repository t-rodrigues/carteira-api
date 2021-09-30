package dev.thiagorodrigues.carteira.application.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonAlias;

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

    @NotNull
    private TipoTransacao tipo;

    @NotNull
    @JsonAlias("usuario_id")
    private Long usuarioId;

}
