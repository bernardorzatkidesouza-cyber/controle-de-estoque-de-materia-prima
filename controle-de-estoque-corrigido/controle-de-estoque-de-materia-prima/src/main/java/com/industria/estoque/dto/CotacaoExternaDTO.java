package com.industria.estoque.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public record CotacaoExternaDTO(
        LocalDate date,
        String base,
        Map<String, BigDecimal> rates
) {}