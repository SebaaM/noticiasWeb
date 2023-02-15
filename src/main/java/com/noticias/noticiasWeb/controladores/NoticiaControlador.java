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
    public String noticia (){
        
        return "noticia.html";
    }
    
    @GetMapping("/")
    public String index (){
        
        return "inicio.html";
    }
    
    @PostMapping("/terminado")
    public String terminado (@RequestParam String titulo,@RequestParam(required = false) String cuerpo, ModelMap model ) {
        //System.out.println("En /terminado prueba de parametros:" + titulo +" y cuerpo: "+ cuerpo );
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
    public String listar (ModelMap modelo) {
        
        List<Noticia> noticias = noticiaServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);
        
        return "listar.html";
    }
    
    @GetMapping("/noticia/modificar/{id}")
    public String modificar(@PathVariable Long id, ModelMap modelo) {
        
        modelo.put ("noticia", noticiaServicio.getOne(id));
        
        return "noticia_modificar.html";
        
    }
    
    @PostMapping("/noticia/modificar/{id}")
    public String modificar (@PathVariable Long id, String titulo, String cuerpo, ModelMap modelo){
        
        try {
            
            noticiaServicio.modificarNoticia(id, titulo, cuerpo);
            
            ////////////////////////////
            // ARREGLAR ESTE RETURN PARA VOLVER A LA LISTA.
            return "/lista";
            /////////////////////
        } catch (ValidacionException e) {
            
            modelo.put("error", e.getMessage());
            
            return "noticia_modificar.html";
        }
    }
    
    @DeleteMapping("{id}")
    public String eliminar (@PathVariable Long id, ModelMap modelo) throws ValidacionException {
        noticiaServicio.eliminar(id);
        
        return "noticia_modificar.html";
    }
    
    
    
    
}
