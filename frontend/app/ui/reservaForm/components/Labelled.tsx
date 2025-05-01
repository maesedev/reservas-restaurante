import React from 'react';
import { LabelledInputProps, LabelledSelectProps, LabelledTextAreaProps } from '../interfaces';


export const LabelledInput: React.FC<LabelledInputProps> = ({ label, ...inputProps }) => (
    <div>
        <label className="block text-gray-700 mb-1">{label}</label>
        <input
            className="w-full px-3 py-2 border rounded focus:outline-none focus:ring"
            {...inputProps}
        />
    </div>
);


export const LabelledSelect: React.FC<LabelledSelectProps> = ({ label, options, ...selectProps }) => (
    <div>
        <label className="block text-gray-700 mb-1">{label}</label>
        <select
            className="w-full px-3 py-2 border rounded focus:outline-none focus:ring"
            {...selectProps}
        >
            {options.map((option, idx) => (
                <option key={idx} value={option.value}>
                    {option.label}
                </option>
            ))}
        </select>
    </div>
);


export const LabelledTextArea: React.FC<LabelledTextAreaProps> = ({
  label,
  ...textareaProps
}) => (
  <div>
    <label className="block text-gray-700 mb-1">{label}</label>
    <textarea
      className="w-full px-3 py-2 border rounded focus:outline-none focus:ring"
      {...textareaProps}
    />
  </div>
);