"use server";

import { auth } from "@/auth";

export interface User {
  id?: number;
  name: string;
  email: string;
  cpf: string;
  password: string;
  phones?: Phone[];
  roles?: Role[];
  error?: string;
}

interface Phone {
  id?: number;
  number: string;
  type: string;
  userId?: number;
}

interface Role {
  name: string;
}

export const createUser = async (
  name: string,
  email: string,
  password: string,
  phones: Phone[]
): Promise<User | undefined> => {
  try {
    const response = await fetch("http://localhost:8080/users/register", {
      method: "POST",
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify({
        name,
        email,
        password,
        phones,
      }),
    });

    const userData = await response.json();

    return userData;
  } catch (error) {
    console.log(error);
  }
};

export const findUser = async (): Promise<User | undefined> => {
  try {
    const session = await auth();

    if (!session?.user) {
      throw new Error("Usuário não autenticado!");
    }

    const { token, id } = session.user;

    const response = await fetch(`http://localhost:8080/users/${id}`, {
      method: "GET",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      throw new Error("Erro ao buscar o usuário");
    }

    const user = await response.json();
    return user;
  } catch (error) {
    console.error(error);
    return undefined;
  }
};
