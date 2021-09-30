package dev.thiagorodrigues.carteira.domain.services;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public Page<TransacaoResponseDto> getTransacoes(Pageable paginacao) {
        Page<Transacao> transacoes = transacaoRepository.findAll(paginacao);

        return transacoes.map(t -> modelMapper.map(t, TransacaoResponseDto.class));
    }

    @Transactional
    public void createTransacao(TransacaoFormDto transacaoFormDto) {
        Transacao transacao = modelMapper.map(transacaoFormDto, Transacao.class);
        transacao.setId(null);

        transacaoRepository.save(transacao);
    }

}
