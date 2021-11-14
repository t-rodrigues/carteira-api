package dev.thiagorodrigues.carteira.application.dtos;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class UsuarioPutFormDto {

    private Set<Long> perfis = new HashSet<>();

}
