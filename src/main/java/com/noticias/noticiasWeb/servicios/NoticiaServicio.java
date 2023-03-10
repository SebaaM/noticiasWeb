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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author SebaaM <sebaamartinez54@gmail.com>
 */
@Service
public class NoticiaServicio {

    @Autowired
    private NoticiaRepositorio noticiaRepositorio;

    @Transactional
    public void crearNoticia(String titulo, String cuerpo) throws ValidacionException {

        validar(titulo, cuerpo);

        Noticia noticia = new Noticia();

        noticia.setTitulo(titulo);
        noticia.setCuerpo(cuerpo);
        noticia.setFecha(new Date());

        noticiaRepositorio.save(noticia);
    }

    @Transactional(readOnly = true)
    public List<Noticia> listarNoticias() {
        List<Noticia> noticias = new ArrayList();

        noticias = noticiaRepositorio.findAll();

        return noticias;
    }

    @Transactional
    public void modificarNoticia(Long id, String titulo, String Cuerpo) throws ValidacionException {

        validar(titulo, Cuerpo);
        if (id == null) {
            throw new ValidacionException("El id de la noticia no puede ser nulo");
        }

        Optional<Noticia> respuesta = noticiaRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Noticia noti = respuesta.get();

            noti.setTitulo(titulo);
            noti.setCuerpo(Cuerpo);

            noticiaRepositorio.save(noti);
        }

    }

    private void validar(String titulo, String cuerpo) throws ValidacionException {
        if (titulo == null || titulo.isEmpty()) {
            throw new ValidacionException("El titulo de la noticia no puede estar vacio ni ser nulo");
        }
        if (cuerpo == null || cuerpo.isEmpty()) {
            throw new ValidacionException("El cuerpo de la noticia no puede estar vacio ni ser nulo");
        }
    }

    @Transactional(readOnly = true)
    public Noticia getOne(Long id) {
        return noticiaRepositorio.getOne(id);
    }

    @Transactional
    public void eliminar(Long id) throws ValidacionException {
        if (id == null) {
            throw new ValidacionException("Fallo al eliminar el id es nulo. ");
        }

        Noticia noticia = noticiaRepositorio.getReferenceById(id);

        noticiaRepositorio.deleteById(noticia.getId());
    }

    /**
     * 
     * EMAIL SENDER
     *  
     */
    
    // String from = "sender@gmail.com";//direcci??n de correo que hace el env??o.
    // String to = "recipient@gmail.com";//direcci??n de correo que recibe el mail.
    
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String from, String to) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Asunto del correo");
        message.setText("Este es un correo autom??tico!");
        mailSender.send(message); //m??todo Send(envio), propio de Java Mail Sender.
    }
}
