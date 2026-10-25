package com.industria.estoque.dto;

import jakarta.validation.constraints.NotBlank;

public record FornecedorEntradaDTO(
    @NotBlank(message = "O nome do fornecedor é obrigatório.")
    String nome,

    String contato
) {}