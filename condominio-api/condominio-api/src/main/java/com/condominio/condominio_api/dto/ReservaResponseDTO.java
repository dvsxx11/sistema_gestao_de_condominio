package com.condominio.condominio_api.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReservaResponseDTO {
    private Long id;
    private String areaComumNome;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private String motivo;
    private String status;
    private String moradorNome;
    private String unidadeNumero;
}