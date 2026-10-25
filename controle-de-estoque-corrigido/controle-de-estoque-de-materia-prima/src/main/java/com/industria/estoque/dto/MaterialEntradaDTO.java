
package com.industria.estoque.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MaterialEntradaDTO(

    @NotBlank(message = "O nome do material é obrigatório.")
    String nome,

    @NotNull(message = "A quantidade é obrigatória.")
    @DecimalMin(
        value = "0.00",
        message = "A quantidade não pode ser negativa."
    )
    BigDecimal quantidade,

    @NotNull(message = "O estoque mínimo é obrigatório.")
    @DecimalMin(
        value = "0.00",
        message = "O estoque mínimo não pode ser negativo."
    )
    BigDecimal estoqueMinimo

) {}
