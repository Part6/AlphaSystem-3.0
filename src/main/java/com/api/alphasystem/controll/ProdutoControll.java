/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.alphasystem.controll;


import com.api.alphasystem.dto.ProdutoCadastroRequest;
import com.api.alphasystem.model.Fornecedor;
import com.api.alphasystem.model.FornecedorProduto;
import com.api.alphasystem.repository.ProdutoRepository;
import java.util.List; 
import org.springframework.web.bind.annotation.*; 
import com.api.alphasystem.model.Produto;
import com.api.alphasystem.repository.FornecedorProdutoRepository;
import com.api.alphasystem.repository.FornecedorRepository;
import com.api.alphasystem.repository.ProdutoHasVendaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController 
@CrossOrigin(origins = "*") 
@RequestMapping("/produtoRest") 
public class ProdutoControll {
    private final ProdutoRepository cr;
    private final FornecedorRepository fornecedorRepository;
    private final FornecedorProdutoRepository fornecedorProdutoRepository;
    private final ProdutoHasVendaRepository produtoHasVendaRepository;

    public ProdutoControll(
            ProdutoRepository cr,
            FornecedorRepository fornecedorRepository,
            FornecedorProdutoRepository fornecedorProdutoRepository,
            ProdutoHasVendaRepository produtoHasVendaRepository) {

        this.cr = cr;
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorProdutoRepository = fornecedorProdutoRepository;
        this.produtoHasVendaRepository = produtoHasVendaRepository;
    }

    @PostMapping("/criar")
    public Produto criarProduto(
            @RequestBody ProdutoCadastroRequest dados) {

        // 1. Busca o fornecedor que foi selecionado
        Fornecedor fornecedor = fornecedorRepository.findById(dados.getFornecedorId()).orElse(null);
        
        if (fornecedor == null) {
            System.out.println("retorno1");
            return null;
        }
        
        System.out.println(dados.getFornecedorId());
        System.out.println(dados.getTaxa());
        System.out.println(dados.getPreco());
        System.out.println(dados.getCategoria());
        System.out.println(dados.getObservacoes());
        
        
        
        if (dados.getTaxa() <= 0
                || dados.getPreco() <= 0
                || dados.getNome() == null
                || dados.getCategoria() == null
                || dados.getObservacoes() == null
                ) {
            System.out.println("retorno 2");
            return null;
        }
            

        

        // 2. Monta e salva o produto
        Produto produto = new Produto();
        produto.setNome(dados.getNome());
        produto.setCategoria(dados.getCategoria());
        produto.setObservacoes(dados.getObservacoes());
        produto.setQuantidade(dados.getQuantidade());
        produto.setPreco(dados.getPreco());
        
        Produto produtoSalvo = cr.save(produto);
        
        System.out.println("ID gerado: " + produtoSalvo.getId());

        // 3. Cria a associação entre o produto e o fornecedor
        FornecedorProduto associacao = new FornecedorProduto();
        associacao.setProduto(produtoSalvo);
        associacao.setFornecedor(fornecedor);
        associacao.setTaxa(dados.getTaxa());

        fornecedorProdutoRepository.save(associacao);

        // 4. Retorna o produto criado
        ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
        return produtoSalvo;
    }

    @GetMapping("/buscar") 
    public List buscarProdutos() { 
         return cr.findAll();
    }

    @GetMapping("/{id}") 
    public Produto buscarProduto(@PathVariable int id) { 
        return cr.findById(id).orElse(null);
    } 
    
    
    @PutMapping("/{id}") 
    public Produto atualizarProduto(@PathVariable int id, @RequestBody ProdutoCadastroRequest dados) { 
        
        Produto produtoExistente = cr.findById(id).orElse(null);
        Fornecedor fornecedor = fornecedorRepository.findById(dados.getFornecedorId()).orElse(null);
             
        if (produtoExistente == null) 
            return null;
        
        
        produtoExistente.setNome(dados.getNome());
        produtoExistente.setCategoria(dados.getCategoria());
        produtoExistente.setObservacoes(dados.getObservacoes());
        produtoExistente.setPreco(dados.getPreco());
        produtoExistente.setQuantidade(dados.getQuantidade());
        cr.save(produtoExistente);  
        
        FornecedorProduto associacao = fornecedorProdutoRepository.findFirstByProduto_IdOrderByIdAsc(id).orElse(null);
        if (associacao == null) {
            associacao = new FornecedorProduto();
            associacao.setProduto(produtoExistente);
        }
        
        associacao.setFornecedor(fornecedor);
        associacao.setTaxa(dados.getTaxa());

        fornecedorProdutoRepository.save(associacao);
        
        return produtoExistente;
        
    } 
    
    

    @DeleteMapping("/{id}") 
    public boolean deletarProduto(@PathVariable int id) { 
        
        
        
        Produto produto = cr.findById(id)
            .orElse(null);

        if (produto == null) {
            return false;
        }
        

        if (produtoHasVendaRepository.existsByProduto_Id(id)) {
            System.out.print("Não é possível excluir este produto, " + "pois ele possui vendas vinculadas.");
            return false;
        }
        
        List<FornecedorProduto> associacoes = fornecedorProdutoRepository.findByProdutoId(id);

        fornecedorProdutoRepository.deleteAll(associacoes);

        cr.delete(produto);

        return true;
    } 
    
    }
