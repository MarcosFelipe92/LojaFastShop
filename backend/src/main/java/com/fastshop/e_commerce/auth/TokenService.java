package com.fastshop.e_commerce.auth;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.fastshop.e_commerce.dtos.login.LoginRequestDTO;
import com.fastshop.e_commerce.dtos.login.LoginResponseDTO;
import com.fastshop.e_commerce.models.RoleBO;
import com.fastshop.e_commerce.models.UserBO;
import com.fastshop.e_commerce.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public LoginResponseDTO login(LoginRequestDTO request) {
        System.out.println("Chegou aqui 1");

        Optional<UserBO> user = userService.findByEmail(request.getEmail());

        if (user.isEmpty() || !user.get().isLoginCorrect(request, passwordEncoder)) {
            throw new BadCredentialsException("User or password is invalid");
        }

        Instant now = Instant.now();
        Long expiresIn = 10800L;

        String scopes = user.get().getRoles()
                .stream()
                .map(RoleBO::getName)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(user.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponseDTO(jwtValue, expiresIn);
    }
}
