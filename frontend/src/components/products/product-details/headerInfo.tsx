import Rating from "@/components/global/rate";
import React from "react";

interface HeaderInfoProps {
  name: string;
  averageRating: number;
  totalReviews: number;
}

export default function HeaderInfo({
  name,
  averageRating,
  totalReviews,
}: HeaderInfoProps) {
  return (
    <div className="flex flex-col gap-4 mb-3">
      <h1 className="text-3xl">{name}</h1>
      <Rating
        averageRating={averageRating}
        totalReviews={totalReviews}
        variant="row"
      />
    </div>
  );
}
