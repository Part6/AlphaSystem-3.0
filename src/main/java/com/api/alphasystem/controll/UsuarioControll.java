/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.alphasystem.controll;

import com.api.alphasystem.repository.UsuarioRepository;
import java.util.List; 
import org.springframework.web.bind.annotation.*; 
import com.api.alphasystem.model.Usuario;
import jakarta.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
/*

@RestController 
@CrossOrigin(origins = "*") 
@RequestMapping("/usuarioRest") 
public class UsuarioControll { 
    
    
 */
@RestController 
@CrossOrigin(origins = "*") 
@RequestMapping("/usuarioRest") 
public class UsuarioControll {
    private final UsuarioRepository cr;
    
    public UsuarioControll(UsuarioRepository cr) {
        this.cr = cr;
    }

    @PostMapping("/criar") 
    public Usuario criarUsuario(@RequestBody Usuario usuario, HttpSession session) { 
        
        if (!"Gerente".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return null;
        } 
        
        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            System.out.print("A senha é obrigatória");
            return null;
        }
        String md5Hex = DigestUtils.md5DigestAsHex(usuario.getSenha().getBytes(StandardCharsets.UTF_8));
        System.out.print(md5Hex);
        usuario.setSenha(md5Hex);
        
        Usuario salvo = cr.save(usuario);
        return salvo;
    }
    
    @PostMapping("/login")
    public boolean autenticar(@RequestBody Usuario credenciais, HttpSession session) {
        if (credenciais.getLogin() == null || credenciais.getSenha() == null) {
            System.out.print("Informe login e senha.");
            ResponseEntity.status(HttpStatus.UNAUTHORIZED);
            return false;
        }

        Optional<Usuario> encontrado = cr.findByLogin(credenciais.getLogin().trim());
        if (encontrado.isEmpty()) {
            System.out.print("Login ou senha inválidos.");
            ResponseEntity.status(HttpStatus.UNAUTHORIZED);
            return false;
        }

        Usuario usuario = encontrado.get();
        String hashInformado =  DigestUtils.md5DigestAsHex(credenciais.getSenha().getBytes(StandardCharsets.UTF_8));
        
        System.out.print("hash: "+hashInformado);
        System.out.print("senha: "+credenciais.getSenha());
        // Compatibilidade temporária com usuários cadastrados antes do hash:
        // após o primeiro login correto, a senha é convertida para MD5.
        if (usuario.getSenha() != null && usuario.getSenha().equals(credenciais.getSenha())) {
            usuario.setSenha(hashInformado);
            cr.save(usuario);
        } else if (usuario.getSenha() == null || !hashInformado.equalsIgnoreCase(usuario.getSenha())) {
            System.out.print("Login ou senha inválidos.");
            ResponseEntity.status(HttpStatus.UNAUTHORIZED);
            return false;
        }
        
        session.setAttribute("usuarioId", usuario.getId());
        session.setAttribute("usuarioNome", usuario.getNome());
        session.setAttribute("usuarioTipo", usuario.getTipo());

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("id", usuario.getId());
        resposta.put("nome", usuario.getNome());
        resposta.put("tipo", usuario.getTipo());
        ResponseEntity.ok(resposta);

        return true;
    }

    @GetMapping("/buscar") 
    public List buscarUsuarios() { 
         return cr.findAll();
    }

    @GetMapping("/{id}") 
    public Usuario buscarUsuario(@PathVariable int id) { 
        return cr.findById(id).orElse(null);
    } 
    
    
    @PutMapping("/{id}") 
    public Usuario atualizarUsuario(@PathVariable int id, @RequestBody Usuario usuario, HttpSession session) { 
        if (!"Gerente".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return null;
        } 
        Usuario existente = cr.findById(id).orElse(null);
        if (existente == null) {
            return null;
        }

        existente.setNome(usuario.getNome());
        existente.setLogin(usuario.getLogin());
        existente.setTipo(usuario.getTipo());
        if (usuario.getSenha() != null && !usuario.getSenha().isBlank()) {
            String senha =  DigestUtils.md5DigestAsHex(usuario.getSenha().getBytes(StandardCharsets.UTF_8));
            existente.setSenha(senha);
        }
        return cr.save(existente);
        
    } 

    @DeleteMapping("/{id}") 
    public boolean deletarUsuario(@PathVariable int id, HttpSession session) { 
        
        if (!"Gerente".equals(session.getAttribute("usuarioTipo").toString())) {
            ResponseEntity.status(403).body("Você não tem permissão para atualizar produtos.");
            return false;
        } 
         
        if (cr.existsById(id)) {

            cr.deleteById(id);

            return true;
        }

        return false;
    } 
}
