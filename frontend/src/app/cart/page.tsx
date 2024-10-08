"use client";

import {
  ApiResponse,
  getAllItems,
  Item,
} from "@/actions/shopping-cart/ShoppingCartService";
import { Container } from "@/components/global/container";
import { Header } from "@/components/global/header";
import { Button } from "@/components/ui/button";
import Image from "next/image";
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

  return (
    <Container>
      <Header />
      <div className="flex flex-col justify-center items-center w-full gap-8 mt-5 bg-slate-100 p-4">
        <h1 className="text-2xl font-bold">Carrinho de compras</h1>
        {items.map((item) => (
          <div key={item.id} className="flex w-full gap-4">
            <Image
              width={100}
              height={100}
              src={`data:image/jpeg;base64,${item?.product.image}`}
              alt="Produto"
            />
            <div>
              <p className="font-bold">{item.product?.name}</p>
              <p>Quantidade: {item.quantity}</p>
              <div className="flex gap-2 items-center">
                <p>
                  Subtotal:{" "}
                  <span className="text-lime-500 font-bold">
                    R$ {item.product?.price * item.quantity}
                  </span>
                </p>
                <Button variant="outline">Excluir</Button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </Container>
  );
}
