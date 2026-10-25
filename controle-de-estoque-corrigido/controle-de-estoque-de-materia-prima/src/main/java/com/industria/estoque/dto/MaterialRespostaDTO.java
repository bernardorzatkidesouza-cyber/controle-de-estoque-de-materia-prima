package com.industria.estoque.dto;

import java.math.BigDecimal;

public record MaterialRespostaDTO(
    Long id,
    String nome,
    BigDecimal quantidade,
    BigDecimal estoqueMinimo
) {}