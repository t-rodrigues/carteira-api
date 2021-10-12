package dev.thiagorodrigues.carteira.application.controllers;

import dev.thiagorodrigues.carteira.application.dtos.TransacaoFormDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoResponseDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoUpdateFormDto;
import dev.thiagorodrigues.carteira.domain.services.TransacaoService;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    @GetMapping
    public Page<TransacaoResponseDto> listar(@PageableDefault(size = 10) Pageable paginacao) {
        return transacaoService.getTransacoes(paginacao);
    }

    @GetMapping("/{id}")
    public TransacaoResponseDto mostrar(@PathVariable Long id) {
        return transacaoService.mostrar(id);
    }

    @PostMapping
    public ResponseEntity<TransacaoResponseDto> add(@RequestBody @Valid TransacaoFormDto transacaoFormDto,
            UriComponentsBuilder uriComponentsBuilder) {
        TransacaoResponseDto transacaoResponseDto = transacaoService.add(transacaoFormDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(transacaoResponseDto.getId()).toUri();

        return ResponseEntity.created(location).body(transacaoResponseDto);
    }

    @PutMapping
    public ResponseEntity<TransacaoResponseDto> atualizar(
            @RequestBody @Valid TransacaoUpdateFormDto transacaoUpdateFormDto) {
        var transacao = transacaoService.atualizar(transacaoUpdateFormDto);

        return ResponseEntity.ok(transacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        transacaoService.remover(id);

        return ResponseEntity.noContent().build();
    }

}
