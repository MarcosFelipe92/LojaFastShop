import Image from "next/image";
import Link from "next/link";

interface CardProductImageProps {
  productId: number;
  src: string;
  alt: string;
}

export function CardProductImage({
  productId,
  src,
  alt,
}: CardProductImageProps) {
  return (
    <Link href={`products/${productId}`}>
      <Image
        src={src}
        width={150}
        height={150}
        alt={alt}
        className="object-cover w-full h-[120px] md:h-[150px]"
      />
    </Link>
  );
}
