"use client";

import { findUser, User } from "@/actions/user/userService";
import { Container } from "@/components/global/container";
import { Header } from "@/components/global/header";
import { ProfileHeader } from "@/components/profile/ProfileHeader";
import { ProfileLinks } from "@/components/profile/ProfileLinks";
import { useEffect, useState } from "react";

export default function PageProfile() {
  const [user, setUser] = useState<User>();

  useEffect(() => {
    const fetchUser = async () => {
      const user = await findUser();
      setUser(user);
    };
    fetchUser();
  }, []);

  return (
    <Container>
      <Header />
      <div className="w-full mt-5 bg-gray-50 p-5 rounded-lg space-y-4">
        <ProfileHeader name={user?.name} email={user?.email} />
        <ProfileLinks />
      </div>
    </Container>
  );
}
