package dev.thiagorodrigues.carteira.application.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import dev.thiagorodrigues.carteira.domain.entities.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoFormDto {

    @NotBlank
    @Size(min = 5, max = 6)
    @Pattern(regexp = "[a-zA-Z]{4}[0-9]{1,2}", message = "Ticker no formato inválido")
    private String ticker;

    @DecimalMin("0.01")
    private BigDecimal preco;

    @Positive
    private Integer quantidade;

    @PastOrPresent
    @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate data;

    @NotNull
    private TipoTransacao tipo;

    @NotNull
    @JsonAlias("usuario_id")
    private Long usuarioId;

}
