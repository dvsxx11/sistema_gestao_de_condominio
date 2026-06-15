package com.condominio.condominio_api.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EncomendaResponseDTO {
    private Long id;
    private String codigoRastreio;
    private String transportadora;
    private LocalDateTime dataRecebimento;
    private LocalDateTime dataRetirada;
    private String status;
    private String observacao;
    private String moradorNome;
    private String unidadeNumero;
}