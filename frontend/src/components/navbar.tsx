export function Navbar() {
  return (
    <div className="flex items-center justify-between w-full max-w-4xl mx-auto">
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
        <li>
          <a href="">Perfil</a>
        </li>
      </ul>
    </div>
  );
}
