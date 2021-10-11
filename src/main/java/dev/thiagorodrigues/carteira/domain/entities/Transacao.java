package dev.thiagorodrigues.carteira.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
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

    public Transacao(String ticker, BigDecimal preco, Integer quantidade, LocalDate data, TipoTransacao tipo,
            Usuario usuario) {
        this.ticker = ticker;
        this.preco = preco;
        this.quantidade = quantidade;
        this.data = data;
        this.tipo = tipo;
        this.usuario = usuario;
    }

}
