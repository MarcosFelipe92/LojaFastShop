"use client";

import {
  ApiResponse,
  getAllItems,
  Item,
  removeItemCart,
} from "@/actions/shopping-cart/ShoppingCartService";
import { CartItem } from "@/components/cart/cart-item";
import { Container } from "@/components/global/container";
import { Header } from "@/components/global/header";
import { Button } from "@/components/ui/button";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export default function PageCart() {
  const [items, setItems] = useState<Item[]>([]);
  const router = useRouter();

  useEffect(() => {
    const fetchItems = async () => {
      const cartResponse: ApiResponse<Item[]> = await getAllItems();

      if (cartResponse.success && cartResponse.data) {
        setItems(cartResponse.data);
      } else {
        if (cartResponse?.redirectToLogin) {
          router.push("auth/login");
        }
      }
    };
    fetchItems();
  }, [router]);

  const getTotalPrice = () => {
    return items.reduce(
      (acc, item) => acc + item.product?.price * item.quantity,
      0
    );
  };

  const removeItem = async (id: number) => {
    const response = await removeItemCart(id);
    console.log(response.success);

    if (response.success) {
      setItems(items.filter((item) => item.id !== id));
    }
  };

  return (
    <Container>
      <Header />
      <div className="flex flex-col justify-center items-center w-full gap-4 mt-5 ">
        <h1 className="text-2xl font-bold">Carrinho de compras</h1>
        {items.length === 0 ? (
          <p>Seu carrinho está vazio.</p>
        ) : (
          items.map((item) => (
            <CartItem
              key={item.id}
              item={item}
              onDelete={() => removeItem(item.id)}
            />
          ))
        )}
        {items.length > 0 && (
          <div className="w-full flex flex-col justify-center items-center gap-2">
            <p className="font-bold text-2xl">
              Total ({items.length} Produtos):{" "}
              <span className="text-lime-500">R$ {getTotalPrice()}</span>
            </p>
            <Button variant="lime" className="w-full">
              Fechar pedido
            </Button>
          </div>
        )}
      </div>
    </Container>
  );
}
