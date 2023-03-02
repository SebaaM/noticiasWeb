/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.noticias.noticiasWeb.servicios;

import com.noticias.noticiasWeb.Enumeraciones.Rol;
import com.noticias.noticiasWeb.entidades.Imagen;
import com.noticias.noticiasWeb.entidades.Usuario;
import com.noticias.noticiasWeb.exepciones.ValidacionException;
import com.noticias.noticiasWeb.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author SebaaM <sebaamartinez54@gmail.com>
 */
@Service
public class UsuarioServicio implements UserDetailsService{

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private ImagenServicio imagenServicio;
    
    @Transactional
    public void registrar(MultipartFile archivo,String nombre, String email, String password, String password2) throws ValidacionException {
        
        validar(nombre, email, password, password2);
        
        Usuario user = new Usuario();

        user.setNombre(nombre);
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRol(Rol.USER);
        
        Imagen imagen = imagenServicio.guardar(archivo);
        
        user.setImagen(imagen);
        
        usuarioRepositorio.save(user);

    }
    
    
    @Transactional
    public void modificar (MultipartFile archivo, String idUsuario, String nombre, String email, String password, String password2) throws ValidacionException {
        
        validar(nombre, email, password, password2);
        
        Optional <Usuario> respuesta = usuarioRepositorio.findById(idUsuario);
        
        if (respuesta.isPresent()){
            Usuario user = respuesta.get();
            
            user.setNombre(nombre);
            user.setEmail(email);
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            user.setRol(Rol.USER);
            
            String idImagen = null;
            if (user.getImagen() != null ){
                idImagen = user.getImagen().getId();
            }
            
            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            
            user.setImagen(imagen);
            
            usuarioRepositorio.save(user);
        }


    }
    
    
    @Transactional(readOnly = true)
    public Usuario getOne(String id) {
        return usuarioRepositorio.getOne(id);
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
        if (!password2.equals(password)) {
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
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            
            HttpSession sesion = attr.getRequest().getSession(true);
            
            sesion.setAttribute("usuariosession", user);
            
            return  new User (user.getEmail(),user.getPassword(),permisos);
            
            
            
            
            
        }
        else {
            return null;
        }
        
    }

    @Transactional(readOnly=true)
    public List<Usuario> listarNoticias() {
        
        List <Usuario> usuarios = new ArrayList();
        
        usuarios= usuarioRepositorio.findAll();
        
        return usuarios;
        
    
    
    }

    @Transactional
    public void cambiarRol(String id) {
        
        Optional <Usuario> respuesta = usuarioRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            
            Usuario usuario = respuesta.get();
            
            if (usuario.getRol().equals(Rol.USER)){
                
                usuario.setRol(Rol.ADMIN);
                
            } else if (usuario.getRol().equals(Rol.ADMIN)){
                
                usuario.setRol(Rol.USER);
            }
                
            
        }
        
    
    }

}
