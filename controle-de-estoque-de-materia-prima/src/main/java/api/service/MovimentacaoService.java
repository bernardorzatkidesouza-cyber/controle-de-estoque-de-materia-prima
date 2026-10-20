package com.industria.estoque.service;

import com.industria.estoque.model.*;
import com.industria.estoque.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MovimentacaoService {

    private final MaterialRepository materialRepository;
    private final FornecedorRepository fornecedorRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    public MovimentacaoService(
            MaterialRepository materialRepository,
            FornecedorRepository fornecedorRepository,
            MovimentacaoRepository movimentacaoRepository) {

        this.materialRepository = materialRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    @Transactional
    public Movimentacao registrar(
            Long materialId,
            Long fornecedorId,
            String tipo,
            BigDecimal quantidade) {

        // Validação da quantidade
        if (quantidade == null ||
                quantidade.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser maior que zero.");
        }

        Material material = materialRepository.findById(materialId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Material não encontrado."));

        if (tipo == null) {
            throw new IllegalArgumentException(
                    "Informe o tipo da movimentação.");
        }

        tipo = tipo.toLowerCase();

        // RN01: impedir saída maior que o estoque
        if (tipo.equals("saida")) {

            if (quantidade.compareTo(material.getQuantidade()) > 0) {
                throw new IllegalArgumentException(
                        "Estoque insuficiente.");
            }

            material.setQuantidade(
                    material.getQuantidade().subtract(quantidade));

        } else if (tipo.equals("entrada")) {

            material.setQuantidade(
                    material.getQuantidade().add(quantidade));

        } else {
            throw new IllegalArgumentException(
                    "Tipo deve ser entrada ou saida.");
        }

        // RN03: atualizar e registrar a movimentação
        materialRepository.save(material);

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setMaterial(material);
        movimentacao.setTipo(tipo);
        movimentacao.setQuantidade(quantidade);

        if (fornecedorId != null) {
            Fornecedor fornecedor =
                    fornecedorRepository.findById(fornecedorId)
                            .orElseThrow(() ->
                                    new IllegalArgumentException(
                                            "Fornecedor não encontrado."));

            movimentacao.setFornecedor(fornecedor);
        }

        return movimentacaoRepository.save(movimentacao);
    }

    // RN02: listar materiais abaixo do estoque mínimo
    public List<Material> consultarEstoqueBaixo() {
        return materialRepository.buscarEstoqueBaixo();
    }
}