package dev.thiagorodrigues.carteira.application.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UsuarioUpdateFormDto extends UsuarioFormDto {

    @NotNull
    private Long id;

}
