"use client";

import { useTransition } from "react";
import { addItemCart } from "@/actions/shopping-cart/ShoppingCartService";
import { Button } from "@/components/ui/button";
import React, { useState } from "react";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

interface AddCartItemButtonProps {
  productId: number;
}

export function AddCartItemButton({ productId }: AddCartItemButtonProps) {
  const [isPending, startTransition] = useTransition();

  const handleAddToCart = () => {
    startTransition(async () => {
      const success = await addItemCart(productId);
      if (!success) {
        toast.error("Falha ao adicionar o item ao carrinho.");
      } else {
        toast.success("Item adicionado ao carrinho com sucesso!");
      }
    });
  };

  return (
    <div>
      <ToastContainer />
      <Button onClick={handleAddToCart} className="w-full" disabled={isPending}>
        {isPending ? "Adicionando..." : "Adicionar ao carrinho"}
      </Button>
    </div>
  );
}
