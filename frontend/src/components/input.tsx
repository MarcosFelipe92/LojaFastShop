import { forwardRef, InputHTMLAttributes, ReactNode, useId } from "react";

type InputProps = {
  className?: string;
  label?: string;
  startAdornment?: ReactNode;
  endAdornment?: ReactNode;
} & InputHTMLAttributes<HTMLInputElement>;

export const Input = forwardRef<HTMLInputElement, InputProps>(
  (
    {
      name = "",
      label = "",
      className = "",
      type = "text",
      startAdornment,
      endAdornment,
      ...props
    },
    ref
  ) => {
    const labelId = useId();
    return (
      <div className={className}>
        {label && <label htmlFor={labelId}>{label}</label>}
        <div className="flex items-center p-2 gap-3 border border-gray-300 rounded-lg focus-within:border-gray-500 focus-within:outline focus-within:outline-2 focus-within:outline-gray-500">
          {startAdornment && <span>{startAdornment}</span>}
          <input
            ref={ref}
            type={type}
            name={name}
            id={labelId}
            {...props}
            className="w-full h-6 bg-transparent border-none outline-none"
          />
          {endAdornment && <span>{endAdornment}</span>}
        </div>
      </div>
    );
  }
);
