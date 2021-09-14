package dev.thiagorodrigues.carteira.application.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import dev.thiagorodrigues.carteira.application.dtos.UsuarioFormDto;
import dev.thiagorodrigues.carteira.application.dtos.UsuarioResponseDto;
import dev.thiagorodrigues.carteira.domain.entities.Usuario;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private List<Usuario> usuarios = new ArrayList<>();
    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping
    public List<UsuarioResponseDto> getUsuarios() {
        return usuarios.stream().map(usuario -> modelMapper.map(usuario, UsuarioResponseDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    public void addUsuario(@RequestBody @Valid UsuarioFormDto usuarioFormDto) {
        usuarios.add(modelMapper.map(usuarioFormDto, Usuario.class));
    }

}
