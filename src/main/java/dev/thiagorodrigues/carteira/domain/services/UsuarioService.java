package dev.thiagorodrigues.carteira.domain.services;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import dev.thiagorodrigues.carteira.application.dtos.UsuarioFormDto;
import dev.thiagorodrigues.carteira.application.dtos.UsuarioResponseDto;
import dev.thiagorodrigues.carteira.domain.entities.Usuario;
import dev.thiagorodrigues.carteira.infra.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private ModelMapper modelMapper = new ModelMapper();

    public List<UsuarioResponseDto> getUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream().map(usuario -> modelMapper.map(usuario, UsuarioResponseDto.class))
                .collect(Collectors.toList());
    }

    public void createUsuario(UsuarioFormDto usuarioFormDto) {
        Usuario novoUsuario = modelMapper.map(usuarioFormDto, Usuario.class);
        String senha = new Random().nextInt(99999) + "";
        novoUsuario.setSenha(senha);

        usuarioRepository.save(novoUsuario);
    }

}
