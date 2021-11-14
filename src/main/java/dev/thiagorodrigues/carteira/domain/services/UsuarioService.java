package dev.thiagorodrigues.carteira.domain.services;

import dev.thiagorodrigues.carteira.application.dtos.UsuarioFormDto;
import dev.thiagorodrigues.carteira.application.dtos.UsuarioPutFormDto;
import dev.thiagorodrigues.carteira.application.dtos.UsuarioResponseDto;
import dev.thiagorodrigues.carteira.application.exceptions.ResourceNotFoundException;
import dev.thiagorodrigues.carteira.domain.entities.Usuario;
import dev.thiagorodrigues.carteira.domain.exceptions.DomainException;
import dev.thiagorodrigues.carteira.infra.mail.MailService;
import dev.thiagorodrigues.carteira.infra.repositories.PerfilRepository;
import dev.thiagorodrigues.carteira.infra.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MailService mailService;

    @Transactional(readOnly = true)
    public Page<UsuarioResponseDto> listar(Pageable paginacao) {
        var usuarios = usuarioRepository.findAll(paginacao);

        return usuarios.map(usuario -> modelMapper.map(usuario, UsuarioResponseDto.class));
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDto detalhar(Long id) {
        var usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(""));

        return modelMapper.map(usuario, UsuarioResponseDto.class);
    }

    @Transactional
    public UsuarioResponseDto criar(UsuarioFormDto usuarioFormDto) {
        try {
            verificarEmailEmUso(usuarioFormDto.getEmail());

            var usuario = modelMapper.map(usuarioFormDto, Usuario.class);
            usuario.getPerfis().add(perfilRepository.getByNome("ROLE_USER"));
            usuario.setSenha(bCryptPasswordEncoder.encode(usuarioFormDto.getSenha()));
            usuarioRepository.save(usuario);

            var recipient = usuario.getEmail();
            var subject = "Carteira - Bem vindo(a)";
            var content = String.format("Olá, %s!\n\n" + "Cadastro realizado com sucesso!\n"
                    + "Para acessar sua conta no Sistema Carteira:"
                    + "\nLogin: %s\nSenha: informada no momento do cadastro.\n\n" + "Atenciosamente,\nCarteira.",
                    usuario.getNome(), usuario.getEmail());

            this.mailService.sendMail(recipient, subject, content);

            return modelMapper.map(usuario, UsuarioResponseDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new DomainException("Perfil inválido");
        }
    }

    @Transactional
    public UsuarioResponseDto atualizar(UsuarioFormDto usuarioFormDto, Usuario logado) {
        try {
            var usuario = usuarioRepository.getById(logado.getId());

            if (!usuarioFormDto.getEmail().equalsIgnoreCase(usuario.getEmail())) {
                verificarEmailEmUso(usuarioFormDto.getEmail());
            }

            usuario.atualizarInformacoes(usuarioFormDto.getNome(), usuarioFormDto.getEmail());
            usuarioRepository.save(usuario);

            return modelMapper.map(usuario, UsuarioResponseDto.class);
        } catch (EntityNotFoundException e) {
            throw getResourceNotFoundException();
        }
    }

    @Transactional
    public void atualizarPerfis(Long id, UsuarioPutFormDto usuarioPutFormDto) {
        var usuario = usuarioRepository.getById(id);
        var perfis = perfilRepository.findAllById(usuarioPutFormDto.getPerfis());

        usuario.getPerfis().clear();
        perfis.forEach(usuario::adicionarPerfil);

        usuarioRepository.save(usuario);
    }

    public void deletar(Long id) {
        try {
            usuarioRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw getResourceNotFoundException();
        } catch (DataIntegrityViolationException e) {
            throw new DomainException("Usuário não pode ser deletado");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    private void verificarEmailEmUso(String email) {
        var usuario = usuarioRepository.findByEmail(email);

        if (!ObjectUtils.isEmpty(usuario)) {
            throw new DomainException("E-mail já em uso por outro usuário");
        }
    }

    private ResourceNotFoundException getResourceNotFoundException() {
        return new ResourceNotFoundException("Usuário inexistente");
    }

}
