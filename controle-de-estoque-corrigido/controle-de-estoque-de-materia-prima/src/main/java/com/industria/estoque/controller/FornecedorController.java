package com.industria.estoque.controller;

import com.industria.estoque.model.Fornecedor;
import com.industria.estoque.repository.FornecedorRepository;
import com.industria.estoque.service.RegraDeNegocioException;

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
            throw new RegraDeNegocioException(
                    "O nome do fornecedor é obrigatório.");
        }

        return repository.save(fornecedor);
    }

    @GetMapping
    public List<Fornecedor> listar() {
        return repository.findAll();
    }
}