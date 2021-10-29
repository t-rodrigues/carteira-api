package dev.thiagorodrigues.carteira.main.security;

import dev.thiagorodrigues.carteira.domain.entities.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    public String generateJwtToken(Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();

        return Jwts.builder().setSubject(usuario.getId().toString()).signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserId(String token) {
        String subject = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();

        return Long.valueOf(subject);
    }

}
