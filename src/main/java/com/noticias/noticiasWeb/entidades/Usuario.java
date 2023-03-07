/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.noticias.noticiasWeb.entidades;

import com.noticias.noticiasWeb.Enumeraciones.Rol;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 *
 * @author SebaaM <sebaamartinez54@gmail.com>
 */
@Getter
@Setter
@ToString
@Data
@NoArgsConstructor (access = AccessLevel.PUBLIC)
@AllArgsConstructor 
@Entity
public class Usuario implements Serializable {
    
    @Id
    @GeneratedValue (generator = "uuid")
    @GenericGenerator (name = "uuid", strategy = "uuid2")
    private String id;
    
    private String nombre;
    private String email;
    private String password;
    private  Boolean activo;
    
    @Temporal(TemporalType.DATE)
    private Date fecha_alta;
    
    @Enumerated(EnumType.STRING)
    private Rol rol;
    
    @OneToOne
    private Imagen imagen;

    
}
