package com.industria.estoque.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record MovimentacaoEntradaDTO(
    @NotNull(message = "Informe o material.")
    Long materialId,

    Long fornecedorId,

    @NotNull(message = "Informe o tipo.")
    @Pattern(
        regexp = "(?i)entrada|saida",
        message = "O tipo deve ser entrada ou saida."
    )
    String tipo,

    @NotNull(message = "Informe a quantidade.")
    @DecimalMin(
        value = "0.01",
        message = "A quantidade deve ser maior que zero."
    )
    BigDecimal quantidade
) {}