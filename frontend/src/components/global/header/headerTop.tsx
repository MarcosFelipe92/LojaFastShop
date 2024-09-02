import { ReactNode } from "react";

interface HeaderTopProps {
  children: ReactNode;
}

export function HeaderTop({ children }: HeaderTopProps) {
  return (
    <div className="flex flex-col md:flex-row items-center gap-5 w-full max-w-6xl justify-between mx-auto">
      {children}
    </div>
  );
}
