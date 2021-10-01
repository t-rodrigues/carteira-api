package dev.thiagorodrigues.carteira.application.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import dev.thiagorodrigues.carteira.domain.entities.TipoTransacao;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class TransacaoFormDto {

    @NotBlank
    @Size(min = 5, max = 6)
    @Pattern(regexp = "[a-zA-Z]{4}[0-9][0-9]?")
    private String ticker;

    @Positive
    private BigDecimal preco;

    @Positive
    private Integer quantidade;

    @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
    @PastOrPresent
    private LocalDate data;

    @NotNull
    private TipoTransacao tipo;

    @NotNull
    @JsonAlias("usuario_id")
    private Long usuarioId;

}
