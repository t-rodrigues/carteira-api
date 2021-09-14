package dev.thiagorodrigues.carteira.application.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioFormDto {

    @NotBlank
    private String nome;

    @Email
    private String email;

}
