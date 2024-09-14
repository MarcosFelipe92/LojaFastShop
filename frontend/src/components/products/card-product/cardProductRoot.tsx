import { ReactNode } from "react";

interface CardProductRootProps {
  children: ReactNode;
}

export function CardProductRoot({ children }: CardProductRootProps) {
  return (
    <div className="w-[150px] lg:w-[170px] xl:w-[170px] p-3 flex flex-col justify-center items-center bg-white border border-gray-200 rounded-lg shadow-md transition-transform transform hover:scale-105">
      {children}
    </div>
  );
}
