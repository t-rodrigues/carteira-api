package dev.thiagorodrigues.carteira.infra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.thiagorodrigues.carteira.domain.entities.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

}
