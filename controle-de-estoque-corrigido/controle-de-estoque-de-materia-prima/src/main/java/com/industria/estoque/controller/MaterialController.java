
package com.industria.estoque.controller;

import com.industria.estoque.dto.MaterialCotacaoDTO;
import com.industria.estoque.dto.MaterialEntradaDTO;
import com.industria.estoque.dto.MaterialRespostaDTO;
import com.industria.estoque.service.CotacaoService;
import com.industria.estoque.service.MaterialService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materiais")
public class MaterialController {

    private final CotacaoService cotacaoService;

    private final MaterialService service;

    public MaterialController(
            MaterialService service,
            CotacaoService cotacaoService) {

        this.service = service;
        this.cotacaoService = cotacaoService;
    }

    @GetMapping("/{id}/cotacao")
    public MaterialCotacaoDTO consultarCotacao(
            @PathVariable Long id) {

        return cotacaoService.consultar(id);
    }

    @PostMapping
    public MaterialRespostaDTO cadastrar(
            @Valid @RequestBody MaterialEntradaDTO dados) {
        return service.cadastrar(dados);
    }

    @GetMapping
    public List<MaterialRespostaDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public MaterialRespostaDTO buscarPorId(
            @PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/estoque-baixo")
    public List<MaterialRespostaDTO> estoqueBaixo() {
        return service.estoqueBaixo();
    }

    @PutMapping("/{id}")
    public MaterialRespostaDTO atualizar(
            @PathVariable Long id,
            @Valid @RequestBody MaterialEntradaDTO dados) {
        return service.atualizar(id, dados);
    }
}
