package dev.thiagorodrigues.carteira.application.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import dev.thiagorodrigues.carteira.domain.entities.Transacao;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private List<Transacao> transacoes = new ArrayList<>();

    @GetMapping
    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    @PostMapping
    public void addTransacao(@RequestBody Transacao transacao) {
        transacoes.add(transacao);
    }

}
