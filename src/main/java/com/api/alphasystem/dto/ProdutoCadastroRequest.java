/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.alphasystem.dto;

public class ProdutoCadastroRequest {

    private String nome;
    private String categoria;
    private String observacoes;
    private int quantidade;
    private float preco;
    private int fornecedorId;
    private float taxa;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getPreco() { return preco; }
    public void setPreco(float preco) { this.preco = preco; }

    public int getFornecedorId() { return fornecedorId; }
    public void setFornecedorId(int fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public float getTaxa() { return taxa; }
    public void setTaxa(float taxa) { this.taxa = taxa; }
}
