/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.alphasystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Id;



@Entity
public class FornecedorProduto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
     
    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;
    private float taxa;
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public Fornecedor getFornecedor() { return fornecedor; }
    public void setFornecedor(Fornecedor fornecedor) { this.fornecedor = fornecedor; }

    public float getTaxa() { return taxa; }
    public void setTaxa(float taxa) { this.taxa = taxa; }
}