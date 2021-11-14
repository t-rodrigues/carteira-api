package dev.thiagorodrigues.carteira.application.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.thiagorodrigues.carteira.domain.entities.Usuario;
import dev.thiagorodrigues.carteira.domain.mocks.AuthFactory;
import dev.thiagorodrigues.carteira.domain.mocks.UsuarioFactory;
import dev.thiagorodrigues.carteira.infra.mail.MailService;
import dev.thiagorodrigues.carteira.infra.repositories.PerfilRepository;
import dev.thiagorodrigues.carteira.infra.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AutenticacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MailService mailService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = UsuarioFactory.criarUsuario();
        usuario.adicionarPerfil(perfilRepository.getByNome("ROLE_ADMIN"));
        usuarioRepository.save(usuario);
    }

    @Test
    void authShouldReturnBadRequestWhenInvalidDataWasProvided() throws Exception {
        var invalidData = "{}";

        mockMvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON).content(invalidData))
                .andExpect(status().isBadRequest());
    }

    @Test
    void authShouldReturnUnauthorizedWhenInvalidEmailOrPassword() throws Exception {
        var authFormDto = AuthFactory.createAuthFormDto();
        authFormDto.setPassword("invalid_password");
        var invalidData = objectMapper.writeValueAsString(authFormDto);

        mockMvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON).content(invalidData))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void authShouldReturnAccessTokenWhenValidData() throws Exception {
        var authFormDto = AuthFactory.createAuthFormDto();
        var validData = objectMapper.writeValueAsString(authFormDto);

        mockMvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON).content(validData))
                .andExpect(status().isOk()).andExpect(jsonPath("$.accessToken").exists());
    }

}
