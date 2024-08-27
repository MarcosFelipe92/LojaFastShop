interface User {
  id?: number;
  name: string;
  email: string;
  password: string;
  phones?: Phone[];
  roles?: Role[];
  error?: string;
}

interface Phone {
  id?: number;
  number: string;
  type: string;
  userId?: number;
}

interface Role {
  name: string;
}

export const createUser = async (
  name: string,
  email: string,
  password: string,
  phones: Phone[]
): Promise<User | undefined> => {
  try {
    const response = await fetch("http://localhost:8080/users/register", {
      method: "POST",
      headers: { "Content-type": "application/json" },
      body: JSON.stringify({
        name,
        email,
        password,
        phones,
      }),
    });

    const user = await response.json();

    return user;
  } catch (error) {
    console.log(error);
  }
};
