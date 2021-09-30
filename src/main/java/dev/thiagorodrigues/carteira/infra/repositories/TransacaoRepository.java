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

    @Query("select t.ticker as ticker, sum(t.quantidade) as quantidade, "
            + "sum(t.quantidade) * 1.0 / (select sum(t2.quantidade) * 1.0 from Transacao t2) as percentual "
            + "from Transacao t group by t.ticker")
    List<ItemCarteiraProjection> relatorioCarteiraDeInvestimentos();

    @Query("select new dev.thiagorodrigues.carteira.application.dtos.ItemCarteiraDto(t.ticker, "
            + "sum(t.quantidade), sum(t.quantidade) * 1.0 / (select sum(t2.quantidade) * 1.0 from Transacao t2)) "
            + "from Transacao t group by t.ticker")
    List<ItemCarteiraDto> relatorioCarteiraDeInvestimentosDto();

}
