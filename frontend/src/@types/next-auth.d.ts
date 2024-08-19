export type ExtendedUser = DefaultSession["user"] & {
  role: string;
};

declare module "next-auth" {
  interface Session {
    user: ExtendedUser;
  }
}

declare module "next-auth" {
  interface User {
    role: string;
    token: string;
  }
}
