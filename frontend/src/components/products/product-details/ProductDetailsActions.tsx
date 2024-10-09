"use client";

import { addItemCart } from "@/actions/shopping-cart/ShoppingCartService";
import { Input } from "@/components/global/input";
import { Button } from "@/components/ui/button";
import { useRouter } from "next/navigation";

import { useState, useTransition } from "react";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

interface ProductDetailsActionsProps {
  productId: number;
}

export function ProductDetailsActions({
  productId,
}: ProductDetailsActionsProps) {
  const [isPending, startTransition] = useTransition();
  const [quantity, setQuantity] = useState(1);
  const router = useRouter();

  const handleAddItem = (productId: number, quantity: number) => {
    startTransition(async () => {
      const response = await addItemCart(productId, quantity);
      if (!response.success) {
        if (response?.error == "Usuário não autenticado!") {
          router.push("/auth/login");
        }
        toast.error(response.error);
      } else {
        toast.success("Item adicionado ao carrinho com sucesso!");
      }
    });
  };

  return (
    <div className="flex flex-col gap-2">
      <ToastContainer />
      <Input
        label="Quantidade"
        value={quantity}
        onChange={(e) => setQuantity(parseInt(e.target.value))}
        type="number"
        className="border-lime-300 rounded-lg focus-within:border-lime-500 focus-within:outline focus-within:outline-2 focus-within:outline-lime-500"
      />

      <Button
        className="w-full"
        onClick={() => handleAddItem(productId, quantity)}
        disabled={isPending}
      >
        {isPending ? "Adicionando..." : "Adicionar ao carrinho"}
      </Button>
      <Button variant="lime" className="w-full">
        Comprar
      </Button>
    </div>
  );
}
