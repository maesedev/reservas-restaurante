"use client"
import React, { useEffect, useState } from "react";

interface Restaurante {
    idRestaurante: number;
    nombre: string;
    direccion: string;
    telefono: string;
    tipoComida: string;
    calificacion: number;
    estado: string;
}

interface MesasResponse {
    idRestaurante: number;
    mesasDisponibles: number[];
}

export default function CrearReserva() {
    const [restaurantes, setRestaurantes] = useState<Restaurante[]>([]);
    const [selectedRestaurante, setSelectedRestaurante] = useState<number | "">("");
    const [fechaHora, setFechaHora] = useState<string>("");
    const [mesas, setMesas] = useState<number[]>([]);
    const [selectedMesa, setSelectedMesa] = useState<number | "">("");
    const [cedulaUsuario, setCedulaUsuario] = useState<string>("");
    const [cantidadPersonas, setCantidadPersonas] = useState<number>(0);
    const [estadoReserva, setEstadoReserva] = useState<string>("confirmada");
    const [comentariosAdicionales, setComentariosAdicionales] = useState<string>("");
    const [mensaje, setMensaje] = useState<string>("");

    // Nuevo estado para el modal de éxito.
    const [modalOpen, setModalOpen] = useState<boolean>(false);
    const [modalMessage, setModalMessage] = useState<string>("");

    const fechaCreacionReserva = new Date().toISOString();

    useEffect(() => {
        fetch(`${process.env.NEXT_PUBLIC_API_BASE_URL}/api/v1/consult/restaurantes`)
            .then((res) => res.json())
            .then((data) => setRestaurantes(data))
            .catch((err) => console.error("Error consultando restaurantes:", err));
    }, []);

    useEffect(() => {
        setMensaje("");
        if (selectedRestaurante && fechaHora) {
            if (selectedRestaurante && fechaHora && new Date(fechaHora) < new Date()) {
                setMensaje("La fecha y hora de la reserva no pueden ser anteriores a la hora actual.");
                setMesas([]);
                return;
            }
            fetch(
                `${process.env.NEXT_PUBLIC_API_BASE_URL}/api/v1/consult/restaurante/mesas?idRestaurante=${selectedRestaurante}&fechaHora=${fechaHora}`
            )
                .then((res) => res.json())
                .then((data: MesasResponse) => setMesas(data.mesasDisponibles || []))
                .catch((err) => {
                    console.error("Error consultando mesas:", err);
                    setMesas([]);
                });
        } else {
            setMesas([]);
        }
    }, [selectedRestaurante, fechaHora]);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!selectedRestaurante || !selectedMesa || !fechaHora) {
            setMensaje("Por favor complete todos los campos requeridos.");
            return;
        }

        const reservaData = {
            cedulaUsuario,
            numeroMesa: selectedMesa,
            idRestaurante: selectedRestaurante,
            cantidadPersonas,
            estado: estadoReserva,
            comentariosAdicionales,
            fechaCreacionReserva,
            fechaHora,
        };

        try {
            const res = await fetch(`${process.env.NEXT_PUBLIC_API_BASE_URL}/api/v1/create/reserva`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(reservaData),
            });
            const responseData = await res.json();
            if (responseData.idReserva) {
                // Abre un modal de éxito
                setModalMessage(`Reserva creada correctamente (${responseData.idReserva})`);
                setModalOpen(true);
            } else {
                setMensaje("Error al crear la reserva.");
            }
        } catch (error) {
            console.error("Error al crear la reserva:", error);
            setMensaje("Error en la comunicación con el servidor.");
        }
    };

    // El formulario es válido sólo si se han completado todos los campos requeridos.
    const isFormValid = cedulaUsuario && selectedRestaurante && fechaHora && selectedMesa;

    return (
        <div className="max-w-xl mx-auto p-4">
            <h1 className="text-2xl font-semibold mb-4">Crear Reserva</h1>
            {/* Se muestran errores en rojo */}
            {mensaje && <p className="mb-4 text-red-600">{mensaje}</p>}
            <form onSubmit={handleSubmit} className="space-y-4">
                <div>
                    <label className="block text-gray-700 mb-1">Cédula Usuario:</label>
                    <input
                        type="text"
                        className="w-full px-3 py-2 border rounded focus:outline-none focus:ring"
                        value={cedulaUsuario}
                        onChange={(e) => setCedulaUsuario(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label className="block text-gray-700 mb-1">Restaurante:</label>
                    <select
                        value={selectedRestaurante}
                        onChange={(e) => setSelectedRestaurante(Number(e.target.value) || "")}
                        className="w-full px-3 py-2 border rounded focus:outline-none focus:ring"
                        required
                    >
                        <option value="">Seleccione un restaurante</option>
                        {restaurantes.map((rest) => (
                            <option key={rest.idRestaurante} value={rest.idRestaurante}>
                                {rest.nombre} ({rest.estado})
                            </option>
                        ))}
                    </select>
                </div>
                <div>
                    <label className="block text-gray-700 mb-1">Fecha y Hora de la Reserva:</label>
                    <input
                        type="datetime-local"
                        value={fechaHora ? fechaHora.substring(0, 16) : ""}
                        onChange={(e) => {
                            const date = new Date(e.target.value);
                            setFechaHora(date.toISOString());
                        }}
                        className="w-full px-3 py-2 border rounded focus:outline-none focus:ring"
                        required
                        disabled={!selectedRestaurante}
                    />
                </div>
                <div>
                    <label className="block text-gray-700 mb-1">Mesas Disponibles:</label>
                    {mesas.length > 0 ? (
                        <select
                            value={selectedMesa}
                            onChange={(e) => setSelectedMesa(Number(e.target.value) || "")}
                            className="w-full px-3 py-2 border rounded focus:outline-none focus:ring"
                            required
                            disabled={!fechaHora || !selectedRestaurante}
                        >
                            <option value="">Seleccione una mesa</option>
                            {mesas.map((mesa) => (
                                <option key={mesa} value={mesa}>
                                    Mesa {mesa}
                                </option>
                            ))}
                        </select>
                    ) : (
                        <p className="text-gray-500">No hay mesas disponibles para la fecha y hora seleccionadas.</p>
                    )}
                </div>
                <div>
                    <label className="block text-gray-700 mb-1">Cantidad de Personas:</label>
                    <input
                        type="number"
                        min="1"
                        value={cantidadPersonas > 0 ? cantidadPersonas : ""}
                        onChange={(e) => setCantidadPersonas(Number(e.target.value))}
                        className="w-full px-3 py-2 border rounded focus:outline-none focus:ring"
                        required
                        placeholder="Cantidad de personas"
                        disabled={!fechaHora || !selectedRestaurante}
                    />
                </div>
                <div>
                    <label className="block text-gray-700 mb-1">Estado de la Reserva:</label>
                    <select
                        value={estadoReserva}
                        onChange={(e) => setEstadoReserva(e.target.value)}
                        className="w-full px-3 py-2 border rounded focus:outline-none focus:ring"
                        required
                        disabled={!fechaHora || !selectedRestaurante}
                    >
                        <option value="confirmada">Confirmada</option>
                        <option value="por confirmar">Por confirmar</option>
                    </select>
                </div>
                <div>
                    <label className="block text-gray-700 mb-1">Comentarios Adicionales:</label>
                    <textarea
                        value={comentariosAdicionales}
                        onChange={(e) => setComentariosAdicionales(e.target.value)}
                        className="w-full px-3 py-2 border rounded focus:outline-none focus:ring"
                    />
                </div>
                <div>
                    <button
                        type="submit"
                        className={`w-full bg-blue-600 text-white px-3 py-2 rounded transition ${
                            isFormValid ? "hover:bg-blue-700" : "opacity-50 cursor-not-allowed"
                        }`}
                        disabled={!isFormValid}
                    >
                        Crear Reserva
                    </button>
                </div>
            </form>

            {(modalOpen && 
                <div className="fixed inset-0 flex items-center justify-center bg-[#0008] ">
                    <div className="bg-white p-6 rounded shadow-lg">
                        <h2 className="text-xl font-bold mb-4">{modalMessage}</h2>
                        <button
                            onClick={() => setModalOpen(false)}
                            className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
                        >
                            Cerrar
                        </button>
                    </div>
                </div>
             )}
        </div>
    );
}