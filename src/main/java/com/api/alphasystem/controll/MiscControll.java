 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.alphasystem.controll;

import com.api.alphasystem.dto.VendaCadastroRequest;
import com.api.alphasystem.model.*;
import com.api.alphasystem.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
 

import jakarta.servlet.http.HttpServletResponse;
//import com.api.alphasystem.model.Preferencia;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller 
public class MiscControll {
     private final ClienteRepository clienteRepository;
     private final ProdutoRepository produtoRepository;
     private final UsuarioRepository usuarioRepository;
     private final FornecedorRepository fornecedorRepository;
     private final VendaRepository vendaRepository;
     private final FornecedorProdutoRepository fornecedorProdutoRepository;
     private final ProdutoHasVendaRepository produtoHasVendaRepository;
     private final EnderecoRepository enderecoRepository;

    public MiscControll(ClienteRepository clienteRepository,
                        ProdutoRepository produtoRepository,
                        UsuarioRepository usuarioRepository,
                        VendaRepository vendaRepository,
                        FornecedorRepository fornecedorRepository,
                        FornecedorProdutoRepository fornecedorProdutoRepository,
                        ProdutoHasVendaRepository produtoHasVendaRepository,
                        EnderecoRepository enderecoRepository) {
        
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
        this.vendaRepository = vendaRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorProdutoRepository = fornecedorProdutoRepository;
        this.produtoHasVendaRepository = produtoHasVendaRepository;
        this.enderecoRepository = enderecoRepository;
    }

    // =========================
    // CADASTRO
    // =========================

    @GetMapping("/")
    public String paginaInicial(@CookieValue(name="pref-nome", defaultValue="")String nome, @CookieValue(name="pref-estilo", defaultValue="claro")String tema,
            Model model) { 
        model.addAttribute("css", tema);
        model.addAttribute("nome", nome);
        return "index";
    }
    
    @RequestMapping("/preferencias") 
    public String preferencias(@CookieValue(name="pref-nome", defaultValue="")String nome, @CookieValue(name="pref-estilo", defaultValue="claro")String tema,Model model){
        model.addAttribute("nome", nome); 
        model.addAttribute("css", tema);
        return "preferencias"; 
    }
    
    @PostMapping("/preferencias") 
    public ModelAndView gravaPreferencias(@ModelAttribute Preferencia pref, HttpServletResponse response){ 

        Cookie cookiePrefNome = new Cookie("pref-nome", pref.getNome()); 
        cookiePrefNome.setDomain("localhost"); //disponível apenas no domínio "localhost" 
        cookiePrefNome.setHttpOnly(true); //acessível apenas por HTTP, JS não 
        cookiePrefNome.setMaxAge(86400); //1 dia 
        

        response.addCookie(cookiePrefNome); 

        Cookie cookiePrefEstilo = new Cookie("pref-estilo", pref.getEstilo()); 
        cookiePrefEstilo.setDomain("localhost"); //disponível apenas no domínio "localhost" 
        cookiePrefEstilo.setHttpOnly(true); //acessível apenas por HTTP, JS não 
        cookiePrefEstilo.setMaxAge(86400); //1 dia 

        response.addCookie(cookiePrefEstilo); 

        return new ModelAndView("redirect:/"); //"index"; 
    }
    
    @GetMapping("/login")
    public String login(@CookieValue(name="pref-nome", defaultValue="")String nome, @CookieValue(name="pref-estilo", defaultValue="claro")String tema,
            Model model) { 
        model.addAttribute("css", tema);
        model.addAttribute("usuario", new Usuario());
        return "login";
    }
    
    @GetMapping("/menu")
    public String menu(@CookieValue(name="pref-nome", defaultValue="")String nome, @CookieValue(name="pref-estilo", defaultValue="claro")String tema,
            Model model,HttpSession session) { 
       model.addAttribute("css", tema);
       model.addAttribute("cargo", session.getAttribute("usuarioTipo").toString());
       model.addAttribute("nome", session.getAttribute("usuarioNome").toString()); //
       
        return "menu";
    }
    
    @GetMapping("/menu/menuCadastro")
    public String menuCadastro(@CookieValue(name="pref-nome", defaultValue="")String nome, @CookieValue(name="pref-estilo", defaultValue="claro")String tema,
            Model model) { 
       model.addAttribute("css", tema);
        return "menu-cadastro";
    }
    
    @GetMapping("/menu/menuConsulta")
    public String menuConsulta(@CookieValue(name="pref-nome", defaultValue="")String nome, @CookieValue(name="pref-estilo", defaultValue="claro")String tema,
            Model model) { 
       model.addAttribute("css", tema);
        return "menu-consulta";
    }
    
    @GetMapping("/menu/menuGerente")
    public String menuGerente(@CookieValue(name="pref-nome", defaultValue="")String nome, @CookieValue(name="pref-estilo", defaultValue="claro")String tema,
            Model model,HttpSession session) {
        
        if (!"Gerente".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return "menu";
        }    
       model.addAttribute("css", tema);
        return "menu-gerente";
    }
    
    @GetMapping("/menu/menuGerente/ListaFornecedor")
    public String GerenteFornecedor(@CookieValue(name="pref-nome", defaultValue="")String nome, @CookieValue(name="pref-estilo", defaultValue="claro")String tema,
            Model model,HttpSession session) {
        
        if (!"Gerente".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return null;
        }    
       model.addAttribute("css", tema);
       
        model.addAttribute("fornecedores", fornecedorRepository.findAll());
        return "consulta-fornecedor";
    }
    
    @GetMapping("/menu/menuGerente/ListaUsuario")
    public String GerenteUsuario(@CookieValue(name="pref-nome", defaultValue="")String nome, @CookieValue(name="pref-estilo", defaultValue="claro")String tema,
            Model model,HttpSession session) {
        
        if (!"Gerente".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return "menu";
        }    
        model.addAttribute("css", tema);
        List<Usuario> usuario = usuarioRepository.findAll();

        model.addAttribute("usuarios", usuario);
        return "consulta-usuario";
    }
    
    // CADASTRO DE CLIENTE
    @GetMapping("/menu/menuCadastro/cadastroCliente")
    public String cadastroCliente(@CookieValue(name="pref-nome", defaultValue="")String nome, @CookieValue(name="pref-estilo", defaultValue="claro")String tema,
            Model model,HttpSession session) {
        
        if ("Financeiro".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return "menu-cadastro";
        } 
        
        model.addAttribute("css", tema);
       
        Cliente cliente = new Cliente();
        Endereco endereco = new Endereco();
        cliente.setEndereco(endereco);
       
        model.addAttribute("cliente", cliente);
        model.addAttribute("endereco", endereco);
        return "cadastro-clientes";
    }
    
    //CADASTRO DE PRODUTO
    @GetMapping("/menu/menuCadastro/cadastroProduto")
    public String cadastroProduto(@CookieValue(name="pref-nome", defaultValue="")String nome, @CookieValue(name="pref-estilo", defaultValue="claro")String tema,
            Model model,HttpSession session) {
        
        if ("Financeiro".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return "menu-cadastro";
        } 
        
        model.addAttribute("css", tema);
        model.addAttribute("produto", new Produto());
        model.addAttribute("fornecedores", fornecedorRepository.findAll());
        
        return "cadastro-produtos";
    }
    
    //CADASTRO DE FORNECEDOR
    @GetMapping("/menu/menuCadastro/cadastroFornecedor")
    public String cadastroFornecedor(@CookieValue(name="pref-nome", defaultValue="")String nome, @CookieValue(name="pref-estilo", defaultValue="claro")String tema,
            Model model,HttpSession session) {
        
        if ("Atendente".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return "menu-cadastro";
        }  
       model.addAttribute("css", tema);
        model.addAttribute("fornecedor", new Fornecedor());
        return "cadastro-fornecedores";
    }
    
    @PostMapping("/menu/menuCadastro/cadastroFornecedor")
    public String processarFornecedor(
            @ModelAttribute("fornecedor") Fornecedor fornecedor) {

        fornecedorRepository.save(fornecedor);

        return "redirect:/menu/menuCadastro";
    }
    
    //CADASTRO DE USUARIO
    @GetMapping("/menu/menuCadastro/cadastroUsuario")
    public String cadastroUsuario(@CookieValue(name="pref-nome", defaultValue="")String nome, @CookieValue(name="pref-estilo", defaultValue="claro")String tema,
            Model model,HttpSession session) {
        
        if (!"Gerente".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return "menu-cadastro";
        }  
       model.addAttribute("css", tema);
        model.addAttribute("usuario", new Usuario());
        return "cadastro-usuarios";
    }

    
    //CADASTRO DE VENDA
    @GetMapping("/menu/menuCadastro/cadastroVenda")
    public String cadastroVenda(@CookieValue(name="pref-nome", defaultValue="")String nome, @CookieValue(name="pref-estilo", defaultValue="claro")String tema,
            Model model,HttpSession session) {
        
        if ("Financeiro".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return "menu-cadastro";
        } 
        model.addAttribute("css", tema);
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("produtos", produtoRepository.findAll());
        model.addAttribute("venda", new Venda());
        model.addAttribute("ProdutoHasVenda", new ProdutoHasVenda());
        return "registro-vendas";
    }
    
    /*@PostMapping("/menu/menuCadastro/cadastroVenda")
    public String processarVenda(
            @ModelAttribute("venda") Venda venda) {

        vendaRepository.save(venda);

        return "redirect:/menu/menuCadastro";
    }*/
    
    //CONSULTA DE CLIENTES
    
    @GetMapping("/menu/menuConsulta/consultaClientes")
    public String consultaCliente(@CookieValue(name="pref-nome", defaultValue="")String nome, @CookieValue(name="pref-estilo", defaultValue="claro")String tema,
            Model model,HttpSession session) {
        
        if ("Financeiro".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return "menu-consulta";
        }   
       model.addAttribute("css", tema);
       List<Cliente> clientes = clienteRepository.findAll();

        model.addAttribute("clientes", clientes);
        return "consulta-clientes";
    }
    
    //CONSULTA DE PRODUTOS
    @GetMapping("/menu/menuConsulta/consultaProdutos")
    public String consultaProdutos(@CookieValue(name="pref-nome", defaultValue="")String nome, @CookieValue(name="pref-estilo", defaultValue="claro")String tema,
            Model model,HttpSession session) {
        
        if ("Atendente".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return "menu-consulta";
        }   
       model.addAttribute("css", tema);
       List<Produto> produtos = produtoRepository.findAll();

         model.addAttribute("produtos", produtos);
        model.addAttribute("associacoes",fornecedorProdutoRepository.findAll()
        );
        return "consulta-produtos";
    }
    
    //CONSULTA DE VENDAS
    @GetMapping("/menu/menuConsulta/consultaVendas")
    public String consultaVendas(@CookieValue(name="pref-nome", defaultValue="")String nome, @CookieValue(name="pref-estilo", defaultValue="claro")String tema,
            Model model,HttpSession session) {
        
        if ("Atendente".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return "menu-consulta";
        }  
        
        model.addAttribute("css", tema);

        model.addAttribute("itensVenda",produtoHasVendaRepository.findAll());
        return "consulta-vendas";
    }
    
    // EDIÇÃO DE CLIENTE
    @GetMapping("/editar/cliente/{id}")
    public String EditarCliente(@CookieValue(name="pref-nome", defaultValue="")String nome, @CookieValue(name="pref-estilo", defaultValue="claro")String tema,
            @PathVariable Integer id,Model model,HttpSession session) {
        
        if ("Financeiro".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return null;
        }  
       model.addAttribute("css", tema);
       
        Cliente cliente = clienteRepository.findById(id).orElse(null);
       
        Endereco endereco = enderecoRepository.findById(cliente.getEndereco().getId()).orElse(null);
        cliente.setEndereco(endereco);
        
        model.addAttribute("cliente", cliente);
        model.addAttribute("endereco", endereco);
        return "editar-clientes";
    }
    
    //EDIÇÃO DE PRODUTO
    @GetMapping("/editar/produtos/{id}")
    public String EditarProduto(@CookieValue(name="pref-nome", defaultValue="")String nome, @CookieValue(name="pref-estilo", defaultValue="claro")String tema,
            @PathVariable Integer id,Model model,HttpSession session) {
        
        if ("Financeiro".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return null;
        }   
        
        Produto produto = produtoRepository.findById(id).orElse(null);
        
        model.addAttribute("css", tema);
        model.addAttribute("produto", produto);
        model.addAttribute("fornecedores", fornecedorRepository.findAll());
        
        return "editar-produtos";
    } 
      
    //EDIÇÃO DE VENDA
    @GetMapping("/editar/vendas/{id}")
    public String editarVenda(@CookieValue(name="pref-nome", defaultValue="")String nome, @CookieValue(name="pref-estilo", defaultValue="claro")String tema,
             @PathVariable Integer id,Model model,HttpSession session) {
        
        if ("Financeiro".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return null;
        }  
        model.addAttribute("css", tema);
        
        Venda venda = vendaRepository.findById(id).orElse(null);
        
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("produtos", produtoRepository.findAll());
        model.addAttribute("venda", venda);
        return "editar-vendas";
    }
    
    @GetMapping("/editar/fornecedor/{id}")
    public String editarFornecedor(@CookieValue(name="pref-nome", defaultValue="")String nome, @CookieValue(name="pref-estilo", defaultValue="claro")String tema,
             @PathVariable Integer id,Model model,HttpSession session) {
        
        if ("Atendente".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return null;
        }   
        model.addAttribute("css", tema);
        
        Fornecedor fornecedor = fornecedorRepository.findById(id).orElse(null);
        
        model.addAttribute("fornecedor", fornecedor);
        return "editar-fornecedores";
    }
    
    @GetMapping("/editar/usuario/{id}")
    public String editarUsuario(@CookieValue(name="pref-nome", defaultValue="")String nome, @CookieValue(name="pref-estilo", defaultValue="claro")String tema,
             @PathVariable Integer id,Model model,HttpSession session) {
        
        if (!"Gerente".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return null;
        }   
        model.addAttribute("css", tema);
        
        Usuario Usuario = usuarioRepository.findById(id).orElse(null);
        
        model.addAttribute("usuario", Usuario);
        return "editar-usuarios";
    }
    
}
