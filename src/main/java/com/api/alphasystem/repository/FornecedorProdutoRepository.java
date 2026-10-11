/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.alphasystem.repository;

import com.api.alphasystem.model.FornecedorProduto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorProdutoRepository
        extends JpaRepository<FornecedorProduto, Integer> {
    
    Optional<FornecedorProduto> findFirstByProduto_IdOrderByIdAsc(int produtoId);
    
    List<FornecedorProduto> findByProdutoId(Integer idProduto);
    
    boolean existsByProduto_Id(int id);
    
    boolean existsByFornecedor_Id(Integer fornecedorId);
}
