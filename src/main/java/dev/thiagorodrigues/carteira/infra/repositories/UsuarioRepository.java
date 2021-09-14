package dev.thiagorodrigues.carteira.infra.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import dev.thiagorodrigues.carteira.domain.entities.Usuario;

@Repository
public class UsuarioRepository {

    private List<Usuario> usuarios = new ArrayList<>();

    public List<Usuario> findAll() {
        return usuarios;
    }

    public Usuario save(Usuario usuario) {
        usuarios.add(usuario);

        return usuario;
    }

}
