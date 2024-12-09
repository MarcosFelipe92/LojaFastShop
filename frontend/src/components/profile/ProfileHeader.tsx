type ProfileHeaderProps = {
  name: string | undefined;
  email: string | undefined;
};

export function ProfileHeader({ name, email }: ProfileHeaderProps) {
  return (
    <div>
      <h1 className="text-2xl font-bold text-lime-500">{name}</h1>
      <p>{email}</p>
    </div>
  );
}
