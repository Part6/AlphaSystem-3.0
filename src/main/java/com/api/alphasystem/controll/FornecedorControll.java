/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.alphasystem.controll;

import com.api.alphasystem.repository.FornecedorRepository;
import java.util.List; 
import org.springframework.web.bind.annotation.*; 
import com.api.alphasystem.model.Fornecedor;
import com.api.alphasystem.repository.FornecedorProdutoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;


@RestController 
@CrossOrigin(origins = "*") 
@RequestMapping("/fornecedorRest") 

public class FornecedorControll {
    
    private final FornecedorRepository cr;
    private final FornecedorProdutoRepository fornecedorProdutoRepository;
    
    public FornecedorControll(FornecedorRepository cr,
            FornecedorProdutoRepository fornecedorProdutoRepository) {
        this.cr = cr;
        this.fornecedorProdutoRepository = fornecedorProdutoRepository;
    }

    @PostMapping("/criar") 
    public Fornecedor criarFornecedor(@RequestBody Fornecedor fornecedor,HttpSession session) { 
         if ("Atendente".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return null;
        } 
        
        return cr.save(fornecedor);
    }

    @GetMapping("/buscar") 
    public List buscarFornecedors() { 
         return cr.findAll();
    }

    @GetMapping("/{id}") 
    public Fornecedor buscarFornecedor(@PathVariable int id) { 
        return cr.findById(id).orElse(null);
    } 
    
    
    @PutMapping("/{id}") 
    public Fornecedor atualizarFornecedor(@PathVariable int id, @RequestBody Fornecedor fornecedor,HttpSession session) { 
        if ("Atendente".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return null;
        } 
        
        Fornecedor fornecedorExistente = cr.findById(id).orElse(null);
        
        if (fornecedorExistente != null) {
        fornecedorExistente.setNome(fornecedor.getNome());
        fornecedorExistente.setLocal(fornecedor.getLocal());
        cr.save(fornecedorExistente);
        }
        return fornecedorExistente;
        
    } 

    @DeleteMapping("/{id}") 
    public boolean deletarFornecedor(@PathVariable int id,HttpSession session) { 
        
        if (!"Gerente".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return false;
        } 
        
        Fornecedor fornecedor = cr.findById(id).orElse(null);

        if (fornecedor == null) {
           return false;
        }

        if (fornecedorProdutoRepository.existsByFornecedor_Id(id)) {
            System.out.print("Não é possível excluir este fornecedor: " + "existem produtos vinculados a ele.");
            return false;
        }

        cr.delete(fornecedor);
        return true;
    } 

    
}
