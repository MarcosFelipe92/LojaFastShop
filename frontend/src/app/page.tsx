import { getAllProducts } from "@/actions/product/productService";
import { Container } from "@/components/global/container";
import { Header } from "@/components/global/header";
import { CardProduct } from "@/components/products/card-product";

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
          <CardProduct.Root key={product.id}>
            <CardProduct.Image
              src="/images/produto1.jpg"
              alt="Produto"
              productId={product.id}
            />
            <CardProduct.Info
              name={product.name}
              price={product.price}
              averageRating={5.0}
              totalReviews={100}
            />
          </CardProduct.Root>
        ))}
      </div>
    </Container>
  );
}
