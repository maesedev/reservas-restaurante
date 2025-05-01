
export interface Restaurante {
    idRestaurante: number;
    nombre: string;
    direccion: string;
    telefono: string;
    tipoComida: string;
    calificacion: number;
    estado: string;
}

export interface MesasResponse {
    idRestaurante: number;
    mesasDisponibles: number[];
}

export interface Option {
    value: number | string;
    label: string;
}

export interface LabelledInputProps extends React.InputHTMLAttributes<HTMLInputElement> {
    label: string;
}

export interface LabelledSelectProps extends React.SelectHTMLAttributes<HTMLSelectElement> {
    label: string;
    options: Option[];
}

export interface ModalProps {
    message: string;
    onClose: () => void;
}


export interface LabelledTextAreaProps extends React.TextareaHTMLAttributes<HTMLTextAreaElement> {
    label: string;
}



export interface FechaComponentProps {
    value: string;
    onChange: (newFechaHora: string) => void;
    disabled?: boolean;
    required?: boolean;
}


export interface HoraComponentProps {
    value: string;
    onChange: (newFechaHora: string) => void;
    disabled?: boolean;
    required?: boolean;
}
