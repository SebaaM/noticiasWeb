/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.noticias.noticiasWeb.controladores;

import com.noticias.noticiasWeb.entidades.Noticia;
import com.noticias.noticiasWeb.exepciones.ValidacionException;
import com.noticias.noticiasWeb.servicios.NoticiaServicio;
import java.util.List;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author SebaaM <sebaamartinez54@gmail.com>
 */
@Controller
@RequestMapping("/")
public class NoticiaControlador {

    @Autowired
    private NoticiaServicio noticiaServicio;

    @GetMapping("/noticia")
    public String noticia() {

        return "noticia.html";
    }

    @PostMapping("/terminado")
    public String terminado(@RequestParam String titulo, @RequestParam(required = false) String cuerpo, ModelMap model) {
        //System.out.println("En /terminado prueba de parametros:" + titulo +" y cuerpo: "+ cuerpo );
        try {

            noticiaServicio.crearNoticia(titulo, cuerpo);
            model.put("exito", "La noticia fue agregada con exito.");

        } catch (ValidacionException e) {

            model.put("error", e.getMessage());
            Logger.getLogger(NoticiaControlador.class.getName()).log(Logger.Level.WARN, e);

            return "noticia.html";
        }
        return "noticia.html";
    }

    @GetMapping("/noticia/lista")
    public String listar(ModelMap modelo) {

        List<Noticia> noticias = noticiaServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);

        return "listar.html";
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
    @GetMapping("/")
    public String index() {

        return "inicio.html";
    }

    @GetMapping("/register")
    public String register() {

        return "register.html";
    }

    @GetMapping("/login")
    public String login() {

        return "login.html";
    }

    /**
     *
     * MAIL SENDER
     *
     */
    
    // String from = "sender@gmail.com";//dirección de correo que hace el envío.
    // String to = "recipient@gmail.com";//dirección de correo que recibe el mail.
    
    @PostMapping ("/enviarEmail")
    public void sendEmail(String from, String to) {

        noticiaServicio.sendEmail(from, to);
    }

}
