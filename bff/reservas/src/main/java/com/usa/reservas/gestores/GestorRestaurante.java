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
import com.usa.reservas.model.Restaurante;
import com.usa.reservas.DAO.RestauranteDAO;

/**
 * Gestor para las operaciones relacionadas con restaurantes
 * Implementa el patrón Singleton
 */
public class GestorRestaurante implements IGestor {
    
    // Instancia única del gestor
    private static GestorRestaurante instance;
    
    // Objeto com.usa.reservas.DAO para acceso a datos
    private RestauranteDAO restauranteDAO;
    
    /**
     * Constructor privado
     */
    private GestorRestaurante() {
        this.restauranteDAO = new RestauranteDAO();
    }
    
    /**
     * Obtiene la instancia única del gestor
     * @return Instancia de GestorRestaurante
     */
    public static synchronized GestorRestaurante getInstance() {
        if (instance == null) {
            instance = new GestorRestaurante();
        }
        return instance;
    }
    
    /**
     * Obtiene un restaurante por su ID
     * @param id ID del restaurante
     * @return Restaurante encontrado o null
     */
    @Override
    public Restaurante obtener(int id) {
        return restauranteDAO.findById(id);
    }
    
    /**
     * Guarda un restaurante (nuevo o actualización)
     * @param obj Restaurante a guardar
     * @return true si se guardó correctamente, false en caso contrario
     */
    @Override
    public boolean guardar(Object obj) {
        if (!(obj instanceof Restaurante)) {
            return false;
        }
        
        Restaurante restaurante = (Restaurante) obj;
        
        try {
            if (restaurante.getId() == 0) {
                // Es un nuevo restaurante
                return restauranteDAO.insert(restaurante);
            } else {
                // Es una actualización
                return restauranteDAO.update(restaurante);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Busca restaurantes por nombre (parcial o completo)
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de restaurantes que coinciden con la búsqueda
     */
    public List<Restaurante> buscarPorNombre(String nombre) {
        try {
            return restauranteDAO.findByName(nombre);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Obtiene todos los restaurantes del sistema
     * @return Lista de todos los restaurantes
     */
    public List<Restaurante> obtenerTodos() {
        try {
            return restauranteDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}