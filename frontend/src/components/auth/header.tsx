import { cn } from "@/lib/utils";
import { Poppins } from "next/font/google";
import { BsLightningChargeFill } from "react-icons/bs";

const font = Poppins({
  subsets: ["latin"],
  weight: ["600"],
});

interface HeaderProps {
  label: string;
}

export function Header({ label }: HeaderProps) {
  return (
    <div className="flex flex-col items-center justify-center gap-y-4 w-full">
      <div className={cn("flex items-center", font.className)}>
        <BsLightningChargeFill className="text-4xl" />
        <h1 className={cn("text-4xl font-semibold text-gray-950")}>FastShop</h1>
      </div>
      <p className="text-muted-foreground text-sm">{label}</p>
    </div>
  );
}
