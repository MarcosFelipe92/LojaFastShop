import { Container } from "@/components/container";
import { Header } from "@/components/header";
import { CardProduct } from "@/components/home/CardProduct";
import Image from "next/image";

export default function Home() {
  return (
    <Container>
      <Header />
      <div className="flex flex-wrap justify-center mt-5">
        <CardProduct name="Produto 1" price={"25.50"}>
          <Image
            src="/images/produto1.jpg"
            width={150}
            height={150}
            alt="Produto 1"
          />
        </CardProduct>
        <CardProduct name="Produto 1" price={"25.50"}>
          <Image
            src="/images/produto1.jpg"
            width={150}
            height={150}
            alt="Produto 1"
          />
        </CardProduct>
        <CardProduct name="Produto 1" price={"25.50"}>
          <Image
            src="/images/produto1.jpg"
            width={150}
            height={150}
            alt="Produto 1"
          />
        </CardProduct>
        <CardProduct name="Produto 1" price={"25.50"}>
          <Image
            src="/images/produto1.jpg"
            width={150}
            height={150}
            alt="Produto 1"
          />
        </CardProduct>
        <CardProduct name="Produto 1" price={"25.50"}>
          <Image
            src="/images/produto1.jpg"
            width={150}
            height={150}
            alt="Produto 1"
          />
        </CardProduct>
        <CardProduct name="Produto 1" price={"25.50"}>
          <Image
            src="/images/produto1.jpg"
            width={150}
            height={150}
            alt="Produto 1"
          />
        </CardProduct>
        <CardProduct name="Produto 1" price={"25.50"}>
          <Image
            src="/images/produto1.jpg"
            width={150}
            height={150}
            alt="Produto 1"
          />
        </CardProduct>
        <CardProduct name="Produto 1" price={"25.50"}>
          <Image
            src="/images/produto1.jpg"
            width={150}
            height={150}
            alt="Produto 1"
          />
        </CardProduct>
        <CardProduct name="Produto 1" price={"25.50"}>
          <Image
            src="/images/produto1.jpg"
            width={150}
            height={150}
            alt="Produto 1"
          />
        </CardProduct>
        <CardProduct name="Produto 1" price={"25.50"}>
          <Image
            src="/images/produto1.jpg"
            width={150}
            height={150}
            alt="Produto 1"
          />
        </CardProduct>
        <CardProduct name="Produto 1" price={"25.50"}>
          <Image
            src="/images/produto1.jpg"
            width={150}
            height={150}
            alt="Produto 1"
          />
        </CardProduct>
        <CardProduct name="Produto 1" price={"25.50"}>
          <Image
            src="/images/produto1.jpg"
            width={150}
            height={150}
            alt="Produto 1"
          />
        </CardProduct>
        <CardProduct name="Produto 1" price={"25.50"}>
          <Image
            src="/images/produto1.jpg"
            width={150}
            height={150}
            alt="Produto 1"
          />
        </CardProduct>
      </div>
    </Container>
  );
}
