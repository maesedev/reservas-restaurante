import { ChevronDownIcon } from "@heroicons/react/24/outline";
import React, { useState, ReactNode } from "react";

interface DropdownProps {
  title?: string;
  children: ReactNode;
}

const DropdownFullWidth: React.FC<DropdownProps> = ({ title, children }) => {
  const [open, setOpen] = useState(false);

  return (
    <button
      onClick={() => setOpen(!open)}
      className="cursor-pointer bg-transparent p-0 w-full border-2 border-gray-300 border-dashed"
    >
      <div className="w-full">
        <div className="flex items-center relative  rounded-lg bg-white w-full">
          <ChevronDownIcon
            className={`h-10 w-10 mx-7  transform inline-block transition-transform duration-300 ${
              open ? "rotate-180" : "rotate-0"
            }`}
          />
          {title && <span className="mr-2">{title}</span>}
        </div>
        <div>
          {open && <div className="top-full left-0 w-full">{children}</div>}
        </div>
      </div>
    </button>
  );
};

export default DropdownFullWidth;
