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
<<<<<<< HEAD:controle-de-estoque-corrigido/controle-de-estoque-de-materia-prima/src/main/java/com/industria/estoque/service/MovimentacaoService.java
            throw new RegraDeNegocioException(
                    "A quantidade deve ser maior que zero.");
        }

        if (materialId == null) {
            throw new RegraDeNegocioException(
                    "Informe o material da movimentação.");
        }

        Material material = materialRepository.findById(materialId)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Material não encontrado: " + materialId));

        if (tipo == null) {
            throw new RegraDeNegocioException(
=======
            throw new IllegalArgumentException(
                    "A quantidade deve ser maior que zero.");
        }

        Material material = materialRepository.findById(materialId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Material não encontrado."));

        if (tipo == null) {
            throw new IllegalArgumentException(
>>>>>>> main:controle-de-estoque-de-materia-prima/src/main/java/api/service/MovimentacaoService.java
                    "Informe o tipo da movimentação.");
        }

        tipo = tipo.toLowerCase();

        // RN01: impedir saída maior que o estoque
        if (tipo.equals("saida")) {

            if (quantidade.compareTo(material.getQuantidade()) > 0) {
<<<<<<< HEAD:controle-de-estoque-corrigido/controle-de-estoque-de-materia-prima/src/main/java/com/industria/estoque/service/MovimentacaoService.java
                throw new RegraDeNegocioException(
                        "RN01: estoque insuficiente. Disponível: "
                                + material.getQuantidade()
                                + ", solicitado: " + quantidade);
=======
                throw new IllegalArgumentException(
                        "Estoque insuficiente.");
>>>>>>> main:controle-de-estoque-de-materia-prima/src/main/java/api/service/MovimentacaoService.java
            }

            material.setQuantidade(
                    material.getQuantidade().subtract(quantidade));

        } else if (tipo.equals("entrada")) {

            material.setQuantidade(
                    material.getQuantidade().add(quantidade));

        } else {
<<<<<<< HEAD:controle-de-estoque-corrigido/controle-de-estoque-de-materia-prima/src/main/java/com/industria/estoque/service/MovimentacaoService.java
            throw new RegraDeNegocioException(
=======
            throw new IllegalArgumentException(
>>>>>>> main:controle-de-estoque-de-materia-prima/src/main/java/api/service/MovimentacaoService.java
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
<<<<<<< HEAD:controle-de-estoque-corrigido/controle-de-estoque-de-materia-prima/src/main/java/com/industria/estoque/service/MovimentacaoService.java
                                    new RecursoNaoEncontradoException(
                                            "Fornecedor não encontrado: " + fornecedorId));
=======
                                    new IllegalArgumentException(
                                            "Fornecedor não encontrado."));
>>>>>>> main:controle-de-estoque-de-materia-prima/src/main/java/api/service/MovimentacaoService.java

            movimentacao.setFornecedor(fornecedor);
        }

        return movimentacaoRepository.save(movimentacao);
    }

    // RN02: listar materiais abaixo do estoque mínimo
    public List<Material> consultarEstoqueBaixo() {
        return materialRepository.buscarEstoqueBaixo();
    }
<<<<<<< HEAD:controle-de-estoque-corrigido/controle-de-estoque-de-materia-prima/src/main/java/com/industria/estoque/service/MovimentacaoService.java
}
=======
}
>>>>>>> main:controle-de-estoque-de-materia-prima/src/main/java/api/service/MovimentacaoService.java
