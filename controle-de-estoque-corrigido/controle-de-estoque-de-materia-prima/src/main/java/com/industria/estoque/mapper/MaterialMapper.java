
package com.industria.estoque.mapper;

import com.industria.estoque.dto.MaterialEntradaDTO;
import com.industria.estoque.dto.MaterialRespostaDTO;
import com.industria.estoque.model.Material;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MaterialMapper {

    // Converte DTO de entrada em entidade
    public Material toEntity(MaterialEntradaDTO dto) {
        if (dto == null) {
            return null;
        }

        Material material = new Material();
        material.setNome(dto.nome());
        material.setQuantidade(dto.quantidade());
        material.setEstoqueMinimo(dto.estoqueMinimo());

        return material;
    }

    // Converte entidade em DTO de resposta
    public MaterialRespostaDTO toResponse(Material material) {
        if (material == null) {
            return null;
        }

        return new MaterialRespostaDTO(
                material.getId(),
                material.getNome(),
                material.getQuantidade(),
                material.getEstoqueMinimo()
        );
    }

    // Converte uma lista de entidades em uma lista de DTOs
    public List<MaterialRespostaDTO> toResponseList(
            List<Material> materiais) {

        return materiais.stream()
                .map(this::toResponse)
                .toList();
    }

    // Atualiza uma entidade existente
    public void updateEntity(
            Material material,
            MaterialEntradaDTO dto) {

        material.setNome(dto.nome());
        material.setQuantidade(dto.quantidade());
        material.setEstoqueMinimo(dto.estoqueMinimo());
    }
}
