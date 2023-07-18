import { ApiProperty } from '@nestjs/swagger';

enum Role {
  ADMIN = 'ROLE_ADMIN',
  USER = 'ROLE_USER',
}

export class RegisterRequestDto {
  @ApiProperty()
  public username: string;

  @ApiProperty()
  public password: string;

  @ApiProperty({ enum: Role })
  public role: Role;
}
