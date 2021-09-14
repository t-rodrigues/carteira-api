package dev.thiagorodrigues.carteira.domain.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import dev.thiagorodrigues.carteira.application.dtos.UsuarioFormDto;
import dev.thiagorodrigues.carteira.application.dtos.UsuarioResponseDto;
import dev.thiagorodrigues.carteira.domain.entities.Usuario;

@Service
public class UsuarioService {

    private List<Usuario> usuarios = new ArrayList<>();
    private ModelMapper modelMapper = new ModelMapper();

    public List<UsuarioResponseDto> getUsuarios() {
        return usuarios.stream().map(usuario -> modelMapper.map(usuario, UsuarioResponseDto.class))
                .collect(Collectors.toList());
    }

    public void save(UsuarioFormDto usuarioFormDto) {
        Usuario novoUsuario = modelMapper.map(usuarioFormDto, Usuario.class);
        String senha = new Random().nextInt(99999) + "";
        novoUsuario.setSenha(senha);

        usuarios.add(novoUsuario);
    }

}
