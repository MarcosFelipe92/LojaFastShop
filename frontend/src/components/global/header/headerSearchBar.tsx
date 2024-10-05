"use client";

import { Input } from "@/components/global/input";
import { Button } from "@/components/ui/button";
import { Form, FormControl, FormField, FormItem } from "@/components/ui/form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";

type HeaderSearchBarProps = {
  onSearch: (query: string) => void;
};

export function HeaderSearchBar({ onSearch }: HeaderSearchBarProps) {
  const searchSchema = z.object({
    name: z.string(),
  });

  const form = useForm<z.infer<typeof searchSchema>>({
    resolver: zodResolver(searchSchema),
    mode: "all",
    defaultValues: {
      name: "",
    },
  });

  const onSubmit = (values: z.infer<typeof searchSchema>) => {
    // Chama a função passada pelo componente pai
    onSearch(values.name);
  };

  return (
    <Form {...form}>
      <form
        onSubmit={form.handleSubmit(onSubmit)}
        className="flex gap-2 w-full items-center max-w-4xl"
      >
        <FormField
          control={form.control}
          name="name"
          render={({ field }) => (
            <FormItem className="flex-1">
              <FormControl>
                <Input
                  {...field}
                  placeholder="Pesquisar"
                  type="search"
                  className="rounded-lg"
                />
              </FormControl>
            </FormItem>
          )}
        />

        <Button type="submit" className="text-xl" variant="lime">
          Pesquisar
        </Button>
      </form>
    </Form>
  );
}
