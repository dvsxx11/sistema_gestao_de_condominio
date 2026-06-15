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
@Table(name = "encomendas")
public class Encomenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoRastreio;
    private String transportadora;
    private LocalDateTime dataRecebimento;
    private LocalDateTime dataRetirada;

    @Enumerated(EnumType.STRING)
    private StatusEncomenda status;

    private String observacao;

    @ManyToOne
    @JoinColumn(name = "morador_id")
    private Morador morador;

    @ManyToOne
    @JoinColumn(name = "unidade_id")
    private Unidade unidade;
}

