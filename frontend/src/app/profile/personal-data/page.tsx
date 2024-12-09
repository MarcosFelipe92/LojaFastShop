"use client";

import { findUser, User } from "@/actions/user/userService";
import { Container } from "@/components/global/container";
import { Header } from "@/components/global/header";
import { Breadcrumb } from "@/components/profile/Breadcrumb";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export default function PagePersonalData() {
  const [user, setUser] = useState<User>();

  const breadcrumbItems = [
    { label: "Meu Perfil", href: "/profile" },
    { label: "Minhas Informações", href: "/profile/personal-data" },
  ];

  useEffect(() => {
    const fetchUser = async () => {
      const user = await findUser();
      setUser(user);
    };
    fetchUser();
  }, []);

  return (
    <Container>
      <Header />
      <div className="w-full mt-4 space-y-4">
        <Breadcrumb
          currentPath={"/profile/personal-data"}
          items={breadcrumbItems}
        />
        <h1 className="text-xl font-bold">Dados Pessoais</h1>
        <div className="flex items-center gap-16 bg-lime-100 p-5 rounded-lg">
          <div>
            <h3 className="text-lg font-semibold">Nome completo</h3>
            <p>{user?.name}</p>
          </div>
          <div>
            <h3 className="text-lg font-semibold">CPF</h3>
            <p>{user?.cpf}</p>
          </div>
        </div>
        <div className="bg-lime-100 p-5 rounded-lg space-y-2">
          <h3 className="text-xl font-bold">Telefones</h3>
          {user?.phones?.map((phone) => (
            <div key={user.id} className="flex gap-2">
              <p>{phone.number}</p>
              <p>{phone.type}</p>
            </div>
          ))}
        </div>
      </div>
    </Container>
  );
}
