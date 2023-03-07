/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.noticias.noticiasWeb.entidades;

import com.noticias.noticiasWeb.Enumeraciones.Rol;
import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author SebaaM <sebaamartinez54@gmail.com>
 */

@Getter @Setter @ToString
@Entity
public class Administrador extends Usuario implements Serializable {


    public Administrador (){
        super();
        super.setRol(Rol.PERIODISTA);
        super.setActivo(Boolean.TRUE);
        
    }
    

    
    
}
