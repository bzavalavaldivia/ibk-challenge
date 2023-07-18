enum Role {
  ADMIN = 'ROLE_ADMIN',
  USER = 'ROLE_USER',
}

export class RegisterRequestDto {
  public username: string;

  public password: string;

  public role: Role;
}
