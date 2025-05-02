/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.usa.reservas.gestores;

/**
 *
 * @author ASUS
 */
import java.util.List;
import com.usa.reservas.interfaz.IGestor;
import com.usa.reservas.model.Horario;
import com.usa.reservas.model.Restaurante;
import com.usa.reservas.DAO.HorarioDAO;

/**
 * Gestor para las operaciones relacionadas con horarios
 * Implementa el patrón Singleton
 */
public class GestorHorario implements IGestor {
    
    // Instancia única del gestor
    private static GestorHorario instance;
    
    // Objeto com.usa.reservas.DAO para acceso a datos
    private HorarioDAO horarioDAO;
    
    // Gestor relacionado
    private GestorRestaurante gestorRestaurante;
    
    /**
     * Constructor privado
     */
    private GestorHorario() {
        this.horarioDAO = new HorarioDAO();
        this.gestorRestaurante = GestorRestaurante.getInstance();
    }
    
    /**
     * Obtiene la instancia única del gestor
     * @return Instancia de GestorHorario
     */
    public static synchronized GestorHorario getInstance() {
        if (instance == null) {
            instance = new GestorHorario();
        }
        return instance;
    }
    
    /**
     * Obtiene un horario por su ID
     * @param id ID del horario
     * @return Horario encontrado o null
     */
    @Override
    public Horario obtener(int id) {
        return horarioDAO.findById(id);
    }
    
    /**
     * Guarda un horario (nuevo o actualización)
     * @param obj Horario a guardar
     * @return true si se guardó correctamente, false en caso contrario
     */
    @Override
    public boolean guardar(Object obj) {
        if (!(obj instanceof Horario)) {
            return false;
        }
        
        Horario horario = (Horario) obj;
        
        try {
            // Verificar que exista el restaurante
            Restaurante restaurante = (Restaurante) gestorRestaurante.obtener(horario.getIdRestaurante());
            if (restaurante == null) {
                return false;
            }
            
            if (horario.getId() == 0) {
                // Es un nuevo horario
                return horarioDAO.insert(horario);
            } else {
                // Es una actualización
                return horarioDAO.update(horario);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Obtiene todos los horarios de un restaurante
     * @param idRestaurante ID del restaurante
     * @return Lista de horarios del restaurante
     */
    public List<Horario> obtenerPorRestaurante(int idRestaurante) {
        try {
            return horarioDAO.findByRestaurante(idRestaurante);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
