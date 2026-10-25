package com.industria.estoque.controller;

import com.industria.estoque.dto.MaterialEntradaDTO;
import com.industria.estoque.dto.MaterialRespostaDTO;
import com.industria.estoque.model.Material;
import com.industria.estoque.repository.MaterialRepository;
import com.industria.estoque.service.RecursoNaoEncontradoException;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materiais")
public class MaterialController {

    private final MaterialRepository repository;

    public MaterialController(MaterialRepository repository) {
        this.repository = repository;
    }

    private MaterialRespostaDTO paraDTO(Material material) {
        return new MaterialRespostaDTO(
            material.getId(),
            material.getNome(),
            material.getQuantidade(),
            material.getEstoqueMinimo()
        );
    }

    @PostMapping
    public MaterialRespostaDTO cadastrar(
            @Valid @RequestBody MaterialEntradaDTO dados) {

        Material material = new Material();
        material.setNome(dados.nome());
        material.setQuantidade(dados.quantidade());
        material.setEstoqueMinimo(dados.estoqueMinimo());

        return paraDTO(repository.save(material));
    }

    @GetMapping
    public List<MaterialRespostaDTO> listar() {
        return repository.findAll()
            .stream()
            .map(this::paraDTO)
            .toList();
    }

    @GetMapping("/estoque-baixo")
    public List<MaterialRespostaDTO> estoqueBaixo() {
        return repository.buscarEstoqueBaixo()
            .stream()
            .map(this::paraDTO)
            .toList();
    }

    @PutMapping("/{id}")
    public MaterialRespostaDTO atualizar(
            @PathVariable Long id,
            @Valid @RequestBody MaterialEntradaDTO dados) {

        Material material = repository.findById(id)
            .orElseThrow(() ->
                new RecursoNaoEncontradoException(
                    "Material não encontrado: " + id
                )
            );

        material.setNome(dados.nome());
        material.setQuantidade(dados.quantidade());
        material.setEstoqueMinimo(dados.estoqueMinimo());

        return paraDTO(repository.save(material));
    }
}