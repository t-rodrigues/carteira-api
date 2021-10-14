package dev.thiagorodrigues.carteira.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transacoes")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticker;
    private BigDecimal preco;
    private Integer quantidade;
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;

    @ManyToOne
    private Usuario usuario;

    public void atualizarInformacoes(String ticker, BigDecimal preco, Integer quantidade, TipoTransacao tipo,
            LocalDate data) {
        this.ticker = ticker;
        this.preco = preco;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.data = data;
    }

}
