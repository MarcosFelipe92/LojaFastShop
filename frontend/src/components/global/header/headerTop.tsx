import { ReactNode } from "react";

interface HeaderRootProps {
  children: ReactNode;
}

export function HeaderTop({ children }: HeaderRootProps) {
  return (
    <div className="flex items-center gap-5 w-full max-w-6xl justify-between mx-auto">
      {children}
    </div>
  );
}
