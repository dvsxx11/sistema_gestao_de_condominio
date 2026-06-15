package com.condominio.condominio_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "areas_comuns")
    public class AreaComum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private Integer capacidadeMaxima;
    private BigDecimal valorLocacao;
    private Boolean precisaAgendamento = true;
    private Boolean ativa = true;

    @ManyToOne
    @JoinColumn(name = "condominio_id")
    private Condominio condominio;

    @JsonIgnore
    @OneToMany(mappedBy = "areaComum")
    private List<ReservaAreaComum> reservas = new ArrayList<>();
}