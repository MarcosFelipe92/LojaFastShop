import jwt from "jsonwebtoken";
import NextAuth from "next-auth";
import Credentials from "next-auth/providers/credentials";

export const { handlers, signIn, signOut, auth } = NextAuth({
  providers: [
    Credentials({
      credentials: {
        email: { label: "Email", type: "email" },
        password: { label: "Password", type: "password" },
      },
      authorize: async (credentials) => {
        try {
          const response = await fetch("http://localhost:8080/token/login", {
            method: "POST",
            headers: { "Content-type": "application/json" },
            body: JSON.stringify({
              email: credentials?.email,
              password: credentials?.password,
            }),
          });

          if (!response.ok) {
            return null;
          }

          const data = await response.json();

          const decodedToken = jwt.decode(data.token) as jwt.JwtPayload;

          if (!decodedToken.sub) {
            return null;
          }

          return {
            id: decodedToken.sub,
            token: data.token,
            role: decodedToken.scope || "BASIC",
          };
        } catch (error) {
          console.error("Authorization error:", error);
          return null;
        }
      },
    }),
  ],
  callbacks: {
    async jwt({ token, user }) {
      if (user) {
        token.sub = user.id;
        token.accessToken = user.token;
        token.role = user.role;
      }

      return token;
    },
    async session({ token, session }) {
      if (session.user && token.sub) {
        session.user.id = token.sub;
        session.user.role = token.role;
        session.user.token = token.accessToken;
      }

      return session;
    },
  },
  pages: {
    signIn: "/auth/login",
    error: "/auth/error",
  },
  session: { strategy: "jwt", maxAge: 4 * 60 * 60 },
});
