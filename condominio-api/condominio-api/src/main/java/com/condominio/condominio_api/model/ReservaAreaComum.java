package com.condominio.condominio_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservas_areas_comuns")
public class ReservaAreaComum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private String motivo;

    @Enumerated(EnumType.STRING)
    private StatusReserva status;

    @ManyToOne
    @JoinColumn(name = "area_comum_id")
    private AreaComum areaComum;

    @ManyToOne
    @JoinColumn(name = "morador_id")
    private Morador morador;

    @ManyToOne
    @JoinColumn(name = "unidade_id")
    private Unidade unidade;
}
