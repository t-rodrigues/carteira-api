package dev.thiagorodrigues.carteira.domain.services;

import dev.thiagorodrigues.carteira.application.dtos.TransacaoFormDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoResponseDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoUpdateFormDto;
import dev.thiagorodrigues.carteira.domain.entities.Transacao;
import dev.thiagorodrigues.carteira.infra.repositories.TransacaoRepository;
import dev.thiagorodrigues.carteira.infra.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final UsuarioRepository usuarioRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Transactional(readOnly = true)
    public Page<TransacaoResponseDto> getTransacoes(Pageable paginacao) {
        Page<Transacao> transacoes = transacaoRepository.findAll(paginacao);

        return transacoes.map(t -> modelMapper.map(t, TransacaoResponseDto.class));
    }

    @Transactional
    public TransacaoResponseDto createTransacao(TransacaoFormDto transacaoFormDto) {
        try {
            Transacao transacao = modelMapper.map(transacaoFormDto, Transacao.class);
            transacao.setId(null);
            transacao.setUsuario(usuarioRepository.getById(transacaoFormDto.getUsuarioId()));
            transacaoRepository.save(transacao);

            return modelMapper.map(transacao, TransacaoResponseDto.class);
        } catch (EntityNotFoundException e) {
            throw new IllegalArgumentException("Usuário inválido");
        }
    }

    @Transactional
    public TransacaoResponseDto atualizar(@Valid TransacaoUpdateFormDto transacaoUpdateFormDto) {
        var transacao = transacaoRepository.getById(transacaoUpdateFormDto.getId());

        transacao.atualizarInformacoes(transacaoUpdateFormDto.getTicker(), transacaoUpdateFormDto.getPreco(),
                transacaoUpdateFormDto.getQuantidade(), transacaoUpdateFormDto.getTipo(),
                transacaoUpdateFormDto.getData());
        transacaoRepository.save(transacao);

        return modelMapper.map(transacao, TransacaoResponseDto.class);
    }

}
