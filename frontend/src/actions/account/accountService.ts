type Address = {
  id: number;
  zipCode: string;
  street: string;
  number: string;
  complement: string;
  neighborhood: string;
  city: string;
  state: string;
  country: string;
  accountId: number;
};

type ShoppingCart = {
  id: number;
  accountId: number;
  items: any[];
};

type Account = {
  id: number;
  userId: number;
  shoppingCart: ShoppingCart;
  addresses: Address[];
};

export const getAccount = async (id: number): Promise<Account> => {
  const response = await fetch(`http://localhost:8080/accounts/${id}`, {
    cache: "no-cache",
  });
  const account = response.json();

  return account;
};
