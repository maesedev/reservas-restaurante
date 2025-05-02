/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.usa.reservas.model;

import java.sql.Time;

public class Horario {
    private int id;
    private int idRestaurante;
    private String diaSemana;
    private Time horaApertura;
    private Time horaCierre;
    private String estado;
    
    /**
     * Constructor por defecto
     */
    public Horario() {
    }
    
    /**
     * Constructor con parámetros
     * @param id ID del horario
     * @param idRestaurante ID del restaurante
     * @param diaSemana Día de la semana
     * @param horaApertura Hora de apertura
     * @param horaCierre Hora de cierre
     * @param estado Estado del horario
     */
    public Horario(int id, int idRestaurante, String diaSemana, Time horaApertura, Time horaCierre, String estado) {
        this.id = id;
        this.idRestaurante = idRestaurante;
        this.diaSemana = diaSemana;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
        this.estado = estado;
    }
    
    /**
     * Constructor sin ID para nuevos registros
     * @param idRestaurante ID del restaurante
     * @param diaSemana Día de la semana
     * @param horaApertura Hora de apertura
     * @param horaCierre Hora de cierre
     * @param estado Estado del horario
     */
    public Horario(int idRestaurante, String diaSemana, Time horaApertura, Time horaCierre, String estado) {
        this.idRestaurante = idRestaurante;
        this.diaSemana = diaSemana;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
        this.estado = estado;
    }
    
    // Getters y setters
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getIdRestaurante() {
        return idRestaurante;
    }
    
    public void setIdRestaurante(int idRestaurante) {
        this.idRestaurante = idRestaurante;
    }
    
    public String getDiaSemana() {
        return diaSemana;
    }
    
    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }
    
    public Time getHoraApertura() {
        return horaApertura;
    }
    
    public void setHoraApertura(Time horaApertura) {
        this.horaApertura = horaApertura;
    }
    
    public Time getHoraCierre() {
        return horaCierre;
    }
    
    public void setHoraCierre(Time horaCierre) {
        this.horaCierre = horaCierre;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return "Horario{" +
                "id=" + id +
                ", idRestaurante=" + idRestaurante +
                ", diaSemana='" + diaSemana + '\'' +
                ", horaApertura=" + horaApertura +
                ", horaCierre=" + horaCierre +
                ", estado='" + estado + '\'' +
                '}';
    }
}