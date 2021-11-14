package dev.thiagorodrigues.carteira.application.controllers;

import dev.thiagorodrigues.carteira.application.dtos.UsuarioFormDto;
import dev.thiagorodrigues.carteira.application.dtos.UsuarioResponseDto;
import dev.thiagorodrigues.carteira.domain.services.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Api(tags = "Registrar")
@RestController
@RequiredArgsConstructor
public class RegistroController {

    private final UsuarioService usuarioService;

    @ApiOperation("Registrar nova conta")
    @PostMapping("/registrar")
    public ResponseEntity<UsuarioResponseDto> inscrever(@RequestBody UsuarioFormDto usuarioFormDto) {
        var usuarioResponseDto = this.usuarioService.criar(usuarioFormDto);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(usuarioResponseDto.getId()).toUri();

        return ResponseEntity.created(location).body(usuarioResponseDto);
    }

}
