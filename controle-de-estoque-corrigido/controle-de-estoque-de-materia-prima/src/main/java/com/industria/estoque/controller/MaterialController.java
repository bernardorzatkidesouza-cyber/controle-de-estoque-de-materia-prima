package com.industria.estoque.controller;

import com.industria.estoque.model.Material;
import com.industria.estoque.repository.MaterialRepository;
<<<<<<< HEAD:controle-de-estoque-corrigido/controle-de-estoque-de-materia-prima/src/main/java/com/industria/estoque/controller/MaterialController.java
import com.industria.estoque.service.RecursoNaoEncontradoException;
import com.industria.estoque.service.RegraDeNegocioException;
=======
>>>>>>> main:controle-de-estoque-de-materia-prima/src/main/java/api/controller/MaterialController.java

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
<<<<<<< HEAD:controle-de-estoque-corrigido/controle-de-estoque-de-materia-prima/src/main/java/com/industria/estoque/controller/MaterialController.java
            throw new RegraDeNegocioException(
=======
            throw new IllegalArgumentException(
>>>>>>> main:controle-de-estoque-de-materia-prima/src/main/java/api/controller/MaterialController.java
                    "O nome do material é obrigatório.");
        }

        if (material.getQuantidade() == null ||
                material.getQuantidade().signum() < 0 ||
                material.getEstoqueMinimo() == null ||
                material.getEstoqueMinimo().signum() < 0) {
<<<<<<< HEAD:controle-de-estoque-corrigido/controle-de-estoque-de-materia-prima/src/main/java/com/industria/estoque/controller/MaterialController.java
            throw new RegraDeNegocioException(
=======
            throw new IllegalArgumentException(
>>>>>>> main:controle-de-estoque-de-materia-prima/src/main/java/api/controller/MaterialController.java
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
<<<<<<< HEAD:controle-de-estoque-corrigido/controle-de-estoque-de-materia-prima/src/main/java/com/industria/estoque/controller/MaterialController.java
                        new RecursoNaoEncontradoException(
                                "Material não encontrado: " + id));
=======
                        new IllegalArgumentException(
                                "Material não encontrado."));
>>>>>>> main:controle-de-estoque-de-materia-prima/src/main/java/api/controller/MaterialController.java

        if (dados.getNome() == null ||
                dados.getNome().isBlank() ||
                dados.getQuantidade() == null ||
                dados.getQuantidade().signum() < 0 ||
                dados.getEstoqueMinimo() == null ||
                dados.getEstoqueMinimo().signum() < 0) {
<<<<<<< HEAD:controle-de-estoque-corrigido/controle-de-estoque-de-materia-prima/src/main/java/com/industria/estoque/controller/MaterialController.java
            throw new RegraDeNegocioException(
=======
            throw new IllegalArgumentException(
>>>>>>> main:controle-de-estoque-de-materia-prima/src/main/java/api/controller/MaterialController.java
                    "Informe dados válidos.");
        }

        material.setNome(dados.getNome());
        material.setQuantidade(dados.getQuantidade());
        material.setEstoqueMinimo(dados.getEstoqueMinimo());

        return repository.save(material);
    }
<<<<<<< HEAD:controle-de-estoque-corrigido/controle-de-estoque-de-materia-prima/src/main/java/com/industria/estoque/controller/MaterialController.java
}
=======
}
>>>>>>> main:controle-de-estoque-de-materia-prima/src/main/java/api/controller/MaterialController.java
