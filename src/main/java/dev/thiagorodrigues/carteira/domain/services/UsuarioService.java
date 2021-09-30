package dev.thiagorodrigues.carteira.domain.services;

import dev.thiagorodrigues.carteira.application.dtos.UsuarioFormDto;
import dev.thiagorodrigues.carteira.application.dtos.UsuarioResponseDto;
import dev.thiagorodrigues.carteira.domain.entities.Usuario;
import dev.thiagorodrigues.carteira.infra.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Transactional(readOnly = true)
    public Page<UsuarioResponseDto> getUsuarios(Pageable paginacao) {
        Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);

        return usuarios.map(usuario -> modelMapper.map(usuario, UsuarioResponseDto.class));
    }

    @Transactional
    public void createUsuario(UsuarioFormDto usuarioFormDto) {
        Usuario novoUsuario = modelMapper.map(usuarioFormDto, Usuario.class);
        String senha = new Random().nextInt(99999) + "";
        novoUsuario.setSenha(senha);

        usuarioRepository.save(novoUsuario);
    }

}
