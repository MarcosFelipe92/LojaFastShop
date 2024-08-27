import Image from "next/image";
import { Navbar } from "./navbar";
import { Input } from "./input";
import { FaUser } from "react-icons/fa";
import Link from "next/link";

export function Header() {
  return (
    <div className="flex flex-col gap-5 w-full">
      <div className="flex items items-center gap-5 w-full max-w-6xl justify-between mx-auto">
        <div>
          <Link href="/">
            <Image src={"/images/logo.png"} width={70} height={70} alt="Logo" />
          </Link>
        </div>
        <div className="flex-1">
          <form>
            <Input
              startAdornment={<FaUser />}
              placeholder="Pesquisar"
              className="border-lime-300 rounded-lg focus-within:border-lime-500 focus-within:outline focus-within:outline-2 focus-within:outline-lime-500"
            />
          </form>
        </div>
      </div>
      <Navbar />
    </div>
  );
}
