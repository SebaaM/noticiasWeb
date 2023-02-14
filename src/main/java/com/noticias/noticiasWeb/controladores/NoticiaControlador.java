/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.noticias.noticiasWeb.controladores;

import com.noticias.noticiasWeb.exepciones.ValidacionException;
import com.noticias.noticiasWeb.servicios.NoticiaServicio;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    private NoticiaServicio noticiaServicio;
    
    @GetMapping("/noticia")
    public String noticia (){
        
        return "noticia.html";
    }
    
    @GetMapping("/")
    public String index (){
        
        return "inicio.html";
    }
    
    @PostMapping("/terminado")
    public String terminado (@RequestParam String titulo,@RequestParam(required = false) String cuerpo, ModelMap model ) {
        System.out.println("En /terminado prueba de parametros:" + titulo +" y cuerpo: "+ cuerpo );
        try {
            noticiaServicio.crearNoticia(titulo, cuerpo); 
            model.put("exito", "La noticia fue agregada con exito.");
        } catch (ValidacionException e) {
            model.put ("error",e.getMessage());
            Logger.getLogger(NoticiaControlador.class.getName()).log(Logger.Level.WARN, e);
            return "noticia.html";
        }
        return "noticia.html";
    }
    
    @GetMapping("/lista")
    public String listar () {
        // falta llamar al metodo listar del servicio
        return "listar.html";
    }
    
    
}
