/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.usa.reservas.gestores;

/**
 *
 * @author ASUS
 */
import java.util.Date;
import java.util.List;
import com.usa.reservas.interfaz.IGestor;
import com.usa.reservas.model.Mesa;
import com.usa.reservas.DAO.MesaDAO;

/**
 * Gestor para las operaciones relacionadas con mesas
 * Implementa el patrón Singleton
 */
public class GestorMesa implements IGestor {
    
    // Instancia única del gestor
    private static GestorMesa instance;
    
    // Objeto com.usa.reservas.DAO para acceso a datos
    private MesaDAO mesaDAO;
    
    /**
     * Constructor privado
     */
    private GestorMesa() {
        this.mesaDAO = new MesaDAO();
    }
    
    /**
     * Obtiene la instancia única del gestor
     * @return Instancia de GestorMesa
     */
    public static synchronized GestorMesa getInstance() {
        if (instance == null) {
            instance = new GestorMesa();
        }
        return instance;
    }
    
    /**
     * Obtiene una mesa por su ID
     * @param id ID de la mesa
     * @return Mesa encontrada o null
     */
    @Override
    public Mesa obtener(int id) {
        return mesaDAO.findById(id);
    }
    
    /**
     * Guarda una mesa (nueva o actualización)
     * @param obj Mesa a guardar
     * @return true si se guardó correctamente, false en caso contrario
     */
    @Override
    public boolean guardar(Object obj) {
        if (!(obj instanceof Mesa)) {
            return false;
        }
        
        Mesa mesa = (Mesa) obj;
        
        try {
            if (mesa.getId() == 0) {
                // Es una nueva mesa
                return mesaDAO.insert(mesa);
            } else {
                // Es una actualización
                return mesaDAO.update(mesa);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Obtiene todas las mesas de un restaurante
     * @param restauranteId ID del restaurante
     * @return Lista de mesas del restaurante
     */
    public List<Mesa> obtenerPorRestaurante(int restauranteId) {
        try {
            return mesaDAO.findByRestaurante(restauranteId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Verifica si una mesa está disponible en una fecha y hora específica
     * @param idMesa ID de la mesa a verificar
     * @param fecha Fecha de la reserva
     * @param hora Hora de la reserva
     * @return true si la mesa está disponible, false en caso contrario
     */
    public boolean verificarDisponibilidad(int idMesa, Date fechaHora) {
        try {
            // Verificar si hay reservas para esa mesa en esa fecha y hora
            // Esta implementación dependerá de cómo manejas las reservas en tu sistema
            return !mesaDAO.isReserved(idMesa, fechaHora);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}