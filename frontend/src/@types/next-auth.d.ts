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
    accountId: number;
    role: string;
    token: string;
  }
}
