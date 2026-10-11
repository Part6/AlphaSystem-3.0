/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.alphasystem.repository;

import com.api.alphasystem.model.ProdutoHasVenda;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoHasVendaRepository extends JpaRepository<ProdutoHasVenda, Integer> {
    List<ProdutoHasVenda> findByVenda_IdVenda(Integer idVenda);
    Boolean existsByProduto_Id(int id);
}
