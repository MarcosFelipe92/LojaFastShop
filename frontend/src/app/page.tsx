import { getAllProducts } from "@/actions/product/productService";
import { Container } from "@/components/container";
import { Header } from "@/components/header";
import { SimpleCardProduct } from "@/components/simple-card-product";
import Image from "next/image";
import Link from "next/link";

async function getData() {
  const products = await getAllProducts();
  return products;
}

export default async function Home() {
  const products = await getData();

  return (
    <Container>
      <Header />
      <div className="flex flex-wrap justify-center gap-8 mt-5">
        {products.map((product) => (
          <SimpleCardProduct
            key={product.id}
            name={product.name}
            price={product.price.toString()}
          >
            <Link href={`products/${product.id}`}>
              <Image
                src="/images/produto1.jpg"
                width={150}
                height={150}
                alt="Produto 1"
              />
            </Link>
          </SimpleCardProduct>
        ))}
      </div>
    </Container>
  );
}
