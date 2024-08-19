import React from "react";

export function Container({ children }: { children: React.ReactNode }) {
  return (
    <div className="flex flex-col items-center justify-center p-24 w-[80%] mx-auto">
      {children}
    </div>
  );
}
