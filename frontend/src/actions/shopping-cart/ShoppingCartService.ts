"use server";

import { auth } from "@/auth";

type Response = {
  message?: string;
  error?: string;
};

export const addItemCart = async (
  productId: number,
  quantity: number
): Promise<Response> => {
  try {
    const session = await auth();

    if (!session?.user) {
      return { error: "Usuário não autenticado!" };
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
        body: JSON.stringify({
          product: { id: productId },
          quantity,
        }),
      }
    );

    if (!response.ok) {
      const errorMessage = await response.text();
      console.error(
        `Erro ao adicionar item ao carrinho: ${response.status} - ${errorMessage}`
      );
      throw new Error(`Erro ${response.status}: ${errorMessage}`);
    }

    const responseData = await response.json();
    return responseData;
  } catch (error: any) {
    console.error(
      "Erro ao adicionar item ao carrinho:",
      error.message || error
    );
    return {
      error: "Falha ao adicionar item. Por favor, tente novamente mais tarde.",
    };
  }
};
