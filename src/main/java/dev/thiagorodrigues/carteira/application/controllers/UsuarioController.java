package dev.thiagorodrigues.carteira.application.controllers;

import dev.thiagorodrigues.carteira.application.dtos.UsuarioFormDto;
import dev.thiagorodrigues.carteira.application.dtos.UsuarioResponseDto;
import dev.thiagorodrigues.carteira.application.dtos.UsuarioUpdateFormDto;
import dev.thiagorodrigues.carteira.domain.services.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

import java.net.URI;

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

    @ApiOperation("Cadastrar novo usuário")
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> criar(@RequestBody @Valid UsuarioFormDto usuarioFormDto,
            UriComponentsBuilder uriComponentsBuilder) {
        UsuarioResponseDto usuarioResponseDto = usuarioService.criar(usuarioFormDto);

        URI location = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuarioResponseDto.getId()).toUri();

        return ResponseEntity.created(location).body(usuarioResponseDto);
    }

    @ApiOperation("Atualizar os dados do usuário")
    @PutMapping
    public ResponseEntity<UsuarioResponseDto> atualizar(@RequestBody @Valid UsuarioUpdateFormDto usuarioUpdateFormDto) {
        UsuarioResponseDto usuarioResponseDto = usuarioService.atualizar(usuarioUpdateFormDto);

        return ResponseEntity.ok(usuarioResponseDto);
    }

    @ApiOperation("Deletar um usuário")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);

        return ResponseEntity.noContent().build();
    }

}
