
package com.industria.estoque.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimentacaoRespostaDTO(
        Long id,
        Long materialId,
        String materialNome,
        Long fornecedorId,
        String fornecedorNome,
        String tipo,
        BigDecimal quantidade,
        LocalDateTime data
) {
}
