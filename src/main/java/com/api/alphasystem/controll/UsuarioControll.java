/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.alphasystem.controll;

import com.api.alphasystem.repository.UsuarioRepository;
import java.util.List; 
import org.springframework.web.bind.annotation.*; 
import com.api.alphasystem.model.Usuario;
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
    public Usuario criarUsuario(@RequestBody Usuario usuario) { 
        
        return cr.save(usuario);
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
    public Usuario atualizarUsuario(@PathVariable int id, @RequestBody Usuario usuario) { 
        
        Usuario usuarioExistente = cr.findById(id).orElse(null);
        
        if (usuarioExistente != null) {
        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setLogin(usuario.getLogin());
        usuarioExistente.setSenha(usuario.getSenha());
        usuarioExistente.setTipo(usuario.getTipo());
        cr.save(usuarioExistente);
        }
        return usuarioExistente;
        
    } 

    @DeleteMapping("/{id}") 
    public boolean deletarUsuario(@PathVariable int id) { 
        if (cr.existsById(id)) {

            cr.deleteById(id);

            return true;
        }

        return false;
    } 
}
