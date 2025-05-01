import { HoraComponentProps } from "../interfaces";

export const HoraComponent: React.FC<HoraComponentProps> = ({
        value,
        onChange,
        disabled,
        required,
}) => {
        const timeValue = value ? value.substring(11, 16) : "";
        const handleTimeChange = (e: React.ChangeEvent<HTMLInputElement>) => {
                const newTime = e.target.value;
                const existingDate = value ? value.substring(0, 10) : new Date().toISOString().substring(0, 10);
                const [year, month, day] = existingDate.split('-').map(Number);
                const [hours, minutes] = newTime.split(':').map(Number);
                const utcDateTime = new Date(Date.UTC(year, month - 1, day, hours, minutes));
                onChange(utcDateTime.toISOString());
        };

        return (
                <div>
                        <label className="block text-gray-700 mb-1">Hora:</label>
                        <input
                                type="time"
                                value={timeValue}
                                onChange={handleTimeChange}
                                disabled={disabled}
                                required={required}
                                className="border rounded px-2 py-1 w-full"
                        />
                </div>
        );
};
