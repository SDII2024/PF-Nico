package com.example.PFNico.TP3.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private String fecha_nacimiento;
    private String mail;
    private int dni;
    private String sexo;
    private int telefono;

    public Usuario(){}

    public Usuario(int id, int dni, String nombre, String apellido, String fecha_nacimiento, String mail,int telefono, String sexo){
        this.id=id;
        this.dni=dni;
        this.nombre=nombre;
        this.apellido=apellido;
        this.fecha_nacimiento=fecha_nacimiento;
        this.mail=mail;
        this.telefono=telefono;
        this.sexo=sexo;
    }
}
