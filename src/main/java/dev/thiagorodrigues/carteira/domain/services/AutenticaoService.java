package dev.thiagorodrigues.carteira.domain.services;

import dev.thiagorodrigues.carteira.application.dtos.AuthFormDto;
import dev.thiagorodrigues.carteira.main.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticaoService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public String autenticar(AuthFormDto authFormDto) {
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authFormDto.getEmail(),
                authFormDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        return jwtTokenUtil.generateJwtToken(authentication);
    }

}
