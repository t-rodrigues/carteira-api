package dev.thiagorodrigues.carteira.infra.repositories;

import dev.thiagorodrigues.carteira.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    @Query("select u from Usuario u JOIN FETCH u.perfis where u.id = :userId")
    Optional<Usuario> findByIdWithProfile(Long userId);

}
