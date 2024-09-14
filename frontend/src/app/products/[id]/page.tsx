import { getProduct } from "@/actions/product/productService";
import { Container } from "@/components/global/container";
import { Header } from "@/components/global/header";
import Rating from "@/components/global/rate";
import ContentInfo from "@/components/products/product-details/ContentInfo";
import HeaderInfo from "@/components/products/product-details/headerInfo";
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
      <div className="flex justify-center mt-5 space-x-52 w-full">
        <div>
          <Image
            src={`data:image/jpeg;base64,${product.image}`}
            width={400}
            height={400}
            alt="Produto"
          />
        </div>
        <div className="flex flex-col max-w-screen-sm w-full">
          <HeaderInfo
            name={product.name}
            averageRating={5.0}
            totalReviews={100}
          />
          <ContentInfo
            description={product.description}
            price={product.price}
          />
        </div>
      </div>
    </Container>
  );
}
