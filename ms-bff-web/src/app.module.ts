import { Module } from '@nestjs/common';
import { CustomersModule } from './customers/customers.module';
import { AuthModule } from './auth/auth.module';
import { APP_GUARD } from '@nestjs/core';
import { JwtAuthGuard } from './auth/guards/jwt-auth.guard';
import { HttpModule } from '@nestjs/axios';
import { JwtModule } from '@nestjs/jwt';
import { AuthService } from './auth/auth.service';

@Module({
  imports: [
    HttpModule,
    JwtModule.register({
      global: true,
      secret: 'JWT_SECRET_KEY',
      signOptions: { expiresIn: '1d' },
    }),
    CustomersModule,
    AuthModule,
  ],
  providers: [
    AuthService,
    {
      provide: APP_GUARD,
      useClass: JwtAuthGuard,
    },
  ],
})
export class AppModule {}
