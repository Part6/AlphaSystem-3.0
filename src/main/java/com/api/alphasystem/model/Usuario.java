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
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String senha;
    private String nome;
    private String tipo;
    
    public int getId(){return id;}
    public void setId(int id){this.id = id;}
    
    public String getLogin(){return login;}
    public void setLogin(String login){this.login = login;}
    
    public String getSenha(){return senha;}
    public void setSenha(String senha){this.senha = senha;}
    
    public String getNome(){return nome;}
    public void setNome(String nome){this.nome = nome;}
    
    public String getTipo(){return tipo;}
    public void setTipo(String tipo){this.tipo = tipo;}
}
