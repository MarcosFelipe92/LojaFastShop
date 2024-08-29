import { NavbarItem } from "@/components/global/navbar/navbarItem";
import { Session } from "next-auth";
import Link from "next/link";

type NavbarActionsProps = {
  session: Session | null;
};

export function NavbarActions({ session }: NavbarActionsProps) {
  return session ? (
    <NavbarItem href="/profile" label="Perfil" />
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
