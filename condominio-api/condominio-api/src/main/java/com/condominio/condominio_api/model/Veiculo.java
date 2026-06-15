package com.condominio.condominio_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "veiculos")
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String placa;

    private String modelo;
    private String marca;
    private String cor;
    private Integer ano;

    @ManyToOne
    @JoinColumn(name = "morador_id")
    private Morador morador;

    @ManyToOne
    @JoinColumn(name = "unidade_id")
    private Unidade unidade;
}