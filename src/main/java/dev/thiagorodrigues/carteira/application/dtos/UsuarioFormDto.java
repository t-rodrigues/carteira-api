package dev.thiagorodrigues.carteira.application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioFormDto {

    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @JsonProperty("perfil_id")
    private Long perfilId;

}
