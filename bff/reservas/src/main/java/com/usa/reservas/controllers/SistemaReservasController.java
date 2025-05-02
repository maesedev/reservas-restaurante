/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.usa.reservas.controllers;

import com.usa.reservas.factory.GestorFactory;
import com.usa.reservas.gestores.GestorReserva;
import com.usa.reservas.gestores.GestorUsuario;
import com.usa.reservas.model.Reserva;
import com.usa.reservas.model.Usuario;
/**
 * Controlador principal que actúa como fachada para el sistema de reservas
 * Implementa el patrón Singleton
 */
public class SistemaReservasController {
    
    // Instancia única del controlador
    private static SistemaReservasController instance;
    
    // Factory para crear gestores
    private GestorFactory factory;
    
    // Sesión actual del usuario
    private Session session;
    
    /**
     * Constructor privado
     */
    private SistemaReservasController() {
        this.factory = GestorFactory.getInstance();
        this.session = new Session();
    }
    
    /**
     * Obtiene la instancia única del controlador
     * @return Instancia de SistemaReservasController
     */
    public static synchronized SistemaReservasController getInstance() {
        if (instance == null) {
            instance = new SistemaReservasController();
        }
        return instance;
    }
    
    /**
     * Inicia sesión de un usuario en el sistema
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @return true si el inicio de sesión fue exitoso, false en caso contrario
     */
    public boolean iniciarSesion(String email, String password) {
        GestorUsuario gestorUsuario = (GestorUsuario) factory.crearGestor("usuario");
        
        Usuario usuario = gestorUsuario.autenticar(email, password);
        if (usuario != null) {
            session.setUsuario(usuario);
            return true;
        }
        
        return false;
    }
    
    /**
     * Registra un nuevo usuario en el sistema
     * @param usuario Usuario a registrar
     * @return true si el registro fue exitoso, false en caso contrario
     */
    public boolean registrarUsuario(Usuario usuario) {
        GestorUsuario gestorUsuario = (GestorUsuario) factory.crearGestor("usuario");
        return gestorUsuario.registrar(usuario);
    }
    
    /**
     * Realiza una reserva en el sistema
     * @param reserva Reserva a realizar
     * @return true si la reserva fue exitosa, false en caso contrario
     */
    public boolean realizarReserva(Reserva reserva) {
        // Verificar que haya un usuario autenticado
        if (session.getUsuario() == null) {
            return false;
        }
        
        // Asignar el usuario actual a la reserva
        reserva.setIdUsuario(session.getUsuario().getId());
        
        GestorReserva gestorReserva = (GestorReserva) factory.crearGestor("reserva");
        return gestorReserva.guardar(reserva);
    }
    
    /**
     * Clase interna para manejar la sesión del usuario
     */
    private class Session {
        private Usuario usuario;
        
        public Usuario getUsuario() {
            return usuario;
        }
        
        public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
        }
    }
}
