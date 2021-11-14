package dev.thiagorodrigues.carteira.application.controllers;

import dev.thiagorodrigues.carteira.application.dtos.TransacaoDetalhadaResponseDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoFormDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoResponseDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoUpdateFormDto;
import dev.thiagorodrigues.carteira.domain.entities.Usuario;
import dev.thiagorodrigues.carteira.domain.services.TransacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@Api(tags = "Transações")
@RestController
@RequestMapping("/transacoes")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService transacaoService;

    @ApiOperation("Listar transações")
    @GetMapping
    public Page<TransacaoResponseDto> listar(@PageableDefault(size = 10) Pageable paginacao,
            @AuthenticationPrincipal Usuario logado) {
        return transacaoService.listar(paginacao, logado);
    }

    @ApiOperation("Mostrar transação completa")
    @GetMapping("/{id}")
    public TransacaoDetalhadaResponseDto mostrar(@PathVariable Long id, @AuthenticationPrincipal Usuario loggedUser) {
        return transacaoService.mostrar(id, loggedUser);
    }

    @ApiOperation("Cadastrar nova transação")
    @PostMapping
    public ResponseEntity<TransacaoResponseDto> add(@RequestBody @Valid TransacaoFormDto transacaoFormDto,
            UriComponentsBuilder uriComponentsBuilder, @AuthenticationPrincipal Usuario logado) {
        TransacaoResponseDto transacaoResponseDto = transacaoService.add(transacaoFormDto, logado);

        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(transacaoResponseDto.getId()).toUri();

        return ResponseEntity.created(location).body(transacaoResponseDto);
    }

    @ApiOperation("Atualizar")
    @PutMapping
    public ResponseEntity<TransacaoResponseDto> atualizar(@RequestBody TransacaoUpdateFormDto transacaoUpdateFormDto,
            @AuthenticationPrincipal Usuario logado) {
        var transacao = transacaoService.atualizar(transacaoUpdateFormDto, logado);

        return ResponseEntity.ok(transacao);
    }

    @ApiOperation("Deletar")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id, @AuthenticationPrincipal Usuario logado) {
        transacaoService.remover(id, logado);

        return ResponseEntity.noContent().build();
    }

}
