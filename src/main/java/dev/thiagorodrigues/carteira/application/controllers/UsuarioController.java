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

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
@Api(tags = "Usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    @ApiOperation("Listar usuários")
    public Page<UsuarioResponseDto> listar(Pageable paginacao) {
        return usuarioService.listar(paginacao);
    }

    @GetMapping("/{id}")
    @ApiOperation("Detalhar usuário")
    public ResponseEntity<UsuarioResponseDto> mostrar(@PathVariable Long id) {
        var usuario = usuarioService.detalhar(id);

        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    @ApiOperation("Cadastrar novo usuário")
    public ResponseEntity<UsuarioResponseDto> criar(@RequestBody @Valid UsuarioFormDto usuarioFormDto,
            UriComponentsBuilder uriComponentsBuilder) {
        UsuarioResponseDto usuarioResponseDto = usuarioService.criar(usuarioFormDto);

        URI location = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuarioResponseDto.getId()).toUri();

        return ResponseEntity.created(location).body(usuarioResponseDto);
    }

    @PutMapping
    @ApiOperation("Atualizar os dados do usuário")
    public ResponseEntity<UsuarioResponseDto> atualizar(@RequestBody @Valid UsuarioUpdateFormDto usuarioUpdateFormDto) {
        UsuarioResponseDto usuarioResponseDto = usuarioService.atualizar(usuarioUpdateFormDto);

        return ResponseEntity.ok(usuarioResponseDto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Deletar um usuário")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);

        return ResponseEntity.noContent().build();
    }

}
