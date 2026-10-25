
package com.industria.estoque.service;

import com.industria.estoque.dto.FornecedorEntradaDTO;
import com.industria.estoque.dto.FornecedorRespostaDTO;
import com.industria.estoque.mapper.FornecedorMapper;
import com.industria.estoque.model.Fornecedor;
import com.industria.estoque.repository.FornecedorRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FornecedorService {

    private final FornecedorRepository repository;
    private final FornecedorMapper mapper;

    public FornecedorService(
            FornecedorRepository repository,
            FornecedorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public FornecedorRespostaDTO cadastrar(FornecedorEntradaDTO dados) {
        Fornecedor fornecedor = mapper.toEntity(dados);
        return mapper.toResponse(repository.save(fornecedor));
    }

    public List<FornecedorRespostaDTO> listar() {
        return mapper.toResponseList(repository.findAll());
    }

    public FornecedorRespostaDTO buscarPorId(Long id) {
        Fornecedor fornecedor = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Fornecedor não encontrado: " + id
                        ));

        return mapper.toResponse(fornecedor);
    }

    @Transactional
    public FornecedorRespostaDTO atualizar(
            Long id,
            FornecedorEntradaDTO dados) {

        Fornecedor fornecedor = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Fornecedor não encontrado: " + id
                        ));

        mapper.updateEntity(fornecedor, dados);

        return mapper.toResponse(repository.save(fornecedor));
    }
}
