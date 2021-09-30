package dev.thiagorodrigues.carteira.application.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import dev.thiagorodrigues.carteira.domain.entities.TipoTransacao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoResponseDto {

    private Long id;
    private String ticker;
    private BigDecimal preco;
    private Integer quantidade;
    private LocalDate data;
    private TipoTransacao tipo;
    private UsuarioResponseDto usuario;

}
