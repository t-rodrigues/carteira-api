package dev.thiagorodrigues.carteira.domain.services;

import dev.thiagorodrigues.carteira.application.dtos.TransacaoDetalhadaResponseDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoFormDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoResponseDto;
import dev.thiagorodrigues.carteira.application.dtos.TransacaoUpdateFormDto;
import dev.thiagorodrigues.carteira.application.exceptions.ResourceNotFoundException;
import dev.thiagorodrigues.carteira.domain.entities.Transacao;
import dev.thiagorodrigues.carteira.domain.entities.Usuario;
import dev.thiagorodrigues.carteira.domain.exceptions.DomainException;
import dev.thiagorodrigues.carteira.infra.repositories.TransacaoRepository;
import dev.thiagorodrigues.carteira.infra.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ModelMapper modelMapper;
    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public Page<TransacaoResponseDto> listar(Pageable paginacao, Usuario usuarioLogado) {
        var transacoes = transacaoRepository.findAllByUsuario(paginacao, usuarioLogado);

        return transacoes.map(t -> modelMapper.map(t, TransacaoResponseDto.class));
    }

    @Transactional(readOnly = true)
    public TransacaoDetalhadaResponseDto mostrar(Long id, Usuario usuarioLogado) {
        var transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transacao não encontrada: " + id));

        if (!transacao.getUsuario().equals(usuarioLogado)) {
            throw getAccessDeniedException();
        }

        return modelMapper.map(transacao, TransacaoDetalhadaResponseDto.class);
    }

    @Transactional
    public TransacaoResponseDto add(TransacaoFormDto transacaoFormDto, Usuario usuarioLogado) {
        try {
            var transacao = modelMapper.map(transacaoFormDto, Transacao.class);
            var usuario = usuarioRepository.getById(transacaoFormDto.getUsuarioId());

            if (!usuario.equals(usuarioLogado)) {
                throw getAccessDeniedException();
            }

            transacao.setUsuario(usuario);
            transacaoRepository.save(transacao);

            return modelMapper.map(transacao, TransacaoResponseDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new DomainException("Usuario inválido");
        }
    }

    @Transactional
    public TransacaoResponseDto atualizar(TransacaoUpdateFormDto transacaoUpdateFormDto, Usuario userLogged) {
        try {
            var transacao = transacaoRepository.getById(transacaoUpdateFormDto.getId());

            if (!transacao.getUsuario().equals(userLogged)) {
                throw getAccessDeniedException();
            }

            transacao.atualizarInformacoes(transacaoUpdateFormDto.getTicker(), transacaoUpdateFormDto.getPreco(),
                    transacaoUpdateFormDto.getQuantidade(), transacaoUpdateFormDto.getTipo(),
                    transacaoUpdateFormDto.getData());
            transacaoRepository.save(transacao);

            return modelMapper.map(transacao, TransacaoResponseDto.class);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Transacao inexistente: " + transacaoUpdateFormDto.getId());
        }
    }

    @Transactional
    public void remover(Long id, Usuario usuario) {
        try {
            var transacao = transacaoRepository.getById(id);

            if (!transacao.getUsuario().equals(usuario)) {
                throw getAccessDeniedException();
            }

            transacaoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Transacao inexistente: " + id);
        }
    }

    private AccessDeniedException getAccessDeniedException() {
        return new AccessDeniedException("Acesso negado!");
    }

}
