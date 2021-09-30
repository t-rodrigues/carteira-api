package dev.thiagorodrigues.carteira.application.controllers;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import dev.thiagorodrigues.carteira.application.dtos.TransacaoFormDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoResponseDto;
import dev.thiagorodrigues.carteira.domain.services.TransacaoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    @GetMapping
    public Page<TransacaoResponseDto> getTransacoes(@PageableDefault(size = 10) Pageable paginacao) {
        return transacaoService.getTransacoes(paginacao);
    }

    @PostMapping
    public void addTransacao(@RequestBody @Valid TransacaoFormDto transacaoFormDto) {
        transacaoService.createTransacao(transacaoFormDto);
    }

}
