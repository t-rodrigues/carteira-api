package dev.thiagorodrigues.carteira.application.dtos;

import lombok.Getter;

@Getter
public class TokenDto {

    private String accessToken;

    public TokenDto(String accessToken) {
        this.accessToken = accessToken;
    }

}
