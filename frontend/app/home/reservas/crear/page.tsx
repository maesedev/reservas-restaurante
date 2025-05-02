"use client";
import {Modal} from "@/app/ui/reservaForm/components/Modal";
import { useRouter } from "next/navigation";
import React, { useEffect, useState } from "react";
import {
    LabelledInput,
    LabelledSelect,
    LabelledTextArea
} from "@/app/ui/reservaForm/components/Labelled";
import {
    FechaComponentProps,
    HoraComponentProps,
    LabelledInputProps,
    LabelledSelectProps,
    LabelledTextAreaProps,
    MesasResponse,
    ModalProps,
    Option,
    Restaurante,
} from "@/app/ui/reservaForm/interfaces";
import SubmitButton from "@/app/ui/reservaForm/components/SubmitButton";
import { FechaComponent } from "@/app/ui/reservaForm/components/FechaComponent";
import { HoraComponent } from "@/app/ui/reservaForm/components/HoraComponent";

export default function CrearReserva() {
    const [restaurantes, setRestaurantes] = useState<Restaurante[]>([]);
    const [selectedRestaurante, setSelectedRestaurante] = useState<number | "">(
        ""
    );
    const [fechaHora, setFechaHora] = useState<string>("");
    const [mesas, setMesas] = useState<number[]>([]);
    const [selectedMesa, setSelectedMesa] = useState<number | "">("");
    const [cedulaUsuario, setCedulaUsuario] = useState<string>("");
    const [cantidadPersonas, setCantidadPersonas] = useState<number>(0);
    const [estadoReserva, setEstadoReserva] = useState<string>("confirmada");
    const [comentariosAdicionales, setComentariosAdicionales] =
        useState<string>("");
    const [mensaje, setMensaje] = useState<string>("");

    // Modal state
    const [modalOpen, setModalOpen] = useState<boolean>(false);
    const [modalMessage, setModalMessage] = useState<string>("");
    const [reservaId, setReservaId] = useState<number | null>(null);
    const fechaCreacionReserva = new Date().toISOString();
    const router = useRouter();

    useEffect(() => {
        fetch(`${process.env.NEXT_PUBLIC_API_BASE_URL}/api/v1/consult/restaurantes`,
            {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("SESSION_JWT")}`,
                },
            }
        )
            .then((res) => res.json())
            .then((data) => setRestaurantes(data))
            .catch((err) => console.error("Error consultando restaurantes:", err));
    }, []);

    useEffect(() => {
        setMensaje("");
        if (selectedRestaurante && fechaHora) {
            if (new Date(fechaHora) < new Date()) {
                setMensaje(
                    "La fecha y hora de la reserva no pueden ser anteriores a la hora actual."
                );
                setMesas([]);
                return;
            }
            fetch(
                `${process.env.NEXT_PUBLIC_API_BASE_URL}/api/v1/consult/restaurante/mesas?idRestaurante=${selectedRestaurante}&fechaHora=${fechaHora}`
                ,
                {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${localStorage.getItem("SESSION_JWT")}`,
                    },
                })
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

    useEffect(() => {
        if (reservaId) {
            setModalMessage(`Reserva creada correctamente (${reservaId})`);
        }
        if (reservaId && !modalOpen) {
            router.push("/home/reservas");
        }
    }, [reservaId, modalOpen, router]);

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
            const res = await fetch(
                `${process.env.NEXT_PUBLIC_API_BASE_URL}/api/v1/create/reserva`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(reservaData),
                }
            );
            const responseData = await res.json();
            if (responseData.idReserva) {
                setModalOpen(true);
                setReservaId(responseData.idReserva);
            } else {
                setMensaje("Error al crear la reserva.");
            }
        } catch (error) {
            console.error("Error al crear la reserva:", error);
            setMensaje("Error en la comunicación con el servidor.");
        }
    };

    const isFormValid =
        cedulaUsuario && selectedRestaurante && fechaHora && selectedMesa;

    /* Options para selects */
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

    return (
        <div className="max-w-xl mx-auto p-4">
            <h1 className="text-2xl font-semibold mb-4">Crear Reserva</h1>
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
                    onChange={(e) => setSelectedRestaurante(Number(e.target.value) || "")}
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
                    <label className="block text-gray-700 mb-1">Mesas Disponibles:</label>
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
                <SubmitButton active={!!isFormValid} text="Crear reserva" />
            </form>
            {modalOpen && (
                <Modal message={modalMessage} onClose={() => setModalOpen(false)} />
            )}
        </div>
    );
}
