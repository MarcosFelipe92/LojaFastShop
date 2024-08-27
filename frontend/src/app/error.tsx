"use client";

import { Container } from "@/components/container";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

export default function Error() {
  return (
    <Container>
      <Card className="p-3">
        <CardHeader>
          <CardTitle>
            <h1 className="text-3xl text-destructive">
              Um erro inesperado aconteceu.
            </h1>
          </CardTitle>
        </CardHeader>
        <CardContent>
          <p>Espere alguns minutos e tente novamente</p>
        </CardContent>
      </Card>
    </Container>
  );
}
