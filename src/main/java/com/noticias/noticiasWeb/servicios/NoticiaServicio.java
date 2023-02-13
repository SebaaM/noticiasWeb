/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.noticias.noticiasWeb.servicios;

import com.noticias.noticiasWeb.entidades.Noticia;
import com.noticias.noticiasWeb.exepciones.ValidacionException;
import com.noticias.noticiasWeb.repositorios.NoticiaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author SebaaM <sebaamartinez54@gmail.com>
 */
@Service
public class NoticiaServicio {

    private NoticiaRepositorio noticiaRepositorio;

    @Transactional
    public void crearNoticia(Long id, String titulo, String cuerpo) throws ValidacionException {
        
        validar(id, titulo, cuerpo);
        
        Noticia noticia = new Noticia();

        noticia.setId(id);
        noticia.setTitulo(titulo);
        noticia.setCuerpo(cuerpo);
        noticia.setFecha(new Date());

        noticiaRepositorio.save(noticia);
    }

    public List<Noticia> listarNoticias() {
        List<Noticia> noticias = new ArrayList();

        noticias = noticiaRepositorio.findAll();

        return noticias;
    }

    public void modificarNoticia(Long id, String titulo, String Cuerpo) throws ValidacionException {
        
        validar(id, titulo, Cuerpo);
        
        Optional<Noticia> respuesta = noticiaRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Noticia noti = respuesta.get();

            noti.setTitulo(titulo);
            noti.setCuerpo(Cuerpo);

            noticiaRepositorio.save(noti);
        }

    }

    private void validar(Long id, String titulo, String cuerpo) throws ValidacionException {
        if ( id == null) {
            throw new ValidacionException ("El id de la noticia no puede ser nulo.");
        } 
        if (titulo == null || titulo.isEmpty()){
            throw new ValidacionException ("El titulo de la noticia no puede estar vacio ni ser nulo");
        }
        if (cuerpo == null || cuerpo.isEmpty()){
            throw new ValidacionException ("El cuerpo de la noticia no puede estar vacio ni ser nulo");
        }
    }

}
