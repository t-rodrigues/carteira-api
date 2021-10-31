package dev.thiagorodrigues.carteira.application.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.thiagorodrigues.carteira.domain.entities.Usuario;
import dev.thiagorodrigues.carteira.domain.mocks.UsuarioFactory;
import dev.thiagorodrigues.carteira.infra.repositories.PerfilRepository;
import dev.thiagorodrigues.carteira.infra.repositories.UsuarioRepository;
import dev.thiagorodrigues.carteira.main.security.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    void setUp() {
        var usuario = new Usuario(null, "John Doe", "admin@mail.com", "superSecretPass");
        var ademir = perfilRepository.getById(1L);
        usuario.adicionarPerfil(ademir);
        usuarioRepository.save(usuario);
        var authentication = new UsernamePasswordAuthenticationToken(usuario, usuario.getEmail());

        token = "Bearer " + jwtTokenUtil.generateJwtToken(authentication);
    }

    @Test
    void naoDeveriaCadastrarUsuarioComDadosIncompletos() throws Exception {
        String json = "{}";

        mvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON).content(json)
                .header(HttpHeaders.AUTHORIZATION, token)).andExpect(status().isBadRequest());
    }

    @Test
    void deveriaCadastrarUmUsuarioComDadosCompletos() throws Exception {
        String json = objectMapper.writeValueAsString(UsuarioFactory.criarUsuarioFormDto());
        var usuarioResponseDto = UsuarioFactory.criarUsuarioResponseDto();

        mvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON).content(json)
                .header(HttpHeaders.AUTHORIZATION, token)).andExpect(status().isCreated())
                .andExpect(header().exists("Location")).andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value(usuarioResponseDto.getNome()));
    }

}
