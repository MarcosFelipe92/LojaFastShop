import { Item } from "@/actions/shopping-cart/ShoppingCartService";
import { Button } from "@/components/ui/button";
import Image from "next/image";

interface CartItemProps {
  item: Item;
  onDelete: (id: number) => void;
}

export function CartItem({ item, onDelete }: CartItemProps) {
  return (
    <div key={item.id} className="flex w-full gap-4 bg-white p-4 rounded-lg">
      <Image
        width={100}
        height={100}
        src={`data:image/jpeg;base64,${item.product.image}`}
        alt="Produto"
      />
      <div>
        <p className="font-bold">{item.product.name}</p>
        <p>Quantidade: {item.quantity}</p>
        <div className="flex gap-2 items-center">
          <p>
            Subtotal:{" "}
            <span className="text-lime-500 font-bold">
              R$ {item.product.price * item.quantity}
            </span>
          </p>
          <Button variant="outline" onClick={() => onDelete(item.id)}>
            Excluir
          </Button>
        </div>
      </div>
    </div>
  );
}
