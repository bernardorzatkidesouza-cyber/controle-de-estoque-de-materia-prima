package com.industria.estoque.repository;

import com.industria.estoque.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository
        extends JpaRepository<Movimentacao, Long> {
}