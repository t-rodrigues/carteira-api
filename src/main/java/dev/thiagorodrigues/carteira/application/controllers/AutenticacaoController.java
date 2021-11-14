package dev.thiagorodrigues.carteira.application.controllers;

import dev.thiagorodrigues.carteira.application.dtos.AuthFormDto;
import dev.thiagorodrigues.carteira.application.dtos.TokenDto;
import dev.thiagorodrigues.carteira.domain.services.AutenticaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "Autenticação")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AutenticaoService autenticaoService;

    @ApiOperation("Autenticar usuario")
    @PostMapping
    public ResponseEntity<TokenDto> auth(@RequestBody @Valid AuthFormDto authFormDto) {
        var token = autenticaoService.autenticar(authFormDto);

        return ResponseEntity.ok(new TokenDto(token));
    }

}
