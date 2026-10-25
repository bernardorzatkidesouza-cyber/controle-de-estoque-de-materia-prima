package com.industria.estoque.controller;

import com.industria.estoque.dto.FornecedorEntradaDTO;
import com.industria.estoque.dto.FornecedorRespostaDTO;
import com.industria.estoque.model.Fornecedor;
import com.industria.estoque.repository.FornecedorRepository;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorRepository repository;

    public FornecedorController(
            FornecedorRepository repository) {
        this.repository = repository;
    }

    private FornecedorRespostaDTO paraDTO(
            Fornecedor fornecedor) {

        return new FornecedorRespostaDTO(
            fornecedor.getId(),
            fornecedor.getNome(),
            fornecedor.getContato()
        );
    }

    @PostMapping
    public FornecedorRespostaDTO cadastrar(
            @Valid @RequestBody FornecedorEntradaDTO dados) {

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(dados.nome());
        fornecedor.setContato(dados.contato());

        return paraDTO(repository.save(fornecedor));
    }

    @GetMapping
    public List<FornecedorRespostaDTO> listar() {
        return repository.findAll()
            .stream()
            .map(this::paraDTO)
            .toList();
    }
}