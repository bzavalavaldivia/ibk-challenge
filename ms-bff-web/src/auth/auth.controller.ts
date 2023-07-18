import { Body, Controller, Post } from '@nestjs/common';
import { AuthService } from './auth.service';
import { LoginRequestDto } from './dto/login-request.dto';
import { LoginResponseDto } from './dto/login-response.dto';
import { RegisterRequestDto } from './dto/register-request.dto';

@Controller('auth')
export class AuthController {
  public constructor(private readonly authService: AuthService) {}

  @Post('register')
  public async register(
    @Body() body: RegisterRequestDto,
  ): Promise<{ message: string }> {
    return await this.authService.register(body);
  }

  @Post('login')
  public async login(
    @Body() credentials: LoginRequestDto,
  ): Promise<LoginResponseDto> {
    return await this.authService.login(credentials);
  }
}
