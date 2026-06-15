package com.condominio.condominio_api.controller;

import com.condominio.condominio_api.dto.LoginRequestDTO;
import com.condominio.condominio_api.dto.LoginResponseDTO;
import com.condominio.condominio_api.model.Usuario;
import com.condominio.condominio_api.repository.UsuarioRepository;
import com.condominio.condominio_api.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(authentication);

        Usuario usuario = usuarioRepository.findByUsername(loginRequest.getUsername()).get();

        return ResponseEntity.ok(new LoginResponseDTO(
                jwt,
                "Bearer",
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getRole().toString()
        ));
    }
}