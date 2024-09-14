import Rating from "@/components/global/rate";

interface CardProductInfoProps {
  name: string;
  price: number;
  averageRating: number;
  totalReviews: number;
}

export function CardProductInfo({
  name,
  price,
  averageRating,
  totalReviews,
}: CardProductInfoProps) {
  return (
    <div className="flex flex-col gap-3 p-4 items-center text-center">
      <h4 className="text-sm sm:text-base md:text-xl font-semibold text-gray-800">
        {name}
      </h4>
      <h4 className="text-sm sm:text-base md:text-xl text-lime-500">
        R$ {price}
      </h4>
      <Rating averageRating={averageRating} totalReviews={totalReviews} />
    </div>
  );
}
