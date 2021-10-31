package dev.thiagorodrigues.carteira.infra.repositories;

import dev.thiagorodrigues.carteira.domain.entities.Usuario;
import dev.thiagorodrigues.carteira.domain.mocks.UsuarioFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private Usuario usuario;

    @BeforeEach
    private void setUp() {
        usuario = UsuarioFactory.criarUsuarioSemId();

        testEntityManager.persist(usuario);
    }

    @Test
    void findByEmailDeveriaRetornarUmUsuarioValido() {
        var usuarioEncontrado = usuarioRepository.findByEmail(usuario.getEmail()).get();

        assertEquals(usuario.getId(), usuarioEncontrado.getId());
        assertEquals(usuario.getEmail(), usuarioEncontrado.getEmail());
        assertEquals(usuario.getNome(), usuarioEncontrado.getNome());
    }

    @Test
    void findByNaoDeveriaTerRetornoComEmailNaoCadastrado() {
        var usuarioEncontrado = usuarioRepository.findByEmail("any@mail.com");

        assertEquals(Optional.empty(), usuarioEncontrado);
    }

}
