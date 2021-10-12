package dev.thiagorodrigues.carteira.application.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import dev.thiagorodrigues.carteira.domain.entities.TipoTransacao;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class TransacaoResponseDto {

    private Long id;
    private String ticker;
    private BigDecimal preco;
    private Integer quantidade;

    @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate data;
    private TipoTransacao tipo;
    private UsuarioResponseDto usuario;

}
