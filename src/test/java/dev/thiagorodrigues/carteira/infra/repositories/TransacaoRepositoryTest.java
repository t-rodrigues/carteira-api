package dev.thiagorodrigues.carteira.infra.repositories;

import dev.thiagorodrigues.carteira.application.dtos.ItemCarteiraProjection;
import dev.thiagorodrigues.carteira.domain.entities.TipoTransacao;
import dev.thiagorodrigues.carteira.domain.entities.Transacao;
import dev.thiagorodrigues.carteira.domain.entities.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class TransacaoRepositoryTest {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        Usuario usuario = new Usuario("Rafaela", "rafaela@mail.com", "123123");
        testEntityManager.persist(usuario);

        Transacao t1 = new Transacao("ITSA4", BigDecimal.valueOf(10.00), 50, LocalDate.now(), TipoTransacao.COMPRA,
                usuario);
        testEntityManager.persist(t1);
        Transacao t2 = new Transacao("BBSE3", BigDecimal.valueOf(22.80), 80, LocalDate.now(), TipoTransacao.COMPRA,
                usuario);
        testEntityManager.persist(t2);
        Transacao t3 = new Transacao("EGIE3", BigDecimal.valueOf(40.00), 25, LocalDate.now(), TipoTransacao.COMPRA,
                usuario);
        testEntityManager.persist(t3);
        Transacao t4 = new Transacao("ITSA4", BigDecimal.valueOf(11.20), 40, LocalDate.now(), TipoTransacao.COMPRA,
                usuario);
        testEntityManager.persist(t4);
        Transacao t5 = new Transacao("SAPR4", BigDecimal.valueOf(4.02), 120, LocalDate.now(), TipoTransacao.COMPRA,
                usuario);
        testEntityManager.persist(t5);
    }

    @Test
    void deveriaRetornarRelatorioCarteiraDeInvestimentos() {
        var relatorio = transacaoRepository.relatorioCarteiraDeInvestimentos();

        Assertions.assertThat(relatorio).hasSize(4)
                .extracting(ItemCarteiraProjection::getTicker, ItemCarteiraProjection::getQuantidade,
                        ItemCarteiraProjection::getPercentual)
                .containsExactlyInAnyOrder(Assertions.tuple("ITSA4", 90L, BigDecimal.valueOf(0.28571)),
                        Assertions.tuple("BBSE3", 80L, BigDecimal.valueOf(0.25397)),
                        Assertions.tuple("EGIE3", 25L, BigDecimal.valueOf(0.07937)),
                        Assertions.tuple("SAPR4", 120L, BigDecimal.valueOf(0.38095)));
    }

}
