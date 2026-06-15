package com.condominio.condominio_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String type;
    private Long id;
    private String username;
    private String email;
    private String role;
}