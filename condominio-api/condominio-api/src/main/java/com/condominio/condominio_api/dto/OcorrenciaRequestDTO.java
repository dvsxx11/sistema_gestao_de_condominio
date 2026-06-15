package com.condominio.condominio_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OcorrenciaRequestDTO {
    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    private LocalDateTime dataOcorrencia;

    private String tipo;
}