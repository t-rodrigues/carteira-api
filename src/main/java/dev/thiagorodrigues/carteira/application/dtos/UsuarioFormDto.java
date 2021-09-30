package dev.thiagorodrigues.carteira.application.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioFormDto {

    @NotBlank
    private String nome;

    @Email
    private String email;

}
