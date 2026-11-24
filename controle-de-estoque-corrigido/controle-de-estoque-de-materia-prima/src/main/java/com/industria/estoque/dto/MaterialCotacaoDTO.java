package com.industria.estoque.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MaterialCotacaoDTO(
        Long id,
        String nome,
        BigDecimal quantidade,
        BigDecimal precoUnitarioDolar,
        BigDecimal valorTotalDolar,
        BigDecimal cotacaoDolar,
        BigDecimal valorTotalReais,
        LocalDate dataCotacao,
        boolean cotacaoDisponivel,
        String fonte
) {}