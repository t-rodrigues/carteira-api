package dev.thiagorodrigues.carteira.domain.mocks;

import dev.thiagorodrigues.carteira.application.dtos.UsuarioFormDto;
import dev.thiagorodrigues.carteira.application.dtos.UsuarioUpdateFormDto;
import dev.thiagorodrigues.carteira.domain.entities.Usuario;
import org.modelmapper.ModelMapper;

public class UsuarioFactory {

    private static ModelMapper modelMapper = new ModelMapper();

    public static Usuario criarUsuario() {
        return new Usuario(1L, "John Doe", "john@mail.com", "123123");
    }

    public static Usuario criarUsuarioSemId() {
        return new Usuario(null, "John Doe", "john@mail.com", "123123");
    }

    public static UsuarioFormDto criarUsuarioFormDto() {
        return modelMapper.map(criarUsuario(), UsuarioFormDto.class);
    }

    public static UsuarioUpdateFormDto criarUsuarioUpdateFormComMesmoEmailDto() {
        var usuario = new Usuario(1L, "Updated John Doe", "john@mail.com", "123123");

        return modelMapper.map(usuario, UsuarioUpdateFormDto.class);
    }

    public static UsuarioUpdateFormDto criarUsuarioUpdateFormComEmailDiferenteDto() {
        var usuario = new Usuario(1L, "Updated John Doe", "updated@mail.com", "123123");

        return modelMapper.map(usuario, UsuarioUpdateFormDto.class);
    }

}