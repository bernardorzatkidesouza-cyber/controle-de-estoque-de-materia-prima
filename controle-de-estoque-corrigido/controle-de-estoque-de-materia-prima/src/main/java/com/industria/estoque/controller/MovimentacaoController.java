package com.industria.estoque.controller;

import com.industria.estoque.model.Movimentacao;
import com.industria.estoque.repository.MovimentacaoRepository;
import com.industria.estoque.service.MovimentacaoService;

import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
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

    @PostMapping
    public Movimentacao registrar(@RequestBody NovaMovimentacao dados) {
        return service.registrar(
                dados.materialId(),
                dados.fornecedorId(),
                dados.tipo(),
                dados.quantidade()
        );
    }

    @GetMapping
    public List<Movimentacao> listar() {
        return repository.findAll();
    }

    public record NovaMovimentacao(
            Long materialId,
            Long fornecedorId,
            String tipo,
            BigDecimal quantidade) {
    }
}