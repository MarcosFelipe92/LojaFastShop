import Rating from "./rating";

interface SimpleCardProductProps {
  children: React.ReactNode;
  name: string;
  price: string;
}

export function SimpleCardProduct({
  children,
  name,
  price,
}: SimpleCardProductProps) {
  return (
    <div className="w-[150px] lg:w-[170px] xl:w-[170px] p-3 flex flex-col justify-center items-center bg-white border border-gray-200 rounded-lg shadow-md transition-transform transform hover:scale-105">
      <div>{children}</div>
      <div className="flex flex-col gap-3 p-4 items-center text-center">
        <h4 className="text-sm sm:text-base md:text-xl font-semibold text-gray-800">
          {name}
        </h4>
        <h4 className="text-sm sm:text-base md:text-xl text-lime-500">
          R$ {price}
        </h4>
        <Rating averageRating={4.9} totalReviews={100} />
      </div>
    </div>
  );
}
