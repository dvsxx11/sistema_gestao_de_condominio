package com.condominio.condominio_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "unidades")
public class Unidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numero;

    private String bloco;
    private String andar;
    private Double area;
    private Integer quartos;
    private Integer vagasGaragem;

    @ManyToOne
    @JoinColumn(name = "condominio_id")
    private Condominio condominio;

    @OneToOne(mappedBy = "unidade")
    private Morador proprietario;

    @JsonIgnore
    @OneToMany(mappedBy = "unidade")
    private List<Morador> moradores = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "unidade")
    private List<Veiculo> veiculos = new ArrayList<>();
}