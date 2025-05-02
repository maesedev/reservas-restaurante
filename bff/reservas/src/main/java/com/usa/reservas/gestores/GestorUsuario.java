/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.usa.reservas.gestores;

import com.usa.reservas.interfaz.IGestor;
import com.usa.reservas.model.Usuario;
import com.usa.reservas.DAO.UsuarioDAO;

/**
 * Gestor para las operaciones relacionadas con usuarios
 * Implementa el patrón Singleton
 */
public class GestorUsuario implements IGestor {
    
    // Instancia única del gestor
    private static GestorUsuario instance;
    
    // Objeto com.usa.reservas.DAO para acceso a datos
    private UsuarioDAO usuarioDAO;
    
    /**
     * Constructor privado
     */
    private GestorUsuario() {
        this.usuarioDAO = new UsuarioDAO();
    }
    
    /**
     * Obtiene la instancia única del gestor
     * @return Instancia de GestorUsuario
     */
    public static synchronized GestorUsuario getInstance() {
        if (instance == null) {
            instance = new GestorUsuario();
        }
        return instance;
    }
    
    /**
     * Obtiene un usuario por su ID
     * @param id ID del usuario
     * @return Usuario encontrado o null
     */
    @Override
    public Usuario obtener(int id) {
        return usuarioDAO.findById(id);
    }
    
    /**
     * Guarda un usuario (nuevo o actualización)
     * @param obj Usuario a guardar
     * @return true si se guardó correctamente, false en caso contrario
     */
    @Override
    public boolean guardar(Object obj) {
        if (!(obj instanceof Usuario)) {
            return false;
        }
        
        Usuario usuario = (Usuario) obj;
        
        try {
            if (usuario.getId() == 0) {
                // Es un nuevo usuario
                return usuarioDAO.insert(usuario);
            } else {
                // Es una actualización
                return usuarioDAO.update(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Autentica un usuario por email y password
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @return Usuario autenticado o null si la autenticación falla
     */
    public Usuario autenticar(String email, String password) {
        try {
            return usuarioDAO.findByEmailAndPassword(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Registra un nuevo usuario en el sistema
     * @param usuario Usuario a registrar
     * @return true si se registró correctamente, false en caso contrario
     */
    public boolean registrar(Usuario usuario) {
        try {
            // Verificar si ya existe un usuario con ese email
            Usuario existente = usuarioDAO.findByEmail(usuario.getEmail());
            if (existente != null) {
                return false;
            }
            
            // Guardar el nuevo usuario
            return usuarioDAO.insert(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
