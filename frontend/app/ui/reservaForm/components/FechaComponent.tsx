import { FechaComponentProps } from "../interfaces";

export const FechaComponent: React.FC<FechaComponentProps> = ({
    value,
    onChange,
    disabled,
    required,
}) => {
    const dateValue = value ? value.substring(0, 10) : "";
    const handleDateChange = (e: React.ChangeEvent<HTMLInputElement>) => {
            const newDate = e.target.value;
            const existingTime = value ? value.substring(11, 16) : "00:00";
            const [year, month, day] = newDate.split('-').map(Number);
            const [hours, minutes] = existingTime.split(':').map(Number);
            const utcDateTime = new Date(Date.UTC(year, month - 1, day, hours, minutes));
            onChange(utcDateTime.toISOString());
    };

    return (
            <div>
                    <label className="block text-gray-700 mb-1">Fecha:</label>
                    <input
                            type="date"
                            value={dateValue}
                            onChange={handleDateChange}
                            disabled={disabled}
                            required={required}
                            className="border rounded px-2 py-1 w-full"
                    />
            </div>
    );
};
