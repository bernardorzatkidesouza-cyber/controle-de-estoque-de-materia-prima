package com.industria.estoque.controller;

import com.industria.estoque.model.Fornecedor;
import com.industria.estoque.repository.FornecedorRepository;
<<<<<<< HEAD:controle-de-estoque-corrigido/controle-de-estoque-de-materia-prima/src/main/java/com/industria/estoque/controller/FornecedorController.java
import com.industria.estoque.service.RegraDeNegocioException;
=======
>>>>>>> main:controle-de-estoque-de-materia-prima/src/main/java/api/controller/FornecedorController.java

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorRepository repository;

    public FornecedorController(FornecedorRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Fornecedor cadastrar(@RequestBody Fornecedor fornecedor) {
        if (fornecedor.getNome() == null ||
                fornecedor.getNome().isBlank()) {
<<<<<<< HEAD:controle-de-estoque-corrigido/controle-de-estoque-de-materia-prima/src/main/java/com/industria/estoque/controller/FornecedorController.java
            throw new RegraDeNegocioException(
=======
            throw new IllegalArgumentException(
>>>>>>> main:controle-de-estoque-de-materia-prima/src/main/java/api/controller/FornecedorController.java
                    "O nome do fornecedor é obrigatório.");
        }

        return repository.save(fornecedor);
    }

    @GetMapping
    public List<Fornecedor> listar() {
        return repository.findAll();
    }
<<<<<<< HEAD:controle-de-estoque-corrigido/controle-de-estoque-de-materia-prima/src/main/java/com/industria/estoque/controller/FornecedorController.java
}
=======
}
>>>>>>> main:controle-de-estoque-de-materia-prima/src/main/java/api/controller/FornecedorController.java
