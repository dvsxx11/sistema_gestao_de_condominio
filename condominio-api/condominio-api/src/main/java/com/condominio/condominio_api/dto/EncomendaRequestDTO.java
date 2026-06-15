package com.condominio.condominio_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EncomendaRequestDTO {
    @NotBlank
    private String codigoRastreio;

    private String transportadora;

    private String observacao;

    @NotNull
    private Long moradorId;
}