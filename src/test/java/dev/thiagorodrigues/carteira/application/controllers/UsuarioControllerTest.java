package dev.thiagorodrigues.carteira.application.controllers;

import dev.thiagorodrigues.carteira.domain.entities.Usuario;
import dev.thiagorodrigues.carteira.infra.mail.MailService;
import dev.thiagorodrigues.carteira.infra.repositories.PerfilRepository;
import dev.thiagorodrigues.carteira.infra.repositories.UsuarioRepository;
import dev.thiagorodrigues.carteira.main.security.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UsuarioControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @MockBean
    private MailService mailService;

    private String token;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(null, "John Doe", "admin@mail.com", "superSecretPass");
        var ademir = perfilRepository.getById(1L);
        usuario.adicionarPerfil(ademir);
        usuarioRepository.save(usuario);
        var authentication = new UsernamePasswordAuthenticationToken(usuario, usuario.getEmail());

        token = "Bearer " + jwtTokenUtil.generateJwtToken(authentication);
    }

    @Test
    void mostrarShouldReturnNotFoundWhenInvalidId() throws Exception {
        var nonExistingId = 100L;

        mvc.perform(get("/usuarios/{id}", nonExistingId).header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isNotFound());
    }

    @Test
    void mostrarShouldReturnUserWhenValidId() throws Exception {
        var existingId = usuario.getId();

        mvc.perform(get("/usuarios/{id}", existingId).header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(usuario.getId()));
    }

}
