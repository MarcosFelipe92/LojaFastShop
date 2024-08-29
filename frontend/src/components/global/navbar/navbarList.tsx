import {
  NavbarItem,
  NavbarItemProps,
} from "@/components/global/navbar/navbarItem";

interface NavbarListProps {
  items: NavbarItemProps[];
}

export function NavbarList({ items }: NavbarListProps) {
  return (
    <ul className="flex items-center gap-8">
      {items.map((item) => (
        <NavbarItem key={item.href} href={item.href} label={item.label} />
      ))}
    </ul>
  );
}
