package com.condominio.condominio_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "moradores")
public class Morador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @Email
    @Column(unique = true)
    private String email;

    private String telefone;
    private String celular;

    @Column(unique = true)
    private String cpf;

    private String rg;
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    private TipoMorador tipo;

    private Boolean ativo = true;

    @ManyToOne
    @JoinColumn(name = "unidade_id")
    private Unidade unidade;

    @JsonIgnore
    @OneToOne(mappedBy = "morador")
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "morador")
    private List<Veiculo> veiculos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "morador")
    private List<ReservaAreaComum> reservas = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "morador")
    private List<Ocorrencia> ocorrencias = new ArrayList<>();
}
