import FormRegister from "@/components/auth/form-register";

export default function PageRegister() {
  return (
    <div className="flex w-full h-screen bg-gradient-to-r from-[#FAF8F9] to-white">
      <div className="bg-[url('/images/fundoLogin.jpg')] bg-cover bg-no-repeat w-1/2 h-full flex items-end justify-center">
        <h1 className="text-6xl italic text-white font-light mb-[20%]">
          <span className="font-extrabold text-7xl text-white">FAST</span>
          SHOP
        </h1>
      </div>
      <div className="w-1/2 h-full flex items-center justify-center">
        <FormRegister />
      </div>
    </div>
  );
}
