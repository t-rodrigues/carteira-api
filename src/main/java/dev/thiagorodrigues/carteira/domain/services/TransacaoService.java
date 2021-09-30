package dev.thiagorodrigues.carteira.domain.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import dev.thiagorodrigues.carteira.application.dtos.TransacaoFormDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoResponseDto;
import dev.thiagorodrigues.carteira.domain.entities.Transacao;
import dev.thiagorodrigues.carteira.infra.repositories.TransacaoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    private ModelMapper modelMapper = new ModelMapper();

    public List<TransacaoResponseDto> getTransacoes() {
        List<Transacao> transacoes = transacaoRepository.findAll();

        return transacoes.stream().map(t -> modelMapper.map(t, TransacaoResponseDto.class))
                .collect(Collectors.toList());
    }

    public void createTransacao(TransacaoFormDto transacaoFormDto) {
        Transacao transacao = modelMapper.map(transacaoFormDto, Transacao.class);
        transacao.setId(null);

        transacaoRepository.save(transacao);
    }

}
