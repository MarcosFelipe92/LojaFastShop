import React from "react";

export function Container({ children }: { children: React.ReactNode }) {
  return (
    <div className="flex flex-col items-center justify-center p-4 w-full xl:w-[80%] mx-auto">
      {children}
    </div>
  );
}
