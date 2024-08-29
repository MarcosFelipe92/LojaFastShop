import { IoIosSearch } from "react-icons/io";
import { Input } from "@/components/global/input";

export function HeaderSearchBar() {
  return (
    <form>
      <Input
        startAdornment={<IoIosSearch />}
        placeholder="Pesquisar"
        className="border-lime-300 rounded-lg focus-within:border-lime-500 focus-within:outline focus-within:outline-2 focus-within:outline-lime-500"
      />
    </form>
  );
}
