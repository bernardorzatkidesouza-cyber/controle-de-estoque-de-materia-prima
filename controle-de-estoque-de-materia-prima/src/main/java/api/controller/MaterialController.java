package com.industria.estoque.controller;

import com.industria.estoque.model.Material;
import com.industria.estoque.repository.MaterialRepository;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/materiais")
public class MaterialController {

    private final MaterialRepository repository;

    public MaterialController(MaterialRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Material cadastrar(@RequestBody Material material) {
        if (material.getNome() == null ||
                material.getNome().isBlank()) {
            throw new IllegalArgumentException(
                    "O nome do material é obrigatório.");
        }

        if (material.getQuantidade() == null ||
                material.getQuantidade().signum() < 0 ||
                material.getEstoqueMinimo() == null ||
                material.getEstoqueMinimo().signum() < 0) {
            throw new IllegalArgumentException(
                    "As quantidades não podem ser negativas.");
        }

        return repository.save(material);
    }

    @GetMapping
    public List<Material> listar() {
        return repository.findAll();
    }

    @GetMapping("/estoque-baixo")
    public List<Material> estoqueBaixo() {
        return repository.buscarEstoqueBaixo();
    }

    @PutMapping("/{id}")
    public Material atualizar(
            @PathVariable Long id,
            @RequestBody Material dados) {

        Material material = repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Material não encontrado."));

        if (dados.getNome() == null ||
                dados.getNome().isBlank() ||
                dados.getQuantidade() == null ||
                dados.getQuantidade().signum() < 0 ||
                dados.getEstoqueMinimo() == null ||
                dados.getEstoqueMinimo().signum() < 0) {
            throw new IllegalArgumentException(
                    "Informe dados válidos.");
        }

        material.setNome(dados.getNome());
        material.setQuantidade(dados.getQuantidade());
        material.setEstoqueMinimo(dados.getEstoqueMinimo());

        return repository.save(material);
    }
}