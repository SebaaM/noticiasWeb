/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.noticias.noticiasWeb.repositorios;

import com.noticias.noticiasWeb.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author SebaaM <sebaamartinez54@gmail.com>
 */
@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String> {
    
}
