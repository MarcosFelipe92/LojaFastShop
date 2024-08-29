import Image from "next/image";
import Link from "next/link";

export function HeaderLogo() {
  return (
    <Link href="/">
      <Image src="/images/logo.png" width={70} height={70} alt="Logo" />
    </Link>
  );
}
