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
@Entity
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)*/
@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String rua;
    private String cidade;
    private String estado;
    private int numero;
    private int cep;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public int getCep() { return cep; }
    public void setCep(int cep) { this.cep = cep; }
}
