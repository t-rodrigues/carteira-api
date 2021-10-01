package dev.thiagorodrigues.carteira.application.controllers;

import dev.thiagorodrigues.carteira.application.dtos.UsuarioFormDto;
import dev.thiagorodrigues.carteira.application.dtos.UsuarioResponseDto;
import dev.thiagorodrigues.carteira.domain.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

import java.net.URI;

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
    public ResponseEntity<UsuarioResponseDto> addUsuario(@RequestBody @Valid UsuarioFormDto usuarioFormDto,
            UriComponentsBuilder uriComponentsBuilder) {
        UsuarioResponseDto usuarioResponseDto = usuarioService.createUsuario(usuarioFormDto);

        URI location = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuarioResponseDto.getId()).toUri();

        return ResponseEntity.created(location).body(usuarioResponseDto);
    }

}
