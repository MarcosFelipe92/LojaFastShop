import { NavbarItem } from "@/components/global/navbar/navbarItem";
import { Session } from "next-auth";
import Link from "next/link";
import { FaRegUserCircle } from "react-icons/fa";

type NavbarActionsProps = {
  session: Session | null;
};

export function NavbarActions({ session }: NavbarActionsProps) {
  return session ? (
    <NavbarItem href="/profile" label={<FaRegUserCircle />} />
  ) : (
    <li>
      <Link
        href="/auth/login"
        className="bg-gray-900 rounded-md py-2 px-3 text-white hover:bg-gray-700"
      >
        Entrar
      </Link>
    </li>
  );
}
