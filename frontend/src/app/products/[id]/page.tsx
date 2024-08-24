import { getProduct } from "@/actions/product/productService";
import { Container } from "@/components/container";
import { Header } from "@/components/header";
import Image from "next/image";

export default async function ProductDetails({
  params,
}: {
  params: { id: number };
}) {
  const product = await getProduct(params.id);

  return (
    <Container>
      <Header />
      <div className="flex justify-between mt-5 w-full">
        <div>
          <Image
            src={"/images/produto1.jpg"}
            width={400}
            height={400}
            alt="Produto"
          />
        </div>
        <div className="flex flex-col max-w-screen-sm">
          <h1 className="text-3xl mb-3">{product.name}</h1>
          <p>{`Lorem ipsum dolor, sit amet consectetur adipisicing elit. Minus, nam amet minima laborum suscipit facere. 
          Tempora quia rem nobis, quas dolorum voluptatum ducimus! Quis deleniti dolore nulla, distinctio quod libero.`}</p>
          <p className="text-3xl text-lime-500 mt-4">R$ {product.price}</p>
          <p>{product?.image}</p>
        </div>
      </div>
    </Container>
  );
}
