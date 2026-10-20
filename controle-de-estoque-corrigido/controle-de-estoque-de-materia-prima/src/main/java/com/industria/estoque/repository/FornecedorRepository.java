package com.industria.estoque.repository;

import com.industria.estoque.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepository
        extends JpaRepository<Fornecedor, Long> {
}