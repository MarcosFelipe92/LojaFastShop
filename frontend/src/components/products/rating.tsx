import Star from "./star";

interface RatingProps {
  averageRating: number;
  totalReviews: number;
  variant?: "column" | "row";
}

export default function Rating({
  averageRating,
  totalReviews,
  variant = "column",
}: RatingProps) {
  const totalStars = [1, 2, 3, 4, 5];
  return (
    <div
      className={
        variant == "row"
          ? "flex items-center gap-2"
          : "flex flex-col items-center justify-center"
      }
    >
      <div className="flex gap-2 items-center">
        <span className="text-sm">{averageRating}</span>
        {totalStars.map((index) => (
          <Star key={index} isActive />
        ))}
      </div>
      <p className="text-sm">{totalReviews} avaliações dos clientes</p>
    </div>
  );
}

// export default function Rating({ averageRating, totalReviews }: RatingProps) {
//     const totalStars = [1, 2, 3, 4, 5];
//     const [activeIndex, setActiveIndex] = useState(0);

//     function handleRating(index: number) {
//       setActiveIndex(index);
//     }

//     return (
//       <div className="flex flex-col items-center justify-center">
//         <div className="flex gap-2 items-center">
//           {averageRating}
//           {totalStars.map((index) => (
//             <div key={index} onClick={() => handleRating(index)}>
//               <Star isActive={index <= activeIndex} />
//             </div>
//           ))}
//         </div>
//         <p className="text-sm">{totalReviews} avaliações dos clientes</p>
//       </div>
//     );
//   }
