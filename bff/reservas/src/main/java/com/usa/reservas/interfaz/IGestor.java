

package com.usa.reservas.interfaz;

public interface IGestor {
    
    /**
     * Obtiene un objeto del sistema por su identificador
     * @param id Identificador del objeto a obtener
     * @return Objeto solicitado o null si no existe
     */
    public Object obtener(int id);
    
    /**
     * Guarda un objeto en el sistema (create o update)
     * @param obj Objeto a guardar
     * @return true si se guardó correctamente, false en caso contrario
     */
    public boolean guardar(Object obj);
}
