package dev.thiagorodrigues.carteira.application.controllers;

import dev.thiagorodrigues.carteira.application.dtos.UsuarioPutFormDto;
import dev.thiagorodrigues.carteira.application.dtos.UsuarioResponseDto;
import dev.thiagorodrigues.carteira.domain.services.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Usuários")
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @ApiOperation("Listar usuários")
    @GetMapping
    public Page<UsuarioResponseDto> listar(Pageable paginacao) {
        return usuarioService.listar(paginacao);
    }

    @ApiOperation("Detalhar usuário")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> mostrar(@PathVariable Long id) {
        var usuario = usuarioService.detalhar(id);

        return ResponseEntity.ok(usuario);
    }

    @ApiOperation("Deletar um usuário")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation("Atualizar perfis do usuário")
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarPerfis(@Valid @PathVariable Long id,
            @RequestBody UsuarioPutFormDto usuarioPutFormDto) {
        this.usuarioService.atualizarPerfis(id, usuarioPutFormDto);

        return ResponseEntity.noContent().build();
    }

}
