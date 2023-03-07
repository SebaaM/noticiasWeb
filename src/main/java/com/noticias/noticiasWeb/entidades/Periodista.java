/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.noticias.noticiasWeb.entidades;

import com.noticias.noticiasWeb.Enumeraciones.Rol;
import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;

/**
 *
 * @author SebaaM <sebaamartinez54@gmail.com>
 */

@Entity
public class Periodista extends Usuario implements Serializable {


    private ArrayList <Noticia> misNoticias;
    private Double sueldoMensual;

    public Periodista (){
        super();
        misNoticias= new ArrayList();
        this.sueldoMensual=0d;
        super.setRol(Rol.PERIODISTA);
        super.setActivo(Boolean.TRUE);
        
    }
    
    public Periodista (Double sueldo){
        super();
        misNoticias= new ArrayList();
        this.sueldoMensual=sueldo;
        super.setRol(Rol.PERIODISTA);
        super.setActivo(Boolean.TRUE);
        
    }
    
}
