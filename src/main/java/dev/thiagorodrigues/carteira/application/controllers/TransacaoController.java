package dev.thiagorodrigues.carteira.application.controllers;

import java.util.List;

import javax.validation.Valid;

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
    public List<TransacaoResponseDto> getTransacoes() {
        return transacaoService.getTransacoes();
    }

    @PostMapping
    public void addTransacao(@RequestBody @Valid TransacaoFormDto transacaoFormDto) {
        transacaoService.createTransacao(transacaoFormDto);
    }

}
