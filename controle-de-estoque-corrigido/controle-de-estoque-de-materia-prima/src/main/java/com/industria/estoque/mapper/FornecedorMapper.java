
package com.industria.estoque.mapper;

import com.industria.estoque.dto.FornecedorEntradaDTO;
import com.industria.estoque.dto.FornecedorRespostaDTO;
import com.industria.estoque.model.Fornecedor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FornecedorMapper {

    public Fornecedor toEntity(FornecedorEntradaDTO dto) {
        if (dto == null) {
            return null;
        }

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(dto.nome());
        fornecedor.setContato(dto.contato());

        return fornecedor;
    }

    public FornecedorRespostaDTO toResponse(
            Fornecedor fornecedor) {

        if (fornecedor == null) {
            return null;
        }

        return new FornecedorRespostaDTO(
                fornecedor.getId(),
                fornecedor.getNome(),
                fornecedor.getContato()
        );
    }

    public List<FornecedorRespostaDTO> toResponseList(
            List<Fornecedor> fornecedores) {

        return fornecedores.stream()
                .map(this::toResponse)
                .toList();
    }

    public void updateEntity(
            Fornecedor fornecedor,
            FornecedorEntradaDTO dto) {

        fornecedor.setNome(dto.nome());
        fornecedor.setContato(dto.contato());
    }
}
