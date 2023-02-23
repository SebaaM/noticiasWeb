/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.noticias.noticiasWeb.servicios;

import com.noticias.noticiasWeb.Enumeraciones.Rol;
import com.noticias.noticiasWeb.entidades.Usuario;
import com.noticias.noticiasWeb.exepciones.ValidacionException;
import com.noticias.noticiasWeb.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author SebaaM <sebaamartinez54@gmail.com>
 */
@Service
public class UsuarioServicio implements UserDetailsService{

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public void registrar(String nombre, String email, String password, String password2) throws ValidacionException {
        
        validar(nombre, email, password, password2);
        
        Usuario user = new Usuario();

        user.setNombre(nombre);
        user.setEmail(email);
        user.setPassword(password);
        user.setRol(Rol.USER);

        usuarioRepositorio.save(user);

    }

    private void validar(String nombre, String email, String password, String password2) throws ValidacionException {

        if (nombre == null || nombre.isEmpty()) {
            throw new ValidacionException("El nombre del Usuario no puede ser nulo o estar vacio");
        }
        if (email == null || email.isEmpty()) {
            throw new ValidacionException("El email del Usuario no puede ser nulo o estar vacio");
        }
        if (password == null || password.isEmpty() || password.length() <=5) {
            throw new ValidacionException("La contraseña del Usuario no puede ser nulo o estar vacio y debe tener mas de 5 digitos");
        }
        if (password2.equals(password)) {
            throw new ValidacionException("Las contraseñas del Usuario deben ser iguales.");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario user = usuarioRepositorio.buscarPorEmail(email);
        
        if (user != null) {
            
            List <GrantedAuthority> permisos = new ArrayList ();
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+ user.getRol().toString()); //ROLE_USER
            
            permisos.add(p);
            
            return  new User (user.getEmail(),user.getPassword(),permisos);
            
        }
        else {
            return null;
        }
        
    }

}
