import { z } from "zod";

export const loginSchema = z.object({
  email: z.string().email("Email é obrigatório"),
  password: z.string().min(1, "Senha é obrigatória"),
});

const phoneSchema = z.object({
  number: z
    .string()
    .min(11, "O número de telefone deve ter pelo menos 10 dígitos"),
  type: z.string().min(3, "O tipo deve ter pelo menos 3 caracteres"),
});

export const registerSchema = z.object({
  name: z.string().min(1, "Nome é obrigatório"),
  email: z.string().email("Email é obrigatório"),
  password: z.string().min(6, "A senha deve ter no mínimo 6 caracteres"),
  phones: z.array(phoneSchema).min(1, "Pelo menos um telefone é necessário"),
});
