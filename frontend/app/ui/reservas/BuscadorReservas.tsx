import { FilterOptions } from "@/hooks/useSearchReserva";


export const BuscadorReservas = ({filterOptions, handleInputChange, clearFilters}:{
    filterOptions: FilterOptions;
    handleInputChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    clearFilters: () => void;
}) => {
    return (
            <div className="mb-8 bg-white p-4 rounded">
                <h2 className="text-xl font-bold mb-4">Buscar reservas</h2>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <input
                        type="text"
                        name="cedula"
                        placeholder="Cédula"
                        value={filterOptions.cedula || ""}
                        onChange={handleInputChange}
                        className="border p-2 rounded"
                    />
                    <input
                        type="text"
                        name="id"
                        placeholder="ID Reserva"
                        value={filterOptions.id || ""}
                        onChange={handleInputChange}
                        className="border p-2 rounded"
                    />
                    <input
                        type="number"
                        name="idRestaurante"
                        placeholder="ID Restaurante"
                        value={filterOptions.idRestaurante || ""}
                        onChange={handleInputChange}
                        className="border p-2 rounded"
                    />
                    <input
                        type="date"
                        name="fechaDesde"
                        placeholder="Fecha Desde"
                        value={filterOptions.fechaDesde || ""}
                        onChange={handleInputChange}
                        className="border p-2 rounded"
                    />
                    <input
                        type="date"
                        name="fechaHasta"
                        placeholder="Fecha Hasta"
                        value={filterOptions.fechaHasta || ""}
                        onChange={handleInputChange}
                        className="border p-2 rounded"
                    />
                    <input
                        type="time"
                        name="horaDesde"
                        placeholder="Hora Desde"
                        value={filterOptions.horaDesde || ""}
                        onChange={handleInputChange}
                        className="border p-2 rounded"
                    />
                    <input
                        type="time"
                        name="horaHasta"
                        placeholder="Hora Hasta"
                        value={filterOptions.horaHasta || ""}
                        onChange={handleInputChange}
                        className="border p-2 rounded"
                    />
                </div>
                <div className="mt-4">
                    <button
                        onClick={clearFilters}
                        className="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded mr-2"
                    >
                        Limpiar filtros
                    </button>
                </div>
            </div>
    );
}