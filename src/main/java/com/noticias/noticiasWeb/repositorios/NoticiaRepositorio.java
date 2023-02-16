/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.noticias.noticiasWeb.repositorios;

import com.noticias.noticiasWeb.entidades.Noticia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author SebaaM <sebaamartinez54@gmail.com>
 */

@Repository
public interface NoticiaRepositorio extends JpaRepository<Noticia,Long> {
    
    @Query("SELECT n FROM Noticia n WHERE n.titulo = :titulo")
    public Noticia buscarPorTitulo (@Param("titulo") String titulo) ;
    
    @Query("SELECT n FROM Noticia n ORDER BY n.fecha desc")
    public List <Noticia> buscarNoticiaDes () ;
    
    
}
