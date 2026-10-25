
package com.industria.estoque.service;

import com.industria.estoque.dto.MaterialEntradaDTO;
import com.industria.estoque.dto.MaterialRespostaDTO;
import com.industria.estoque.mapper.MaterialMapper;
import com.industria.estoque.model.Material;
import com.industria.estoque.repository.MaterialRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepository repository;
    private final MaterialMapper mapper;

    public MaterialService(
            MaterialRepository repository,
            MaterialMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public MaterialRespostaDTO cadastrar(MaterialEntradaDTO dados) {
        Material material = mapper.toEntity(dados);
        return mapper.toResponse(repository.save(material));
    }

    public List<MaterialRespostaDTO> listar() {
        return mapper.toResponseList(repository.findAll());
    }

    public MaterialRespostaDTO buscarPorId(Long id) {
        Material material = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Material não encontrado: " + id
                        ));

        return mapper.toResponse(material);
    }

    public List<MaterialRespostaDTO> estoqueBaixo() {
        return mapper.toResponseList(repository.buscarEstoqueBaixo());
    }

    @Transactional
    public MaterialRespostaDTO atualizar(
            Long id,
            MaterialEntradaDTO dados) {

        Material material = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Material não encontrado: " + id
                        ));

        mapper.updateEntity(material, dados);

        return mapper.toResponse(repository.save(material));
    }
}
