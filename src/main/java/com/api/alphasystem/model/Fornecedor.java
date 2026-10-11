/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.alphasystem.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


/*
public class Cliente {
    
    */

@Entity
public class Fornecedor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String local;
    //private List<FornecedorProduto> produtos;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    @Override
    public String toString() {
    return nome; // é isso que aparece no ComboBox
}
   // public List<FornecedorProduto> getProdutos() { return produtos; }
   // public void setProdutos(List<FornecedorProduto> produtos) { this.produtos = produtos; }
}
