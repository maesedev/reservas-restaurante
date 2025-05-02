/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.usa.reservas.model;

/**
 *
 * @author ASUS
 */
public class Restaurante {
    private int id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String descripcion;
    private String calificacion;
    private java.util.Date horarioApertura;
    private java.util.Date horarioCierre;
    private int idAdmin;
    private String estado;
    private java.util.Date fechaApertura;
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getCalificacion() { return calificacion; }
    public void setCalificacion(String calificacion) { this.calificacion = calificacion; }
    
    
    public java.util.Date getHorarioApertura() { return horarioApertura; }
    public void setHorarioApertura(java.util.Date horarioApertura) { this.horarioApertura = horarioApertura; }
    
    public java.util.Date getHorarioCierre() { return horarioCierre; }
    public void setHorarioCierre(java.util.Date horarioCierre) { this.horarioCierre = horarioCierre; }
    
    public int getIdAdmin() { return idAdmin; }
    public void setIdAdmin(int idAdmin) { this.idAdmin = idAdmin; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public java.util.Date getFechaApertura() { return fechaApertura; }
    public void setFechaApertura(java.util.Date fechaApertura) { this.fechaApertura = fechaApertura; }
}

