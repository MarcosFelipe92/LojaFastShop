import Image from "next/image";
import { Navbar } from "./navbar";
import { Input } from "./input";
import { FaUser } from "react-icons/fa";

export function Header() {
  return (
    <div className="flex flex-col gap-5 w-full">
      <div className="flex items items-center gap-5 w-full max-w-6xl mx-auto">
        <div>
          <a href="/">
            <Image
              src={"/images/logo2.png"}
              width={100}
              height={100}
              alt="Logo"
            />
          </a>
        </div>
        <div className="flex-1">
          <form>
            <Input
              startAdornment={<FaUser className="text-white" />}
              placeholder="Pesquisar"
            />
          </form>
        </div>
      </div>
      <Navbar />
    </div>
  );
}
