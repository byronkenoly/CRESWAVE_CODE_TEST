package com.creswave.blog.Service;

import com.creswave.blog.DTO.AuthRequest;
import com.creswave.blog.DTO.AuthResponse;
import com.creswave.blog.DTO.RegisterRequest;
import com.creswave.blog.Entity.Role;
import com.creswave.blog.Entity.User;
import com.creswave.blog.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest registerRequest){
        var user = User
                .builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.REGULAR)
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder().token(jwtToken).build();
    }

    public AuthResponse authenticate(AuthRequest authRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(authRequest.getEmail()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder().token(jwtToken).build();
    }
}
