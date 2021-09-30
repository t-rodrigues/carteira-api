package dev.thiagorodrigues.carteira.application.controllers;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import dev.thiagorodrigues.carteira.application.dtos.UsuarioFormDto;
import dev.thiagorodrigues.carteira.application.dtos.UsuarioResponseDto;
import dev.thiagorodrigues.carteira.domain.services.UsuarioService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public Page<UsuarioResponseDto> getUsuarios(Pageable paginacao) {
        return usuarioService.getUsuarios(paginacao);
    }

    @PostMapping
    public void addUsuario(@RequestBody @Valid UsuarioFormDto usuarioFormDto) {
        usuarioService.createUsuario(usuarioFormDto);
    }

}
