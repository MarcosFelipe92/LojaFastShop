import { HeaderTop } from "@/components/global/header/headerTop";
import { HeaderLogo } from "@/components/global/header/headerLogo";
import { HeaderSearchBar } from "@/components/global/header/headerSearchBar";
import { Navbar } from "@/components/global/navbar";

export function Header() {
  return (
    <div className="flex flex-col gap-5 w-full">
      <HeaderTop>
        <HeaderLogo />
        <div className="flex-1">
          <HeaderSearchBar />
        </div>
      </HeaderTop>
      <Navbar />
    </div>
  );
}
