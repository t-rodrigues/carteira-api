package dev.thiagorodrigues.carteira.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import dev.thiagorodrigues.carteira.domain.entities.enums.TipoTransacao;

public class Transacao {

    private String ticker;
    private BigDecimal preco;
    private int quantidade;
    private LocalDate data;
    private TipoTransacao tipo;

    public Transacao() {
    }

    public Transacao(String ticker, BigDecimal preco, int quantidade, LocalDate data, TipoTransacao tipo) {
        this.ticker = ticker;
        this.preco = preco;
        this.quantidade = quantidade;
        this.data = data;
        this.tipo = tipo;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Transacao [data=" + data + ", preco=" + preco + ", quantidade=" + quantidade + ", ticker=" + ticker
                + ", tipo=" + tipo + "]";
    }

}
