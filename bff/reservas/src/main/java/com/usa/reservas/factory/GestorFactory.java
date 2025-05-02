
package com.usa.reservas.factory;

import com.usa.reservas.interfaz.IGestor;

import com.usa.reservas.gestores.GestorHorario;
import com.usa.reservas.gestores.GestorMesa;
import com.usa.reservas.gestores.GestorReserva;
import com.usa.reservas.gestores.GestorRestaurante;
import com.usa.reservas.gestores.GestorUsuario;

/**
 * Implementación del patrón Factory Method para crear com.usa.reservas.gestores
 * Utiliza el patrón Singleton para asegurar una única instancia
 */
public class GestorFactory {
    
    // Instancia única del factory (Singleton)
    private static GestorFactory instance;
    
    /**
     * Constructor privado para evitar instanciación directa
     */
    private GestorFactory() {
        // Constructor privado
    }
    
    /**
     * Obtiene la instancia única del factory
     * @return Instancia de GestorFactory
     */
    public static synchronized GestorFactory getInstance() {
        if (instance == null) {
            instance = new GestorFactory();
        }
        return instance;
    }
    
    /**
     * Crea un gestor del tipo especificado
     * @param tipo Tipo de gestor a crear ("usuario", "restaurante", "mesa", "reserva", "horario")
     * @return Instancia del gestor solicitado o null si el tipo no es válido
     */
    public IGestor crearGestor(String tipo) {
        if (tipo == null) {
            return null;
        }
        
        switch (tipo.toLowerCase()) {
            case "usuario":
                return GestorUsuario.getInstance();
            case "restaurante":
                return GestorRestaurante.getInstance();
            case "mesa":
                return GestorMesa.getInstance();
            case "reserva":
                return GestorReserva.getInstance();
            case "horario":
                return GestorHorario.getInstance();
            default:
                return null;
        }
    }
}