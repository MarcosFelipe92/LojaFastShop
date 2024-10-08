"use client";

import { getAllProducts, Product } from "@/actions/product/productService";
import { Container } from "@/components/global/container";
import { Header } from "@/components/global/header";
import { HeaderSearchBar } from "@/components/global/header/headerSearchBar";
import { CardProduct } from "@/components/products/card-product";
import { useState, useEffect } from "react";

export default function Home() {
  const [allProducts, setAllProducts] = useState<Product[]>([]);
  const [filteredProducts, setFilteredProducts] = useState<Product[]>([]);

  useEffect(() => {
    const fetchProducts = async () => {
      const products = await getAllProducts();
      setAllProducts(products);
      setFilteredProducts(products);
    };
    fetchProducts();
  }, []);

  const handleSearch = (query: string) => {
    if (query === "") {
      setFilteredProducts(allProducts);
    } else {
      const filtered = allProducts.filter((product) =>
        product.name.toLowerCase().includes(query.toLowerCase())
      );
      setFilteredProducts(filtered);
    }
  };

  return (
    <Container>
      <Header />
      <HeaderSearchBar onSearch={handleSearch} />
      <div className="flex flex-wrap justify-center gap-8 mt-5">
        {filteredProducts.map((product) => (
          <CardProduct.Root key={product.id}>
            <CardProduct.Image
              src={`data:image/jpeg;base64,${product.image}`}
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
