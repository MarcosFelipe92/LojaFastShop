"use server";

import { auth } from "@/auth";

type Product = {
  id: number;
  name: string;
  description: string;
  price: number;
  image?: string;
};

type ItemCart = {
  id: number;
  product: Product;
  shoppingCartId: number;
};

export const addItemCart = async (productId: number): Promise<boolean> => {
  try {
    const session = await auth();
    if (!session?.user) {
      throw new Error("Usuário não autenticado");
    }

    const { accountId, token } = session.user;

    const response = await fetch(
      `http://localhost:8080/accounts/${accountId}/shopping-cart`,
      {
        cache: "no-cache",
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ product: { id: productId } }),
      }
    );

    if (!response.ok) {
      const errorMessage = await response.text();
      console.error(
        `Erro ao adicionar item ao carrinho: ${response.status} - ${errorMessage}`
      );
      throw new Error(`Erro ${response.status}: ${errorMessage}`);
    }

    return true;
  } catch (error) {
    console.error("Erro na função addItemCart:", error);
    return false;
  }
};
