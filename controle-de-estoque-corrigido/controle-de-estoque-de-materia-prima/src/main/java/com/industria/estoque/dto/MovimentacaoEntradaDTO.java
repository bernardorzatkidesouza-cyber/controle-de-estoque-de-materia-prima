
package com.industria.estoque.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record MovimentacaoEntradaDTO(

    @NotNull(message = "Informe o material.")
    @Positive(message = "O ID do material deve ser maior que zero.")
    Long materialId,

    @Positive(message = "O ID do fornecedor deve ser maior que zero.")
    Long fornecedorId,

    @NotNull(message = "Informe o tipo da movimentação.")
    @Pattern(
        regexp = "(?i)^(entrada|saida)$",
        message = "O tipo deve ser 'entrada' ou 'saida'."
    )
    String tipo,

    @NotNull(message = "Informe a quantidade.")
    @DecimalMin(
        value = "0.01",
        message = "A quantidade deve ser maior que zero."
    )
    BigDecimal quantidade

) {}
