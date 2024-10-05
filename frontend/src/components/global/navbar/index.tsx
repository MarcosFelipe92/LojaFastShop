"use client";

import { NavbarActions } from "@/components/global/navbar/navbarActions";
import { NavbarItemProps } from "@/components/global/navbar/navbarItem";
import { NavbarList } from "@/components/global/navbar/navbarList";
import { useSession } from "next-auth/react";
import { NavbarRoot } from "./navbarRoot";

export function Navbar() {
  const { data: session } = useSession();

  const navItemsLeft: NavbarItemProps[] = [
    { href: "/categories", label: "Categorias" },
    { href: "/best-sellers", label: "Mais Vendidos" },
    { href: "/contact", label: "Contato" },
  ];

  const navItemsRight: NavbarItemProps[] = [
    { href: "/history", label: "Histórico" },
    { href: "/cart", label: "Carrinho" },
    { href: "/purchases", label: "Compras" },
  ];

  return (
    <NavbarRoot>
      <NavbarList items={navItemsLeft} />
      <ul className="flex items-center gap-8">
        <NavbarList items={navItemsRight} />
        <NavbarActions session={session} />
      </ul>
    </NavbarRoot>
  );
}
