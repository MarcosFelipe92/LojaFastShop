"use client";

import { register } from "@/actions/auth/register";
import { formatPhoneNumber } from "@/lib/utils";
import { registerSchema } from "@/schema";
import { zodResolver } from "@hookform/resolvers/zod";
import { useState, useTransition } from "react";
import { useForm } from "react-hook-form";
import { FaRegUser } from "react-icons/fa";
import { FiPhone } from "react-icons/fi";
import { IoIosPhonePortrait } from "react-icons/io";
import { MdAlternateEmail } from "react-icons/md";
import { PiPassword } from "react-icons/pi";
import { z } from "zod";
import { Input } from "../input";
import { Button } from "../ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "../ui/form";
import { CardWrapper } from "./card-wrapper";
import FormError from "./form-error";
import FormSuccess from "./form-sucess";

export default function FormRegister() {
  const [isPending, startTransition] = useTransition();
  const [error, setError] = useState<string | undefined>("");
  const [success, setSuccess] = useState<string | undefined>("");

  const form = useForm<z.infer<typeof registerSchema>>({
    resolver: zodResolver(registerSchema),
    mode: "all",
    defaultValues: {
      name: "",
      email: "",
      password: "",
      phones: [{ number: "", type: "" }],
    },
  });

  const onSubmit = (values: z.infer<typeof registerSchema>) => {
    setError("");
    setSuccess("");

    startTransition(() => {
      register(values).then((data) => {
        if (data?.error) {
          setError(data.error);
        } else if (data?.success) {
          setSuccess(data.success);
        }
      });
    });
  };

  const addPhone = () => {
    form.setValue("phones", [
      ...form.getValues("phones"),
      { number: "", type: "" },
    ]);
  };

  const removePhone = (index: number) => {
    const phones = form.getValues("phones");

    if (phones.length > 1) {
      phones.splice(index, 1);
      form.setValue("phones", phones);
    } else {
      setError("Você deve adicionar pelo menos um telefone.");
    }
  };

  return (
    <CardWrapper
      headerLabel="Crie uma conta!"
      backButtonLabel="Já tem uma conta?"
      backButtonHref="/auth/login"
      variant="register"
    >
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
          <div className="space-y-4">
            <FormField
              {...form}
              control={form.control}
              name="name"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Nome</FormLabel>
                  <FormControl>
                    <Input
                      {...field}
                      disabled={isPending}
                      placeholder="João da Silva"
                      type="text"
                      startAdornment={<FaRegUser />}
                      className="border-gray-300 rounded-lg focus-within:border-gray-500 focus-within:outline focus-within:outline-2 focus-within:outline-gray-500"
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="email"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Email</FormLabel>
                  <FormControl>
                    <Input
                      {...field}
                      disabled={isPending}
                      placeholder="exemplo@gmail.com"
                      type="email"
                      startAdornment={<MdAlternateEmail />}
                      className="border-gray-300 rounded-lg focus-within:border-gray-500 focus-within:outline focus-within:outline-2 focus-within:outline-gray-500"
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              {...form}
              control={form.control}
              name="password"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Senha</FormLabel>
                  <FormControl>
                    <Input
                      {...field}
                      disabled={isPending}
                      placeholder="Xy123456"
                      type="password"
                      startAdornment={<PiPassword />}
                      className="border-gray-300 rounded-lg focus-within:border-gray-500 focus-within:outline focus-within:outline-2 focus-within:outline-gray-500"
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            {form.watch("phones").map((_, index) => (
              <div key={index} className="flex items-end gap-2">
                <FormField
                  control={form.control}
                  name={`phones.${index}.number`}
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Número de Telefone</FormLabel>
                      <FormControl>
                        <Input
                          value={formatPhoneNumber(field.value)}
                          onChange={(e) => field.onChange(e.target.value)}
                          disabled={isPending}
                          placeholder="11 91234-5678"
                          type="text"
                          startAdornment={<FiPhone />}
                          className="border-gray-300 rounded-lg focus-within:border-gray-500 focus-within:outline focus-within:outline-2 focus-within:outline-gray-500"
                        />
                      </FormControl>
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name={`phones.${index}.type`}
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Tipo</FormLabel>
                      <FormControl>
                        <Input
                          {...field}
                          disabled={isPending}
                          placeholder="Casa, Trabalho, etc."
                          type="text"
                          startAdornment={<IoIosPhonePortrait />}
                          className="border-gray-300 rounded-lg focus-within:border-gray-500 focus-within:outline focus-within:outline-2 focus-within:outline-gray-500"
                        />
                      </FormControl>
                    </FormItem>
                  )}
                />
                <Button
                  type="button"
                  onClick={() => removePhone(index)}
                  disabled={isPending}
                  className="mb-1"
                >
                  Remover
                </Button>
              </div>
            ))}
            <Button type="button" onClick={addPhone} disabled={isPending}>
              Adicionar Telefone
            </Button>
          </div>
          <FormError message={error} />
          <FormSuccess message={success} />
          <Button type="submit" disabled={isPending} className="w-full">
            Registrar
          </Button>
        </form>
      </Form>
    </CardWrapper>
  );
}
