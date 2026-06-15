package com.condominio.condominio_api.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OcorrenciaResponseDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataOcorrencia;
    private String tipo;
    private String status;
    private String moradorNome;
}