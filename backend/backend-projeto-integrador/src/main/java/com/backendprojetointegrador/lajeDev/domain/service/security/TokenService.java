package com.backendprojetointegrador.lajeDev.domain.service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.backendprojetointegrador.lajeDev.domain.model.Usuario;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    public String generateToken(Usuario usuario) {
        return JWT.create()
                        .withIssuer("pi_carango")
                        .withSubject(usuario.getEmail())
                        .withClaim("id", usuario.getId())
                        .withExpiresAt(Date.from(LocalDateTime.now()
                                . plusMinutes(20)
                                .toInstant(ZoneOffset.of("-03:00"))))
                        .sign(Algorithm.HMAC256("qualquerStringMaisForteQueEsta"));

    }

    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256("qualquerStringMaisForteQueEsta"))
                .withIssuer("pi_carango")
                .build()
                    .verify(token)
                    .getSubject();
    }
}
