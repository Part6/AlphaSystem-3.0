/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.alphasystem.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

/*
public class Cliente {
    
    */


@Entity
public class Venda {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVenda;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    
    private String observacoes;
    private LocalDate data;


    public int getIdVenda() { return idVenda; }
    public void setIdVenda(int idVenda) { this.idVenda = idVenda; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

}
