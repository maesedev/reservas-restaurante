import { useMemo } from "react";

import { Reserva } from "@/schema/types";

export interface FilterOptions {
    cedula?: string;
    id?: string;
    idRestaurante?: number;
    // Rango de fechas en formato "YYYY-MM-DD"
    fechaDesde?: string;
    fechaHasta?: string;
    // Rango de horas en formato "HH:mm"
    horaDesde?: string;
    horaHasta?: string;
}

export default function useSearchReserva(
    reservas: Reserva[],
    options: FilterOptions
): Reserva[] {
    const {
        cedula,
        id,
        idRestaurante,
        fechaDesde,
        fechaHasta,
        horaDesde,
        horaHasta,
    } = options;

    return useMemo(() => {
        return reservas.filter((reserva) => {

            console.log("reserva", reserva);
            
            // Filtrar por cedula (búsqueda parcial)
            if (cedula && !reserva.idUsuario?.toString().includes(cedula)) {
                return false;
            }

            // Filtrar por id (búsqueda parcial)
            if (id && !reserva.id.toString().includes(id)) {
                return false;
            }

            // Filtrar por restaurante
            if (
                typeof idRestaurante === "number" &&
                reserva.idRestaurante !== idRestaurante
            ) {
                return false;
            }

            // Extraer fecha y hora de reserva (asumiendo ISO string "YYYY-MM-DDTHH:mm:ssZ")
            const reservaDateObj = new Date(reserva.fechaHora);
            if (isNaN(reservaDateObj.getTime())) {
                return false;
            }
            // Obtener parte de fecha en "YYYY-MM-DD"
            const reservaFecha = reserva.fechaHora.slice(0, 10);
            // Obtener parte de hora en "HH:mm" (posiciones 11 a 16)
            const reservaHora = reserva.fechaHora.slice(11, 16);

            // Filtrar por rango de fechas
            if (fechaDesde && reservaFecha < fechaDesde) {
                return false;
            }
            if (fechaHasta && reservaFecha > fechaHasta) {
                return false;
            }

            // Filtrar por rango de horas
            if (horaDesde && reservaHora < horaDesde) {
                return false;
            }
            if (horaHasta && reservaHora > horaHasta) {
                return false;
            }

            return true;
        });
    }, [
        reservas,
        cedula,
        id,
        idRestaurante,
        fechaDesde,
        fechaHasta,
        horaDesde,
        horaHasta,
    ]);
}