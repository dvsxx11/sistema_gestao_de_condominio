package com.condominio.condominio_api.dto;

import lombok.Data;

@Data
public class MoradorResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String celular;
    private String cpf;
    private String tipo;
    private String unidadeNumero;
    private String condominioNome;
    private String senhaTemporaria;
}