
package com.industria.estoque.controller;

import com.industria.estoque.dto.FornecedorEntradaDTO;
import com.industria.estoque.dto.FornecedorRespostaDTO;
import com.industria.estoque.service.FornecedorService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorService service;

    public FornecedorController(FornecedorService service) {
        this.service = service;
    }

    @PostMapping
    public FornecedorRespostaDTO cadastrar(
            @Valid @RequestBody FornecedorEntradaDTO dados) {
        return service.cadastrar(dados);
    }

    @GetMapping
    public List<FornecedorRespostaDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public FornecedorRespostaDTO buscarPorId(
            @PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public FornecedorRespostaDTO atualizar(
            @PathVariable Long id,
            @Valid @RequestBody FornecedorEntradaDTO dados) {
        return service.atualizar(id, dados);
    }
}
