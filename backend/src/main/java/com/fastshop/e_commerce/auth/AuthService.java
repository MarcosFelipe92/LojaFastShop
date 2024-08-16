package com.fastshop.e_commerce.auth;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.fastshop.e_commerce.dtos.login.LoginRequestDTO;
import com.fastshop.e_commerce.dtos.login.LoginResponseDTO;
import com.fastshop.e_commerce.models.RoleBO;
import com.fastshop.e_commerce.models.UserBO;
import com.fastshop.e_commerce.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public LoginResponseDTO login(LoginRequestDTO request) {
        UserBO user = userRepository.findByEmail(request.getEmail()).get();

        if (user == null || !user.isLoginCorrect(request, passwordEncoder)) {
            throw new BadCredentialsException("User or password is invalid");
        }

        Instant now = Instant.now();
        Long expiresIn = 10800L;

        String scopes = user.getRoles()
                .stream()
                .map(RoleBO::getName)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponseDTO(jwtValue, expiresIn);
    }

    public boolean validateUserPermission(JwtAuthenticationToken token, Long userId) {
        UserBO requestUser = userRepository.findById(Long.parseLong(token.getName())).get();
        boolean isAdmin = requestUser.hasRole(RoleBO.getAdminRole());

        if (isAdmin || requestUser.getId().equals(userId)) {
            return true;
        }
        return false;
    }

}
