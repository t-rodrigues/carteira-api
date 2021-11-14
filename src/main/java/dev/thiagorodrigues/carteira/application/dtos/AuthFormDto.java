package dev.thiagorodrigues.carteira.application.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class AuthFormDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

}
