package com.industria.estoque.repository;

import com.industria.estoque.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MaterialRepository
        extends JpaRepository<Material, Long> {

    @Query("""
        SELECT m FROM Material m
        WHERE m.quantidade < m.estoqueMinimo
    """)
    List<Material> buscarEstoqueBaixo();
}