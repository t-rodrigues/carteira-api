package dev.thiagorodrigues.carteira.infra.repositories;

import dev.thiagorodrigues.carteira.domain.entities.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

}
