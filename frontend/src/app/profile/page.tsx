"use client";

import { findUser, User } from "@/actions/user/userService";
import { Container } from "@/components/global/container";
import { Header } from "@/components/global/header";
import Link from "next/link";
import { useEffect, useState } from "react";
import { CiCreditCard1 } from "react-icons/ci";
import { FaRegAddressCard } from "react-icons/fa";
import { GrSecure } from "react-icons/gr";
import { LuMapPin } from "react-icons/lu";

export default function PageProfile() {
  const [user, setUser] = useState<User>();

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
      <div className="w-full mt-5 bg-gray-50 p-5 rounded-lg space-y-4">
        <div>
          <h1 className="text-2xl font-bold text-lime-500">{user?.name}</h1>
          <p>{user?.email}</p>
        </div>
        <div className="space-y-6">
          <Link href="" className="flex gap-4 items-center text-xl">
            <FaRegAddressCard className="text-lime-500 text-3xl" />{" "}
            <div>
              <h3>Dados Pessoais</h3>
              <p className="text-sm font-thin">
                Informações do seus documentos, bem como informações de contato
              </p>
            </div>
          </Link>
          <Link href="" className="flex gap-4 items-center text-xl">
            <GrSecure className="text-lime-500 text-3xl" />{" "}
            <div>
              <h3>Segurança</h3>
              <p className="text-sm font-thin">
                Informações e configurações de segurança da sua conta
              </p>
            </div>
          </Link>
          <Link href="" className="flex gap-4 items-center text-xl">
            <LuMapPin className="text-lime-500 text-3xl" />{" "}
            <div>
              <h3>Endereços</h3>
              <p className="text-sm font-thin">Informações do seus endereços</p>
            </div>
          </Link>
          <Link href="" className="flex gap-4 items-center text-xl">
            <CiCreditCard1 className="text-lime-500 text-3xl" />{" "}
            <div>
              <h3>Cartões</h3>
              <p className="text-sm font-thin">Cartões salvos na sua conta</p>
            </div>
          </Link>
        </div>
      </div>
    </Container>
  );
}
