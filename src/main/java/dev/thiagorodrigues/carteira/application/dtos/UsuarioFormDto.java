package dev.thiagorodrigues.carteira.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioFormDto {

    @NotBlank
    private String nome;

    @Email
    private String email;

}
