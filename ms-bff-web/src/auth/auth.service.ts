import { Injectable } from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import { LoginRequestDto } from './dto/login-request.dto';
import { LoginResponseDto } from './dto/login-response.dto';
import { firstValueFrom } from 'rxjs';
import { HttpService } from '@nestjs/axios';
import { RegisterRequestDto } from './dto/register-request.dto';

@Injectable()
export class AuthService {
  public constructor(
    private readonly http: HttpService,
    private readonly jwt: JwtService,
  ) {}

  public async register(
    body: RegisterRequestDto,
  ): Promise<{ message: string }> {
    try {
      await firstValueFrom(
        this.http.post('http://localhost:9000/auth/register', body),
      );

      return {
        message: 'User registered successfully',
      };
    } catch (error) {
      throw error;
    }
  }

  public async login(credentials: LoginRequestDto): Promise<LoginResponseDto> {
    const res = await firstValueFrom(
      this.http.post<LoginResponseDto>(
        'http://localhost:9000/auth/login',
        credentials,
      ),
    );

    return res.data;
  }
}
