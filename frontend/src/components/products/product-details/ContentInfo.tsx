import React from "react";

interface ContentInfo {
  description: string;
  price: number;
}

export default function ContentInfo({ description, price }: ContentInfo) {
  return (
    <div>
      <p>{description}</p>
      <p className="text-3xl text-lime-500 mt-4">R$ {price}</p>
    </div>
  );
}
