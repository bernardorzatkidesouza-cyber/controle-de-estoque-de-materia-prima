
package com.industria.estoque.mapper;

import com.industria.estoque.dto.MovimentacaoEntradaDTO;
import com.industria.estoque.dto.MovimentacaoRespostaDTO;
import com.industria.estoque.model.Material;
import com.industria.estoque.model.Fornecedor;
import com.industria.estoque.model.Movimentacao;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovimentacaoMapper {

    // Converte DTO de entrada em entidade
    public Movimentacao toEntity(
            MovimentacaoEntradaDTO dto,
            Material material,
            Fornecedor fornecedor) {

        if (dto == null) {
            return null;
        }

        Movimentacao movimentacao = new Movimentacao();

        movimentacao.setMaterial(material);
        movimentacao.setFornecedor(fornecedor);
        movimentacao.setTipo(dto.tipo());
        movimentacao.setQuantidade(dto.quantidade());

        return movimentacao;
    }

    // Converte entidade em DTO de resposta
    public MovimentacaoRespostaDTO toResponse(
            Movimentacao movimentacao) {

        if (movimentacao == null) {
            return null;
        }

        Material material = movimentacao.getMaterial();
        Fornecedor fornecedor = movimentacao.getFornecedor();

        return new MovimentacaoRespostaDTO(
                movimentacao.getId(),

                material != null
                        ? material.getId()
                        : null,

                material != null
                        ? material.getNome()
                        : null,

                fornecedor != null
                        ? fornecedor.getId()
                        : null,

                fornecedor != null
                        ? fornecedor.getNome()
                        : null,

                movimentacao.getTipo(),
                movimentacao.getQuantidade(),
                movimentacao.getData()
        );
    }

    // Converte uma lista de entidades em DTOs
    public List<MovimentacaoRespostaDTO> toResponseList(
            List<Movimentacao> movimentacoes) {

        return movimentacoes.stream()
                .map(this::toResponse)
                .toList();
    }
}
