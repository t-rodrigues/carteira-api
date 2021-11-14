package dev.thiagorodrigues.carteira.domain.mocks;

import dev.thiagorodrigues.carteira.application.dtos.AuthFormDto;

public class AuthFactory {

    public static AuthFormDto createAuthFormDto() {
        return AuthFormDto.builder().email("john@mail.com").password("123123").build();
    }

}
