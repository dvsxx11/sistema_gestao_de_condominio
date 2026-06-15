package com.condominio.condominio_api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MoradorRequestDTO {
    @NotBlank
    private String nome;

    @Email
    private String email;

    private String telefone;
    private String celular;
    private String cpf;
    private String rg;
    private LocalDate dataNascimento;
    private String tipo;
    private Long unidadeId;
}