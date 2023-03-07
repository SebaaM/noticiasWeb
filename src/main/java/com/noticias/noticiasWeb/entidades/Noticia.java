/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.noticias.noticiasWeb.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author SebaaM <sebaamartinez54@gmail.com>
 */

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor (access = AccessLevel.PUBLIC)
@AllArgsConstructor 
@ToString
public class Noticia implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String titulo;
    private String cuerpo;
    
    @OneToOne
    private Periodista creador;
    
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    // METODOS
    


    
    
    
}
