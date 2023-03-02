/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.noticias.noticiasWeb.controladores;

import com.noticias.noticiasWeb.entidades.Noticia;
import com.noticias.noticiasWeb.entidades.Usuario;
import com.noticias.noticiasWeb.exepciones.ValidacionException;
import com.noticias.noticiasWeb.servicios.NoticiaServicio;
import com.noticias.noticiasWeb.servicios.UsuarioServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author SebaaM <sebaamartinez54@gmail.com>
 */
@Controller
@RequestMapping("/")
public class NoticiaControlador {

    @Autowired
    private NoticiaServicio noticiaServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/noticia/nueva")
    public String noticia() {

        return "noticia_crear.html";
    }

    @PostMapping("/noticia/terminado")
    public String terminado(@RequestParam String titulo, @RequestParam(required = false) String cuerpo, ModelMap model) {
        //System.out.println("En /terminado prueba de parametros:" + titulo +" y cuerpo: "+ cuerpo );
        try {

            noticiaServicio.crearNoticia(titulo, cuerpo);
            model.put("exito", "La noticia fue agregada con exito.");

        } catch (ValidacionException e) {

            model.put("error", e.getMessage());
            Logger.getLogger(NoticiaControlador.class.getName()).log(Logger.Level.WARN, e);

            return "noticia_crear.html";
        }
        return "noticia_crear.html";
    }

    @GetMapping("/noticia/lista")
    public String listar(ModelMap modelo) {

        List<Noticia> noticias = noticiaServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);

        return "noticia_listar.html";
    }

    @GetMapping("/noticia/modificar/{id}")
    public String modificar(@PathVariable Long id, ModelMap modelo) {

        modelo.put("noticia", noticiaServicio.getOne(id));

        return "noticia_modificar.html";

    }

    @PostMapping("/noticia/modificar/{id}")
    public String modificar(@PathVariable Long id, String titulo, String cuerpo, ModelMap modelo) {

        try {

            noticiaServicio.modificarNoticia(id, titulo, cuerpo);
            modelo.put("exito", "La noticia fue cargado correctamente!");

            return "redirect:../lista";
        } catch (ValidacionException e) {

            modelo.put("error", e.getMessage());

            return "noticia_modificar.html";
        }
    }

    @PostMapping("/noticia/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {

        try {
            System.out.println("El id a eliminar: " + id);
            noticiaServicio.eliminar(id);

        } catch (ValidacionException e) {

            System.out.println("ERROR AL ELIMINAR ");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "redirect:../lista";
        }

        return "redirect:../lista";

    }

    @GetMapping("/noticia/eliminar/{id}")
    public String delete(@PathVariable Long id, ModelMap model) {

        model.put("noticia", noticiaServicio.getOne(id));

        return "noticia_eliminar.html";

    }

    /**
     *
     * CONTROLADOR PORTAL
     *
     */
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        return "inicio.html";
    }

   
    
    @GetMapping("/")
    public String index(HttpSession session) {

        return "inicio.html";
    }

    @GetMapping("/registrar")
    public String register() {

        return "register.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap model) {

        if (error != null) {
            model.put("error", "Usuario o Contraseña invalidos..");
        }

        return "login.html";
    }

    @PostMapping("/registroUser")
    public String registrarUsuario(ModelMap model, @RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam String password2, MultipartFile archivo) {

        try {
            usuarioServicio.registrar(archivo, nombre, email, password, password2);

            model.put("exito", "Usuario registrado correctamente!");
            return "inicio.html";
        } catch (ValidacionException e) {

            model.put("error", e.getMessage());
            model.put("nombre", nombre);
            model.put("email", email);
            //Contraseñas no se autorellenan.

            return "register.html";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String perfil(ModelMap model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        model.put("usuario", usuario);
        
        return "usuario_modificar.html";

    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/perfil/{id}")
    public String actualizarUsuario(ModelMap model,@PathVariable String id, @RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam String password2, MultipartFile archivo) {

        try {
            usuarioServicio.modificar(archivo, id, nombre, email, password, password2);

            model.put("exito", "Usuario registrado correctamente!");
            return "inicio.html";
        } catch (ValidacionException e) {

            model.put("error", e.getMessage());
            model.put("nombre", nombre);
            model.put("email", email);
            //Contraseñas no se autorellenan.

            return "usuario_modificar.html";
        }
    }
    

    
    

    /**
     *
     * CONTROLADOR ADMIN
     *
     */
    @GetMapping("/admin/dashboard")
    public String panelAdmin() {

        return "panelAdmin.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/perfil/lista")
    public String listaUsuarios(ModelMap modelo) {

        List<Usuario> usuarios = usuarioServicio.listarNoticias();
        modelo.addAttribute("usuarios", usuarios);

        return "usuario_lista.html";
    }
    
    @GetMapping("/modificarRol/{id}")
    public String cambiarRol (@PathVariable String id){
        usuarioServicio.cambiarRol(id);
        
        return "redirect:/perfil/lista";
    }
    
    
    
    @DeleteMapping
    public void borrarUsuario (){
        
    }
    
    

    /**
     *
     * CONTROLADOR IMAGENES
     *
     */
    @GetMapping("/imagen/perfil/{id}")
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable String id) {
        Usuario usuario = usuarioServicio.getOne(id);

        byte[] imagen = usuario.getImagen().getContenido();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);

    }

    /**
     *
     * MAIL SENDER
     *
     */
    // String from = "sender@gmail.com";//dirección de correo que hace el envío.
    // String to = "recipient@gmail.com";//dirección de correo que recibe el mail.
    @PostMapping("/enviarEmail")
    public void sendEmail(String from, String to) {

        noticiaServicio.sendEmail(from, to);
    }

}
