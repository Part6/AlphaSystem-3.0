/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.alphasystem.repository;

/**
 *
 * @author Desktop
 */

import com.api.alphasystem.model.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Desktop
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
     Optional<Usuario> findByLogin(String login);
    //public void inserir(Usuario p);
    // public Usuario validarUsuarioSeguro(Usuario Usuario);
}
