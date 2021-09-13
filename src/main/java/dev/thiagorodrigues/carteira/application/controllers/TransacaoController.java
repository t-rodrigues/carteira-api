package dev.thiagorodrigues.carteira.application.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import dev.thiagorodrigues.carteira.application.dtos.TransacaoFormDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoResponseDto;
import dev.thiagorodrigues.carteira.domain.entities.Transacao;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private ModelMapper modelMapper = new ModelMapper();
    private List<Transacao> transacoes = new ArrayList<>();

    @GetMapping
    public List<TransacaoResponseDto> getTransacoes() {
        return transacoes.stream().map(t -> modelMapper.map(t, TransacaoResponseDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    public void addTransacao(@RequestBody TransacaoFormDto transacaoFormDto) {
        Transacao transacao = modelMapper.map(transacaoFormDto, Transacao.class);
        transacoes.add(transacao);
    }

}
