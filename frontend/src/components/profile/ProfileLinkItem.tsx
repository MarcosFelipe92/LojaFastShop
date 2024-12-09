import Link from "next/link";

type ProfileLinkItemProps = {
  href: string;
  icon: JSX.Element;
  title: string;
  description: string;
};

export function ProfileLinkItem({
  href,
  icon,
  title,
  description,
}: ProfileLinkItemProps) {
  return (
    <Link href={href} className="flex gap-4 items-center text-xl">
      {icon}
      <div>
        <h3>{title}</h3>
        <p className="text-sm font-thin">{description}</p>
      </div>
    </Link>
  );
}
