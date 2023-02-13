/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.noticias.noticiasWeb.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author SebaaM <sebaamartinez54@gmail.com>
 */
@Controller
@RequestMapping("/noticia")
public class NoticiaControlador {
    
    @GetMapping("/noticia")
    public String noticia (){
        
        return "noticia.html";
    }
    
}