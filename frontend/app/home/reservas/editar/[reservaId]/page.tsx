"use client";
import React, { useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import {
    LabelledInput,
    LabelledSelect,
    LabelledTextArea,
} from "@/app/ui/reservaForm/components/Labelled";
import { Modal } from "@/app/ui/reservaForm/components/Modal";
import {
    Option,
    Restaurante,
    MesasResponse,
} from "@/app/ui/reservaForm/interfaces";
import { FechaComponent } from "@/app/ui/reservaForm/components/FechaComponent";
import { HoraComponent } from "@/app/ui/reservaForm/components/HoraComponent";

export default function EditarReserva() {
    const { reservaId } = useParams();
    const router = useRouter();

    // Form states
    const [restaurantes, setRestaurantes] = useState<Restaurante[]>([]);
    const [selectedRestaurante, setSelectedRestaurante] = useState<number | "">("");
    const [fechaHora, setFechaHora] = useState<string>("");
    const [fechaCreacionReserva, setFechaCreacionReserva] = useState<string>("");
    const [mesas, setMesas] = useState<number[]>([]);
    const [selectedMesa, setSelectedMesa] = useState<number | "">("");
    const [cedulaUsuario, setCedulaUsuario] = useState<string>("");
    const [cantidadPersonas, setCantidadPersonas] = useState<number>(0);
    const [estadoReserva, setEstadoReserva] = useState<string>("confirmada");
    const [comentariosAdicionales, setComentariosAdicionales] = useState<string>("");
    const [mensaje, setMensaje] = useState<string>("");

    // Modal state
    const [modalOpen, setModalOpen] = useState<boolean>(false);
    const [modalMessage, setModalMessage] = useState<string>("");
    // Error modal for load failure
    const [loadError, setLoadError] = useState<boolean>(false);

    // Fetch initial data de restaurantes
    useEffect(() => {
        fetch(`${process.env.NEXT_PUBLIC_API_BASE_URL}/api/v1/consult/restaurantes`)
            .then((res) => res.json())
            .then((data) => setRestaurantes(data))
            .catch((err) =>
                console.error("Error consultando restaurantes:", err)
            );
    }, []);

    // Fetch reserva details based on reservaId and pre-populate fields
    useEffect(() => {
        if (!reservaId) return;
        fetch(
            `${process.env.NEXT_PUBLIC_API_BASE_URL}/api/v1/consult/reserva?idReserva=${reservaId}`
        )
            .then((res) => {
                if (!res.ok) {
                    throw new Error("Error al cargar la reserva");
                }
                return res.json();
            })
            .then((data) => {
                // Supongamos que el API devuelve los campos en el mismo formato que
                // se usan en el formulario. Ajusta esto según tu API.
                setCedulaUsuario(data.cedulaUsuario);
                setSelectedRestaurante(data.idRestaurante);
                setFechaHora(data.fechaHora);
                setFechaCreacionReserva(data.fechaCreacionReserva);
                setSelectedMesa(data.numeroMesa);
                setCantidadPersonas(data.cantidadPersonas);
                setEstadoReserva(data.estado);
                setComentariosAdicionales(data.comentariosAdicionales);
            })
            .catch((err) => {
                console.error(err);
                setLoadError(true);
            });
    }, [reservaId]);

    // Fetch mesas disponibles cuando cambia restaurante o fechaHora
    useEffect(() => {
        setMensaje("");
        if (selectedRestaurante && fechaHora) {
            // Validar que la fechaHora sea al menos 15 minutos mayor a la actual
            const reservaDate = new Date(fechaHora);
            const validDate = new Date(Date.now() + 15 * 60 * 1000);
            if (reservaDate < validDate) {
                setMensaje(
                    "La fecha y hora de la reserva debe ser al menos 15 minutos en el futuro."
                );
                setMesas([]);
                return;
            }
            fetch(
                `${process.env.NEXT_PUBLIC_API_BASE_URL}/api/v1/consult/restaurante/mesas?idRestaurante=${selectedRestaurante}&fechaHora=${fechaHora}`
            )
                .then((res) => res.json())
                .then((data: MesasResponse) =>
                    setMesas(data.mesasDisponibles || [])
                )
                .catch((err) => {
                    console.error("Error consultando mesas:", err);
                    setMesas([]);
                });
        } else {
            setMesas([]);
        }
    }, [selectedRestaurante, fechaHora]);

    // Options para selects
    const restaurantesOptions: Option[] = [
        { value: "", label: "Seleccione un restaurante" },
        ...restaurantes.map((rest) => ({
            value: rest.idRestaurante,
            label: `${rest.nombre} (${rest.estado})`,
        })),
    ];

    const estadoOptions: Option[] = [
        { value: "confirmada", label: "Confirmada" },
        { value: "por confirmar", label: "Por confirmar" },
    ];

    const mesasOptions: Option[] = [
        { value: "", label: "Seleccione una mesa" },
        ...mesas.map((mesa) => ({
            value: mesa,
            label: `Mesa ${mesa}`,
        })),
    ];

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        // Validar que se hayan seleccionado los campos obligatorios
        if (!selectedRestaurante || !selectedMesa || !fechaHora) {
            setMensaje("Por favor complete todos los campos requeridos.");
            return;
        }
        const reservaDate = new Date(fechaHora);
        const validDate = new Date(Date.now() + 15 * 60 * 1000);
        if (reservaDate < validDate) {
            setMensaje(
                "La fecha y hora de la reserva debe ser al menos 15 minutos en el futuro."
            );
            return;
        }
        const fechaModificacionReserva = new Date().toISOString();
        const reservaData = {
            id: reservaId,
            cedulaUsuario,
            numeroMesa: selectedMesa,
            idRestaurante: selectedRestaurante,
            cantidadPersonas,
            estado: estadoReserva,
            comentariosAdicionales,
            fechaModificacionReserva,
            fechaHora,
            fechaCreacionReserva
        };
        try {
            const res = await fetch(
                `${process.env.NEXT_PUBLIC_API_BASE_URL}/api/v1/update/reserva`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(reservaData),
                }
            );
            const responseData = await res.json();
            if (responseData.id) {
                setModalMessage(`Reserva modificada correctamente (${responseData.id})`);
                setModalOpen(true);
            } else {
                setMensaje("Error al modificar la reserva.");
            }
        } catch (error) {
            console.error("Error al modificar la reserva:", error);
            setMensaje("Error en la comunicación con el servidor.");
        }
    };

    // Redirigir después de cerrar modal de éxito
    useEffect(() => {
        if (modalOpen === false && modalMessage) {
            router.push("/home/reservas");
        }
    }, [modalOpen, modalMessage, router]);

    // Validación simple para ver si el formulario está completo
    const isFormValid =
        cedulaUsuario &&
        selectedRestaurante &&
        fechaHora &&
        selectedMesa &&
        cantidadPersonas > 0;

    if (loadError) {
        return (
            <Modal
                message="Error al cargar la reserva. Por favor intentelo más tarde."
                onClose={() => router.push("/home/reservas")}
            />
        );
    }

    return (
        <div className="max-w-xl mx-auto p-4">
            <h1 className="text-2xl font-semibold mb-4">
                Editar reserva {reservaId}
            </h1>
            {mensaje && <p className="mb-4 text-red-600">{mensaje}</p>}
            <form onSubmit={handleSubmit} className="space-y-4">
                <LabelledInput
                    label="Cédula Usuario:"
                    type="text"
                    value={cedulaUsuario}
                    onChange={(e) => setCedulaUsuario(e.target.value)}
                    required
                />
                <LabelledSelect
                    label="Restaurante:"
                    value={selectedRestaurante}
                    onChange={(e) =>
                        setSelectedRestaurante(Number(e.target.value) || "")
                    }
                    options={restaurantesOptions}
                    required
                />
                <FechaComponent
                    value={fechaHora}
                    onChange={setFechaHora}
                    required
                    disabled={!selectedRestaurante}
                />
                <HoraComponent  
                    value={fechaHora}
                    onChange={setFechaHora}
                    required
                    disabled={!selectedRestaurante}
                />
                <div>
                    <label className="block text-gray-700 mb-1">
                        Mesas Disponibles:
                    </label>
                    {mesas.length > 0 ? (
                        <LabelledSelect
                            label="Seleccione una Mesa:"
                            value={selectedMesa}
                            onChange={(e) => setSelectedMesa(Number(e.target.value) || "")}
                            options={mesasOptions}
                            required
                            disabled={!fechaHora || !selectedRestaurante}
                        />
                    ) : (
                        <p className="text-gray-500">
                            No hay mesas disponibles para la fecha y hora seleccionadas.
                        </p>
                    )}
                </div>
                <LabelledInput
                    label="Cantidad de Personas:"
                    type="number"
                    min="1"
                    value={cantidadPersonas > 0 ? cantidadPersonas : ""}
                    onChange={(e) => setCantidadPersonas(Number(e.target.value))}
                    required
                    placeholder="Cantidad de personas"
                    disabled={!fechaHora || !selectedRestaurante}
                />
                <LabelledSelect
                    label="Estado de la Reserva:"
                    value={estadoReserva}
                    onChange={(e) => setEstadoReserva(e.target.value)}
                    options={estadoOptions}
                    required
                    disabled={!fechaHora || !selectedRestaurante}
                />
                <LabelledTextArea
                    label="Comentarios Adicionales:"
                    value={comentariosAdicionales}
                    onChange={(e) => setComentariosAdicionales(e.target.value)}
                />
                <div>
                    <button
                        type="submit"
                        className={`w-full bg-blue-600 text-white px-3 py-2 rounded transition ${
                            isFormValid
                                ? "hover:bg-blue-700"
                                : "opacity-50 cursor-not-allowed"
                        }`}
                        disabled={!isFormValid}
                    >
                        Modificar Reserva
                    </button>
                </div>
            </form>
            {modalOpen && (
                <Modal
                    message={modalMessage}
                    onClose={() => setModalOpen(false)}
                />
            )}
        </div>
    );
}