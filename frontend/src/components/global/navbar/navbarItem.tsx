import Link from "next/link";

export interface NavbarItemProps {
  href: string;
  label: string;
}

export function NavbarItem({ href, label }: NavbarItemProps) {
  return (
    <li>
      <Link href={href}>{label}</Link>
    </li>
  );
}
