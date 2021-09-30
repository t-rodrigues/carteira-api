package dev.thiagorodrigues.carteira.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemCarteiraDto {

    private String ticker;
    private Long quantidade;
    private Double percentual;

}
