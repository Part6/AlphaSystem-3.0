package com.api.alphasystem.dto;

import java.time.LocalDate;

public class VendaCadastroRequest {

    private Integer clienteId;
    private Integer produtoId;
    private LocalDate data;
    private String observacoes;
    private int quantidadeItens;

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }


    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    public int getQuantidadeItens(){
        return this.quantidadeItens;
    }
    
    public void setQuantidadeItens(int qnt){
        this.quantidadeItens = qnt;
    }
}