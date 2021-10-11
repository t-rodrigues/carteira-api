package dev.thiagorodrigues.carteira.infra.repositories;

import dev.thiagorodrigues.carteira.application.dtos.ItemCarteiraDto;
import dev.thiagorodrigues.carteira.application.dtos.ItemCarteiraProjection;
import dev.thiagorodrigues.carteira.domain.entities.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query("SELECT t.ticker as ticker, sum(case when t.tipo = 'COMPRA' then t.quantidade else -t.quantidade end) as quantidade, "
            + "sum(case when t.tipo = 'COMPRA' then t.quantidade else -t.quantidade end) * 1.0 / "
            + "(select sum(case when t2.tipo = 'COMPRA' then t2.quantidade else -t2.quantidade end) from Transacao t2) * 100.0 as percentual "
            + "FROM Transacao t group by t.ticker")
    List<ItemCarteiraProjection> relatorioCarteiraDeInvestimentos();

    @Query("SELECT new dev.thiagorodrigues.carteira.application.dtos.ItemCarteiraDto("
            + "t.ticker, sum(case when t.tipo = 'COMPRA' then t.quantidade else -t.quantidade end), "
            + "(select sum(case when t2.tipo = 'COMPRA' then t2.quantidade else -t2.quantidade end) from Transacao t2)) "
            + "FROM Transacao t group by t.ticker")
    List<ItemCarteiraDto> relatorioCarteiraDeInvestimentosDto();

}
