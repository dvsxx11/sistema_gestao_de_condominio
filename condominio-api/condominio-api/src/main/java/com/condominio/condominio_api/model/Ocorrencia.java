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
@Table(name = "ocorrencias")
public class Ocorrencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private LocalDateTime dataOcorrencia;

    @Enumerated(EnumType.STRING)
    private TipoOcorrencia tipo;

    @Enumerated(EnumType.STRING)
    private StatusOcorrencia status;

    @ManyToOne
    @JoinColumn(name = "morador_id")
    private Morador morador;

    @ManyToOne
    @JoinColumn(name = "unidade_id")
    private Unidade unidade;
}

