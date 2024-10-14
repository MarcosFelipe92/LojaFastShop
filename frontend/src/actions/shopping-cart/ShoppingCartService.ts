"use server";

import { auth } from "@/auth";
import { Product } from "../product/productService";

export type Item = {
  id: number;
  product: Product;
  quantity: number;
  shoppingCartId: number;
};

type Response = {
  message?: string;
};

export type ApiResponse<T> = {
  success: boolean;
  data?: T;
  error?: string;
  redirectToLogin?: boolean;
};

export const addItemCart = async (
  productId: number,
  quantity: number
): Promise<ApiResponse<Response>> => {
  try {
    const session = await auth();

    if (!session?.user) {
      return { success: false, error: "Usuário não autenticado!" };
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
    return { success: true, data: responseData };
  } catch (error: any) {
    console.error(
      "Erro ao adicionar item ao carrinho:",
      error.message || error
    );
    return {
      success: false,
      error: "Falha ao adicionar item. Por favor, tente novamente mais tarde.",
    };
  }
};

export const removeItemCart = async (
  id: number
): Promise<ApiResponse<Response>> => {
  try {
    const session = await auth();

    const { accountId, token } = session?.user;

    const response = await fetch(
      `http://localhost:8080/accounts/${accountId}/shopping-cart/${id}`,
      {
        cache: "no-cache",
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
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
    return { success: true, data: responseData };
  } catch (error: any) {
    console.error(
      "Erro ao adicionar item ao carrinho:",
      error.message || error
    );
    return {
      success: false,
      error: "Falha ao adicionar item. Por favor, tente novamente mais tarde.",
    };
  }
};

export const getAllItems = async (): Promise<ApiResponse<Item[]>> => {
  try {
    const session = await auth();

    if (!session?.user) {
      return { success: false, error: "Usuário não autenticado!" };
    }

    const { accountId, token } = session.user;

    const responseAccount = await fetch(
      `http://localhost:8080/accounts/${accountId}`,
      {
        cache: "no-cache",
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      }
    );

    if (!responseAccount.ok) {
      const errorMessage = await responseAccount.text();
      console.error(
        `Erro ao carregar conta: ${responseAccount.status} - ${errorMessage}`
      );
      if (responseAccount.status === 401) {
        return {
          success: false,
          error: "Token expirado, redirecionando para login.",
          redirectToLogin: true,
        };
      }
      return {
        success: false,
        error: `Erro ${responseAccount.status}: ${errorMessage}`,
      };
    }

    const account = await responseAccount.json();

    const response = await fetch(
      `http://localhost:8080/accounts/${accountId}/shopping-cart/${account.shoppingCart.id}`,
      {
        cache: "no-cache",
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      }
    );

    if (!response.ok) {
      const errorMessage = await response.text();
      console.error(
        `Erro ao carregar carrinho: ${response.status} - ${errorMessage}`
      );
      return {
        success: false,
        error: `Erro ${response.status}: ${errorMessage}`,
      };
    }

    const responseData = await response.json();
    return { success: true, data: responseData.items };
  } catch (error: any) {
    console.error("Erro ao carregar carrinho:", error.message || error);
    return {
      success: false,
      error:
        "Falha ao carregar carrinho. Por favor, tente novamente mais tarde.",
    };
  }
};
