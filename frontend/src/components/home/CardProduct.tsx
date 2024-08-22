interface CardProductProps {
  children: React.ReactNode;
  name: string;
  price: String;
}

export function CardProduct({ children, name, price }: CardProductProps) {
  return (
    <div>
      <div className="w-[200px] p-3 flex flex-col justify-center items-center">
        <div>{children}</div>
        <div>
          <div className="flex flex-col gap-3 p-4 items-center mx-auto">
            <h4 className="text-xl">{name}</h4>
            <h4 className="text-xl">R$ {price}</h4>
          </div>
        </div>
      </div>
    </div>
  );
}
