package com.industria.estoque.controller;

import com.industria.estoque.model.Material;
import com.industria.estoque.repository.MaterialRepository;
import com.industria.estoque.service.RecursoNaoEncontradoException;
import com.industria.estoque.service.RegraDeNegocioException;

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
            throw new RegraDeNegocioException(
                    "O nome do material é obrigatório.");
        }

        if (material.getQuantidade() == null ||
                material.getQuantidade().signum() < 0 ||
                material.getEstoqueMinimo() == null ||
                material.getEstoqueMinimo().signum() < 0) {
            throw new RegraDeNegocioException(
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
                        new RecursoNaoEncontradoException(
                                "Material não encontrado: " + id));

        if (dados.getNome() == null ||
                dados.getNome().isBlank() ||
                dados.getQuantidade() == null ||
                dados.getQuantidade().signum() < 0 ||
                dados.getEstoqueMinimo() == null ||
                dados.getEstoqueMinimo().signum() < 0) {
            throw new RegraDeNegocioException(
                    "Informe dados válidos.");
        }

        material.setNome(dados.getNome());
        material.setQuantidade(dados.getQuantidade());
        material.setEstoqueMinimo(dados.getEstoqueMinimo());

        return repository.save(material);
    }
}