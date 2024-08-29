import { ReactNode } from "react";

interface NavbarRootProps {
  children: ReactNode;
}
export function NavbarRoot({ children }: NavbarRootProps) {
  return (
    <div className="flex items-center justify-between w-full max-w-4xl mx-auto text-xl">
      {children}
    </div>
  );
}
