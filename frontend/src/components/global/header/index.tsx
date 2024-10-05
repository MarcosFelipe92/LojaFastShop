import { HeaderLogo } from "@/components/global/header/headerLogo";
import { Navbar } from "@/components/global/navbar";

export function Header() {
  return (
    <div className="flex items-center justify-between gap-5 w-full">
      <HeaderLogo />
      <Navbar />
    </div>
  );
}
