package dev.thiagorodrigues.carteira.application.controllers;

import dev.thiagorodrigues.carteira.application.dtos.UsuarioFormDto;
import dev.thiagorodrigues.carteira.application.dtos.UsuarioResponseDto;
import dev.thiagorodrigues.carteira.domain.entities.Usuario;
import dev.thiagorodrigues.carteira.domain.services.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Perfil")
@RestController
@RequestMapping("/perfil")
@RequiredArgsConstructor
public class PerfilController {

    private final UsuarioService usuarioService;

    @ApiOperation("Detalhar usuário")
    @GetMapping
    public ResponseEntity<UsuarioResponseDto> mostrar(@AuthenticationPrincipal Usuario logado) {
        var usuario = usuarioService.detalhar(logado.getId());

        return ResponseEntity.ok(usuario);
    }

    @ApiOperation("Atualizar os dados")
    @PutMapping
    public ResponseEntity<UsuarioResponseDto> atualizar(@RequestBody @Valid UsuarioFormDto usuarioFormDto,
            @AuthenticationPrincipal Usuario logado) {
        UsuarioResponseDto usuarioResponseDto = usuarioService.atualizar(usuarioFormDto, logado);

        return ResponseEntity.ok(usuarioResponseDto);
    }

}
