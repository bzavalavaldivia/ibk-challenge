import {
  CallHandler,
  ExecutionContext,
  Logger,
  NestInterceptor,
} from '@nestjs/common';
import { Observable, tap } from 'rxjs';

export class LoggingInterceptor implements NestInterceptor {
  intercept(
    context: ExecutionContext,
    next: CallHandler<any>,
  ): Observable<any> | Promise<Observable<any>> {
    const response = context.switchToHttp().getResponse();

    const input = `Input: ${context.getClass().name}: ${
      context.getHandler().name
    }(${JSON.stringify(context.getArgs()[0].body)})`;

    Logger.log(input);

    const now = Date.now();

    return next.handle().pipe(
      tap((data) => {
        Logger.log(`Output: ${JSON.stringify(data)} - ${Date.now() - now}ms`);
      }),
    );
  }
}
