/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.alphasystem.repository;

/**
 *
 * @author Desktop
 */

import java.util.List;
import com.api.alphasystem.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Desktop
 */
@Repository
public interface VendaRepository extends JpaRepository<Venda, Integer>{
    
    //public void inserir(Venda v);
    //public int searchId(Venda v);
    //public List<Object[]> getAllEProduto();
    boolean existsByCliente_Id(Integer clienteId);
}