package com.industria.estoque.controller;

import com.industria.estoque.dto.MovimentacaoEntradaDTO;
import com.industria.estoque.dto.MovimentacaoRespostaDTO;
import com.industria.estoque.model.Movimentacao;
import com.industria.estoque.repository.MovimentacaoRepository;
import com.industria.estoque.service.MovimentacaoService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

    private final MovimentacaoService service;
    private final MovimentacaoRepository repository;

    public MovimentacaoController(
            MovimentacaoService service,
            MovimentacaoRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    private MovimentacaoRespostaDTO paraDTO(
            Movimentacao movimentacao) {

        return new MovimentacaoRespostaDTO(
            movimentacao.getId(),
            movimentacao.getMaterial().getId(),
            movimentacao.getMaterial().getNome(),
            movimentacao.getFornecedor() != null
                ? movimentacao.getFornecedor().getId()
                : null,
            movimentacao.getFornecedor() != null
                ? movimentacao.getFornecedor().getNome()
                : null,
            movimentacao.getTipo(),
            movimentacao.getQuantidade(),
            movimentacao.getData()
        );
    }

    @PostMapping
    public MovimentacaoRespostaDTO registrar(
            @Valid @RequestBody MovimentacaoEntradaDTO dados) {

        Movimentacao movimentacao = service.registrar(
            dados.materialId(),
            dados.fornecedorId(),
            dados.tipo(),
            dados.quantidade()
        );

        return paraDTO(movimentacao);
    }

    @GetMapping
    public List<MovimentacaoRespostaDTO> listar() {
        return repository.findAll()
            .stream()
            .map(this::paraDTO)
            .toList();
    }
}