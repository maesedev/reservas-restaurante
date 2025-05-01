import { ModalProps } from "../interfaces";

export const Modal: React.FC<ModalProps> = ({ message, onClose }) => (
  <div className="fixed inset-0 flex items-center justify-center bg-[#0008]">
    <div className="bg-white p-6 rounded shadow-lg">
      <h2 className="text-xl font-bold mb-4">{message}</h2>
      <button
        onClick={onClose}
        className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
      >
        Cerrar
      </button>
    </div>
  </div>
);