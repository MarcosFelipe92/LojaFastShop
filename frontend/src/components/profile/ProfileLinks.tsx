import { FaRegAddressCard } from "react-icons/fa";
import { GrSecure } from "react-icons/gr";
import { LuMapPin } from "react-icons/lu";
import { CiCreditCard1 } from "react-icons/ci";
import { ProfileLinkItem } from "./ProfileLinkItem";

export function ProfileLinks() {
  return (
    <div className="space-y-6">
      <ProfileLinkItem
        href="/profile/personal-data"
        icon={<FaRegAddressCard className="text-lime-500 text-3xl" />}
        title="Dados Pessoais"
        description="Informações do seus documentos, bem como informações de contato"
      />
      <ProfileLinkItem
        href="/profile/security"
        icon={<GrSecure className="text-lime-500 text-3xl" />}
        title="Segurança"
        description="Informações e configurações de segurança da sua conta"
      />
      <ProfileLinkItem
        href="/profile/addresses"
        icon={<LuMapPin className="text-lime-500 text-3xl" />}
        title="Endereços"
        description="Informações do seus endereços"
      />
      <ProfileLinkItem
        href="/profile/cards"
        icon={<CiCreditCard1 className="text-lime-500 text-3xl" />}
        title="Cartões"
        description="Cartões salvos na sua conta"
      />
    </div>
  );
}
