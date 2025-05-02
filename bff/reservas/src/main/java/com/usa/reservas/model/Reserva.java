/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.usa.reservas.model;

/**
 *
 * @author ASUS
 */
/**
 * Entidad Reserva
 */
public class Reserva {
    private int id;
    private int idUsuario;
    private int idRestaurante;
    private int idMesa;
    private java.util.Date fechaHora;
    private int cantidadPersonas;
    private String estado;
    private String comentarios;
    private java.util.Date fechaCreacion;
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    
    public int getIdRestaurante() { return idRestaurante; }
    public void setIdRestaurante(int idRestaurante) { this.idRestaurante = idRestaurante; }
    
    public int getIdMesa() { return idMesa; }
    public void setIdMesa(int idMesa) { this.idMesa = idMesa; }
    
    public java.util.Date getFechaHora() { return fechaHora; }
    public void setFechaHora(java.util.Date fechaHora) { this.fechaHora = fechaHora; }
    
    
    public int getCantidadPersonas() { return cantidadPersonas; }
    public void setCantidadPersonas(int cantidadPersonas) { this.cantidadPersonas = cantidadPersonas; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
    
    public java.util.Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(java.util.Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

}
