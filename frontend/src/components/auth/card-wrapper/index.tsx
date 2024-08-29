"use client";

import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
} from "@/components/ui/card";
import { Header } from "@/components/auth/header";
import { BackButton } from "@/components/auth/back-button";
import { cn } from "@/lib/utils";

interface CardWrapperProps {
  children: React.ReactNode;
  headerLabel: string;
  backButtonLabel: string;
  backButtonHref: string;
  showSocial?: boolean;
  variant?: "login" | "register";
}

export function CardWrapper({
  children,
  headerLabel,
  backButtonHref,
  backButtonLabel,
  variant = "login",
}: CardWrapperProps) {
  return (
    <div>
      <Card
        className={cn(
          "shadow-md",
          variant == "register" ? "w-[600px]" : "w-[400px]"
        )}
      >
        <CardHeader>
          <Header label={headerLabel} />
        </CardHeader>
        <CardContent>{children}</CardContent>
        <CardFooter>
          <BackButton href={backButtonHref} label={backButtonLabel} />
        </CardFooter>
      </Card>
    </div>
  );
}
