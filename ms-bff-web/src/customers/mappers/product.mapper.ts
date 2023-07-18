import { Injectable } from '@nestjs/common';
import { Product } from '../entities/product.entity';
import { ProductDto } from '../dto/product.dto.';

@Injectable()
export class ProductMapper {
  public static async toDto(entity: Product): Promise<ProductDto> {
    return {
      type: entity.type,
      name: entity.name,
      balance: entity.balance,
    };
  }
}
