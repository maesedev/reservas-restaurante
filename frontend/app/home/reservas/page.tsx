"use client";

import React, { useEffect, useState } from "react";
import Link from "next/link";
import useGetSession, { TSessionPayload } from "@/hooks/useGetSession";

type Reserva = {
    id: string;
    cedulaUsuario: string;
    numeroMesa: number;
    idRestaurante: number;
    cantidadPersonas: number;
    estado: string;
    comentariosAdicionales: string;
    fechaCreacionReserva: string;
    fechaModificacionReserva: string;
    fechaHora: string;
};

const ReservasPage = () => {
    const { getSessionPayload } = useGetSession();
    const session = getSessionPayload() as TSessionPayload | null;
    const [reservas, setReservas] = useState<Reserva[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string>("");

    useEffect(() => {
        if (!session) {
            setError("No se encontró sesión iniciada.");
            setLoading(false);
            return;
        }

        fetch(
            `${process.env.NEXT_PUBLIC_API_BASE_URL}/api/v1/consult/usuario/reservas?cedulaUsuario=${session.sub}`
        )
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Error al obtener reservas");
                }
                return response.json();
            })
            .then((data) => {
                setReservas(data);
                setLoading(false);
            })
            .catch((err) => {
                setError(err.message || "Error inesperado");
                setLoading(false);
            });
    }, []);

    if (loading) {
        return <div>Cargando reservas...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <div className="p-6 bg-gray-50 min-h-screen">
            <h1 className="text-3xl font-bold mb-6 text-center">Mis Reservas</h1>

            {/* Botón para crear reserva */}
            <div className="flex flex-col items-center mb-8 transition-shadow ">
                <Link href="/home/reservas/crear" >
                    <button className="text-6xl hover:cursor-pointer hover:opacity-65 text-green-500 font-bold">+</button>
                </Link> 
                <span className="mt-2 text-lg">Crear reserva</span>
            </div>

            {reservas.length === 0 ? (
                <p className="text-center">No tienes reservas.</p>
            ) : (
                <div className="grid gap-6 sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-3">
                    {reservas.map((reserva, index) => {
                        const [fecha, hora] = reserva.fechaHora.split("T");

                        const [year, month, day] = fecha.split("-").map(Number);
                        const [hour, minute] = hora.split(":").map(Number);
                        
                        // month - 1 porque en JS los meses van 0–11
                        const combinedDateTime = new Date(Date.UTC(
                          year,
                          month - 1,
                          day,
                          hour,
                          minute
                        ));

                        const fechaHorario = new Date(reserva.fechaHora);
                        const fechaFormateada = combinedDateTime.toLocaleDateString("es-ES", {
                            weekday: "long",
                            day: "numeric",
                            month: "long",
                        });

                        const horaFormateada = fechaHorario.toLocaleTimeString("es-ES", {
                            hour: "2-digit",
                            minute: "2-digit",
                        });

                        return (
                            <div
                                key={index}
                                className="bg-white border rounded-lg shadow-lg p-5 hover:shadow-2xl transition-shadow"
                            >
                                <div className="flex justify-between items-start mb-4">
                                    <Link href={`/home/reservas/editar/${reserva.id}`}>
                                        <button className="flex items-center bg-blue-500 hover:bg-blue-600 text-white px-3 py-1 rounded">
                                            <svg
                                                xmlns="http://www.w3.org/2000/svg"
                                                className="h-5 w-5 mr-1"
                                                fill="none"
                                                viewBox="0 0 24 24"
                                                stroke="currentColor"
                                            >
                                                <path
                                                    strokeLinecap="round"
                                                    strokeLinejoin="round"
                                                    strokeWidth={2}
                                                    d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5M18.5 2.5a2.121 2.121 0 113 3L12 15l-4 1 1-4 9.5-9.5z"
                                                />
                                            </svg>
                                            Editar
                                        </button>
                                    </Link>
                                </div>
                                <div>
                                    <p className="text-gray-700 mb-1">
                                        <strong>Fecha: </strong>
                                        {fechaFormateada}
                                    </p>
                                    <p className="text-gray-700 mb-1">
                                        <strong>Hora: </strong>
                                        {horaFormateada}
                                    </p>
                                    <p className="text-gray-700 mb-1">
                                        <strong>Mesa: </strong> {reserva.numeroMesa}
                                    </p>
                                    <p className="text-gray-700 mb-1">
                                        <strong>Personas: </strong> {reserva.cantidadPersonas}
                                    </p>
                                    <p className="text-gray-700 mb-1">
                                        <strong>Estado: </strong> {reserva.estado}
                                    </p>
                                    {reserva.comentariosAdicionales && (
                                        <p className="text-gray-700 mb-1">
                                            <strong>Comentarios: </strong>
                                            {reserva.comentariosAdicionales}
                                        </p>
                                    )}
                                </div>
                            </div>
                        );
                    })}
                </div>
            )}
        </div>
    );
};

export default ReservasPage;