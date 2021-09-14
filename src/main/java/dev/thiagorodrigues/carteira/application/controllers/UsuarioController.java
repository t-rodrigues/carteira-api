package dev.thiagorodrigues.carteira.application.controllers;

import java.util.List;

import javax.validation.Valid;

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
    public List<UsuarioResponseDto> getUsuarios() {
        return usuarioService.getUsuarios();
    }

    @PostMapping
    public void addUsuario(@RequestBody @Valid UsuarioFormDto usuarioFormDto) {
        usuarioService.createUsuario(usuarioFormDto);
    }

}
