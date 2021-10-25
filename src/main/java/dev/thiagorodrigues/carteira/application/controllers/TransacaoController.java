package dev.thiagorodrigues.carteira.application.controllers;

import dev.thiagorodrigues.carteira.application.dtos.TransacaoDetalhadaResponseDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoFormDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoResponseDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoUpdateFormDto;
import dev.thiagorodrigues.carteira.domain.services.TransacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

import java.net.URI;

@Api(tags = "Transações")
@RestController
@RequestMapping("/transacoes")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService transacaoService;

    @ApiOperation("Listar transações")
    @GetMapping
    public Page<TransacaoResponseDto> listar(@PageableDefault(size = 10) Pageable paginacao) {
        return transacaoService.listar(paginacao);
    }

    @ApiOperation("Mostrar transação completa")
    @GetMapping("/{id}")
    public TransacaoDetalhadaResponseDto mostrar(@PathVariable Long id) {
        return transacaoService.mostrar(id);
    }

    @ApiOperation("Cadastrar nova transação")
    @PostMapping
    public ResponseEntity<TransacaoResponseDto> add(@RequestBody @Valid TransacaoFormDto transacaoFormDto,
            UriComponentsBuilder uriComponentsBuilder) {
        TransacaoResponseDto transacaoResponseDto = transacaoService.add(transacaoFormDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(transacaoResponseDto.getId()).toUri();

        return ResponseEntity.created(location).body(transacaoResponseDto);
    }

    @ApiOperation("Atualizar")
    @PutMapping
    public ResponseEntity<TransacaoResponseDto> atualizar(
            @RequestBody @Valid TransacaoUpdateFormDto transacaoUpdateFormDto) {
        var transacao = transacaoService.atualizar(transacaoUpdateFormDto);

        return ResponseEntity.ok(transacao);
    }

    @ApiOperation("Deletar")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        transacaoService.remover(id);

        return ResponseEntity.noContent().build();
    }

}
