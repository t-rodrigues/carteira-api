package dev.thiagorodrigues.carteira.infra.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import dev.thiagorodrigues.carteira.domain.entities.Transacao;

@Repository
public class TransacaoRepository {

    private List<Transacao> transacoes = new ArrayList<>();

    public List<Transacao> findAll() {
        return transacoes;
    }

    public Transacao save(Transacao transacao) {
        transacoes.add(transacao);

        return transacao;
    }

}
