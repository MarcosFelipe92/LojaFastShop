import FormLogin from "@/components/auth/form-login";
import { Container } from "@/components/container";
import { revalidatePath } from "next/cache";

export default function PageLogin() {
  return (
    <Container>
      <FormLogin />
    </Container>
  );
}
