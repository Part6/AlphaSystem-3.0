/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.alphasystem.repository;

import java.util.ArrayList;
import java.util.List;
import com.api.alphasystem.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Desktop
 */



@Repository 
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{
    
    /*public int inserir(Produto p);
    public void updateQuantidade(int id);
    public List<Produto> getAll();
    public ArrayList<Object[]> getAllEFornecedor();
    public int searchId(String nome, String cate, String obs, int qnt, Double preco);
    public int searchId(String nome) ;*/
}
