"use server";

import { z } from "zod";
import { registerSchema } from "@/schema/index";
import { createUser } from "../user/userService";
import {} from "@/actions/user/userService";
import { error } from "console";

export const register = async (values: z.infer<typeof registerSchema>) => {
  const validateFields = registerSchema.safeParse(values);

  if (!validateFields.success) return { error: "Campos inválidos!" };

  const { email, password, name, phones } = validateFields.data;

  const user = await createUser(name, email, password, phones);

  if (user?.error) return { error: user.error };

  if (!user) return { error: "Erro desconhecido, tente novamente mais tarde!" };

  return { success: "Usuário criado!" };
};
