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
import com.usa.reservas.model.Mesa;
import com.usa.reservas.model.Reserva;
import com.usa.reservas.model.Restaurante;
import com.usa.reservas.model.Usuario;
import com.usa.reservas.DAO.ReservaDAO;

/**
 * Gestor para las operaciones relacionadas con reservas
 * Implementa el patrón Singleton
 */
public class GestorReserva implements IGestor {
    
    // Instancia única del gestor
    private static GestorReserva instance;
    
    // Objeto com.usa.reservas.DAO para acceso a datos
    private ReservaDAO reservaDAO;
    
    // com.usa.reservas.gestores relacionados
    private GestorMesa gestorMesa;
    private GestorUsuario gestorUsuario;
    private GestorRestaurante gestorRestaurante;
    
    /**
     * Constructor privado
     */
    private GestorReserva() {
        this.reservaDAO = new ReservaDAO();
        this.gestorMesa = GestorMesa.getInstance();
        this.gestorUsuario = GestorUsuario.getInstance();
        this.gestorRestaurante = GestorRestaurante.getInstance();
    }
    
    /**
     * Obtiene la instancia única del gestor
     * @return Instancia de GestorReserva
     */
    public static synchronized GestorReserva getInstance() {
        if (instance == null) {
            instance = new GestorReserva();
        }
        return instance;
    }
    
    /**
     * Obtiene una reserva por su ID
     * @param id ID de la reserva
     * @return Reserva encontrada o null
     */
    @Override
    public Reserva obtener(int id) {
        return reservaDAO.findById(id);
    }
    
    /**
     * Guarda una reserva (nueva o actualización)
     * @param obj Reserva a guardar
     * @return true si se guardó correctamente, false en caso contrario
     */
    @Override
    public boolean guardar(Object obj) {
        if (!(obj instanceof Reserva)) {
            return false;
        }
        
        Reserva reserva = (Reserva) obj;
        
        try {
            // Verificar que existan el usuario, restaurante y mesa
            Usuario usuario = (Usuario) gestorUsuario.obtener(reserva.getIdUsuario());
            if (usuario == null) {
                return false;
            }
            
            Restaurante restaurante = (Restaurante) gestorRestaurante.obtener(reserva.getIdRestaurante());
            if (restaurante == null) {
                return false;
            }
            
            Mesa mesa = gestorMesa.obtener(reserva.getIdMesa());
            if (mesa == null) {
                return false;
            }
            
            // Verificar que la mesa pertenezca al restaurante
            if (mesa.getIdRestaurante() != reserva.getIdRestaurante()) {
                return false;
            }
            
            // Verificar disponibilidad de la mesa
            if (!gestorMesa.verificarDisponibilidad(reserva.getIdMesa(), reserva.getFechaHora())) {
                return false;
            }
            
            if (reserva.getId() == 0) {
                // Es una nueva reserva
                return reservaDAO.insert(reserva);
            } else {
                // Es una actualización
                return reservaDAO.update(reserva);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Obtiene todas las reservas de un usuario
     * @param idUsuario ID del usuario
     * @return Lista de reservas del usuario
     */
    public List<Reserva> obtenerPorUsuario(int idUsuario) {
        try {
            return reservaDAO.findByUsuario(idUsuario);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
