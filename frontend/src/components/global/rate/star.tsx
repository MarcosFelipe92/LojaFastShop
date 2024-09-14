import { cn } from "@/lib/utils";
import { FaStar } from "react-icons/fa";

interface StarProps {
  isActive: boolean;
}

export default function Star({ isActive }: StarProps) {
  return (
    <div className="flex">
      <FaStar
        className={cn(
          "text-sm",
          isActive ? "text-orange-300" : "text-gray-200"
        )}
      />
    </div>
  );
}
