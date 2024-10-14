import Link from "next/link";
import { ReactNode } from "react";

export interface NavbarItemProps {
  href: string;
  label: string | ReactNode;
}

export function NavbarItem({ href, label }: NavbarItemProps) {
  return (
    <li>
      <Link href={href}>{label}</Link>
    </li>
  );
}
