import { auth } from "@/auth";
import Link from "next/link";

export async function Navbar() {
  const session = await auth();

  console.log(session);

  return (
    <div className="flex items-center justify-between w-full max-w-4xl mx-auto text-xl">
      <ul className="flex items-center gap-8">
        <li>
          <a href="">Categorias</a>
        </li>
        <li>
          <a href="">Mais Vendidos</a>
        </li>
        <li>
          <a href="">Contato</a>
        </li>
      </ul>
      <ul className="flex items-center gap-8">
        <li>
          <a href="">Histórico</a>
        </li>
        <li>
          <a href="">Carrinho</a>
        </li>
        <li>
          <a href="">Compras</a>
        </li>
        {session ? (
          <li>
            <a href="">Perfil</a>
          </li>
        ) : (
          <li>
            <Link
              href="/auth/login"
              className="bg-gray-900 rounded-md py-2 px-3 text-white hover:bg-gray-700"
            >
              Entrar
            </Link>
          </li>
        )}
      </ul>
    </div>
  );
}
