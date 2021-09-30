package dev.thiagorodrigues.carteira.infra.repositories;

import dev.thiagorodrigues.carteira.domain.entities.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

}
