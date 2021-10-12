package dev.thiagorodrigues.carteira.domain.services;

import dev.thiagorodrigues.carteira.application.dtos.UsuarioFormDto;
import dev.thiagorodrigues.carteira.application.dtos.UsuarioResponseDto;
import dev.thiagorodrigues.carteira.application.dtos.UsuarioUpdateFormDto;
import dev.thiagorodrigues.carteira.application.exceptions.ResourceNotFoundException;
import dev.thiagorodrigues.carteira.domain.entities.Usuario;
import dev.thiagorodrigues.carteira.domain.exceptions.DomainException;
import dev.thiagorodrigues.carteira.infra.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.util.Objects;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private ModelMapper modelMapper = new ModelMapper();

    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public Page<UsuarioResponseDto> listar(Pageable paginacao) {
        Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);

        return usuarios.map(usuario -> modelMapper.map(usuario, UsuarioResponseDto.class));
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDto detalhar(Long id) {
        try {
            var usuario = usuarioRepository.getById(id);

            return modelMapper.map(usuario, UsuarioResponseDto.class);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Usuário inexistente");
        }
    }

    @Transactional
    public UsuarioResponseDto criar(UsuarioFormDto usuarioFormDto) {
        verificarEmailEmUso(usuarioFormDto.getEmail());

        Usuario usuario = modelMapper.map(usuarioFormDto, Usuario.class);
        String senha = gerarSenha();
        usuario.setSenha(senha);
        usuarioRepository.save(usuario);

        return modelMapper.map(usuario, UsuarioResponseDto.class);
    }

    @Transactional
    public UsuarioResponseDto atualizar(UsuarioUpdateFormDto usuarioUpdateFormDto) {
        try {
            var usuario = usuarioRepository.getById(usuarioUpdateFormDto.getId());

            if (!usuarioUpdateFormDto.getEmail().equalsIgnoreCase(usuario.getEmail())) {
                verificarEmailEmUso(usuarioUpdateFormDto.getEmail());
            }

            usuario.atualizarInformacoes(usuarioUpdateFormDto.getNome(), usuarioUpdateFormDto.getEmail());
            usuarioRepository.save(usuario);

            return modelMapper.map(usuario, UsuarioResponseDto.class);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Usuário inexistente");
        }
    }

    public void deletar(Long id) {
        try {
            usuarioRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Usuário inexistente");
        } catch (DataIntegrityViolationException e) {
            throw new DomainException("Usuário não pode ser deletado");
        }
    }

    private void verificarEmailEmUso(String email) {
        var usuario = usuarioRepository.findByEmail(email);

        if (Objects.nonNull(usuario)) {
            throw new DomainException("E-mail já em uso por outro usuário");
        }
    }

    private String gerarSenha() {
        return new Random().nextInt(99999) + "";
    }

}
