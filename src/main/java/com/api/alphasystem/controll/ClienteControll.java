/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.alphasystem.controll;

/**
 *
 * @author Desktop
 */


import com.api.alphasystem.repository.ClienteRepository;
import java.util.List; 
import org.springframework.web.bind.annotation.*; 
import com.api.alphasystem.model.Cliente;
import com.api.alphasystem.model.Endereco;
import com.api.alphasystem.repository.EnderecoRepository;
import com.api.alphasystem.repository.VendaRepository;


@RestController 
@CrossOrigin(origins = "*") 
@RequestMapping("/clienteRest") 
public class ClienteControll { 
    
    private final ClienteRepository cr;
    private final EnderecoRepository er;
    private final VendaRepository vendaRepository;
    

    public ClienteControll(
            ClienteRepository cr,
            EnderecoRepository er,
            VendaRepository vendaRepository) {
        this.cr = cr;
        this.er = er;
        this.vendaRepository = vendaRepository;
    }

    @PostMapping("/criar") 
    public Cliente criarCliente(@RequestBody Cliente cliente) { 
        if (cliente.getEndereco() != null) {
            Endereco enderecoSalvo = er.save(cliente.getEndereco());

            cliente.setEndereco(enderecoSalvo);
        }
        return cr.save(cliente);
    }

    @GetMapping("/buscar") 
    public List buscarClientes() { 
         return cr.findAll();
    }

    @GetMapping("/{id}") 
    public Cliente buscarCliente(@PathVariable int id) { 
        return cr.findById(id).orElse(null);
    } 
    
    
    @PutMapping("/{id}") 
    public Cliente atualizarCliente(@PathVariable int id, @RequestBody Cliente cliente) { 
        
        Cliente clienteExistente = cr.findById(id).orElse(null);
        if(clienteExistente == null)
            return null;
        
        clienteExistente.setNome(cliente.getNome());
        clienteExistente.setCpf(cliente.getCpf());
        clienteExistente.setEmail(cliente.getEmail());
        
        Endereco enderecoExistente = er.findById(id).orElse(null);
        if(enderecoExistente == null)
            return null;
        
        enderecoExistente.setCep(cliente.getEndereco().getCep());
        enderecoExistente.setCidade(cliente.getEndereco().getCidade());
        enderecoExistente.setEstado(cliente.getEndereco().getEstado());
        enderecoExistente.setNumero(cliente.getEndereco().getNumero());
        enderecoExistente.setRua(cliente.getEndereco().getRua());
        
        er.save(enderecoExistente);
        
        clienteExistente.setEndereco(enderecoExistente);
        cr.save(clienteExistente);
        
        return clienteExistente;
        
    } 

    @DeleteMapping("/{id}") 
    public boolean deletarCliente(@PathVariable int id) { 
         
        Cliente cliente = cr.findById(id).orElse(null);

        if (cliente == null) {
            return false;
        }

        if (vendaRepository.existsByCliente_Id(id)) {
            System.out.print("Não é possível excluir este cliente: "
                        + "existem vendas vinculadas a ele.");
            return false;
        }

        cr.delete(cliente);
        return true;
    } 
} 
