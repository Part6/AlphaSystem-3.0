/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.alphasystem.controll;

import com.api.alphasystem.dto.VendaCadastroRequest;
import com.api.alphasystem.model.Cliente;
import com.api.alphasystem.model.Produto;
import com.api.alphasystem.model.ProdutoHasVenda;
import com.api.alphasystem.repository.VendaRepository;
import java.util.List; 
import org.springframework.web.bind.annotation.*; 
import com.api.alphasystem.model.Venda;
import com.api.alphasystem.repository.ClienteRepository;
import com.api.alphasystem.repository.ProdutoHasVendaRepository;
import com.api.alphasystem.repository.ProdutoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

@RestController 
@CrossOrigin(origins = "*") 
@RequestMapping("/vendaRest") 
public class VendaControll {
    
    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ProdutoHasVendaRepository produtoHasVendaRepository;

    public VendaControll(
            VendaRepository vendaRepository,
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository,
            ProdutoHasVendaRepository produtoHasVendaRepository) {

        this.vendaRepository = vendaRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.produtoHasVendaRepository = produtoHasVendaRepository;
    }

   /* @PostMapping("/criar") 
    public Venda criarVenda(@RequestBody Venda venda) { 
        return cr.save(venda);
    }*/
    
    @PostMapping("/criar")
    public Venda criarVenda(
            @RequestBody VendaCadastroRequest request,HttpSession session) {
        
        if ("Financeiro".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return null;
        } 
        
        System.out.println(request.getQuantidadeItens());

        if (request.getClienteId() == null
                || request.getProdutoId() == null
                || request.getData() == null) {
            System.out.println(request.getClienteId());
            System.out.println(request.getProdutoId());
            System.out.println(request.getData());
            System.out.println("retorno 1");
            return null;
        }

        Cliente cliente = clienteRepository
                .findById(request.getClienteId())
                .orElse(null);

        if (cliente == null) {
            System.out.println("retorno2");
            return null;
        }

        Produto produto = produtoRepository.findById(request.getProdutoId()).orElse(null);
        produto.setQuantidade(produto.getQuantidade() - request.getQuantidadeItens());
        

        if (produto.getQuantidade() <= 0) {
            System.out.println("retorno1");
            return null;
        }
        
        produtoRepository.save(produto);

        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setData(request.getData());
        venda.setObservacoes(request.getObservacoes());
        venda.setQuantidadeItens(request.getQuantidadeItens());

        Venda vendaSalva = vendaRepository.save(venda);

        ProdutoHasVenda item = new ProdutoHasVenda();
        item.setVenda(vendaSalva);
        item.setProduto(produto);
        
        produtoHasVendaRepository.save(item);

        return vendaSalva;
    }

    @GetMapping("/buscar") 
    public List buscarVendas() { 
         return vendaRepository.findAll();
    }

    @GetMapping("/{id}") 
    public Venda buscarVenda(@PathVariable int id) { 
        return vendaRepository.findById(id).orElse(null);
    } 
    
    
    @PutMapping("/{id}") 
    public Venda atualizarVenda(@PathVariable int id, @RequestBody VendaCadastroRequest dados,HttpSession session) { 
        
        if ("Financeiro".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return null;
        } 
       
        Venda vendaExistente = vendaRepository.findById(id).orElse(null);

        Cliente cliente = clienteRepository.findById(dados.getClienteId()).orElse(null);

        Produto produto = produtoRepository.findById(dados.getProdutoId()).orElse(null);
        produto.setQuantidade(produto.getQuantidade() - dados.getQuantidadeItens());
        

        if (produto.getQuantidade() <= 0) {
            System.out.println("retorno1");
            return null;
        }
        
        if (vendaExistente == null) 
            return null;
        
        produtoRepository.save(produto);
                    
        vendaExistente.setCliente(cliente);
        vendaExistente.setData(dados.getData());
        vendaExistente.setObservacoes(dados.getObservacoes());
        vendaExistente.setQuantidadeItens(dados.getQuantidadeItens());
        vendaRepository.save(vendaExistente);
        
        List<ProdutoHasVenda> itens = produtoHasVendaRepository.findByVenda_IdVenda(id);

        ProdutoHasVenda item;

        if (itens.isEmpty()) {
            item = new ProdutoHasVenda();
            item.setVenda(vendaExistente);
            
        } else {
            item = itens.get(0);
        }
        
        item.setProduto(produto);
        

        produtoHasVendaRepository.save(item);
        
        return vendaExistente;
        
    } 

    @DeleteMapping("/{id}") 
    public boolean deletarVenda(@PathVariable int id, HttpSession session) { 
        
        if (!"Gerente".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return false;
        } 
        
        Venda venda = vendaRepository.findById(id).orElse(null);

        if (venda == null) {
            return false;
        }

        List<ProdutoHasVenda> itens = produtoHasVendaRepository.findByVenda_IdVenda(id);

        produtoHasVendaRepository.deleteAll(itens);
        vendaRepository.delete(venda);

        return true;
    }
}
