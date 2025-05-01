"use client"
import { useParams } from "next/navigation";

export default function Page() {
    
    const { reservaId } = useParams()

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
            <h1 className="text-2xl font-bold mb-4">Editar Reserva {reservaId}</h1>
            <p className="text-gray-700 mb-4">Esta es la página para editar una reserva específica.</p>
            <p className="text-gray-700 mb-4">Aquí puedes agregar el formulario para editar la reserva.</p>
        </div>
    );
}