package dev.thiagorodrigues.carteira.domain.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import dev.thiagorodrigues.carteira.application.dtos.TransacaoFormDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoResponseDto;
import dev.thiagorodrigues.carteira.domain.entities.Transacao;

public class TransacaoService {

    private ModelMapper modelMapper = new ModelMapper();
    private List<Transacao> transacoes = new ArrayList<>();

    public List<TransacaoResponseDto> getTransacoes() {
        return transacoes.stream().map(t -> modelMapper.map(t, TransacaoResponseDto.class))
                .collect(Collectors.toList());
    }

    public void createTransacao(TransacaoFormDto transacaoFormDto) {
        Transacao transacao = modelMapper.map(transacaoFormDto, Transacao.class);

        transacoes.add(transacao);
    }

}
