"use server";

export type Product = {
  id: number;
  name: string;
  description: string;
  price: number;
  image?: string;
};

export const getAllProducts = async (): Promise<Product[]> => {
  const response = await fetch("http://localhost:8080/products", {
    cache: "no-cache",
  });
  const products = response.json();

  return products;
};

export const getProduct = async (id: number): Promise<Product> => {
  const response = await fetch(`http://localhost:8080/products/${id}`, {
    cache: "no-cache",
  });
  const product = response.json();

  return product;
};
