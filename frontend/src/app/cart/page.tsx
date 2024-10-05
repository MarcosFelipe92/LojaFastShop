import { auth } from "@/auth";
import { Container } from "@/components/global/container";
import { Header } from "@/components/global/header";
import React from "react";

export default async function PageCart() {
  const session = await auth();

  return (
    <Container>
      <Header />
    </Container>
  );
}
