import { Injectable } from '@nestjs/common';
import { Customer } from '../entities/customer.entity';
import { CustomerDto } from '../dto/customer.dto';
import { ProductMapper } from './product.mapper';
import { Encrypter } from '../../utils/encrypter.util';

@Injectable()
export class CustomerMapper {
  public static async toDto(entity: Customer): Promise<CustomerDto> {
    return {
      customerId: await Encrypter.encrypt(entity.uuid),
      name: `${entity.firstName} ${entity.lastName}`,
      documentType: entity.documentType,
      documentNumber: entity.documentNumber,
      products: entity.products
        ? await Promise.all(
            entity.products.map(
              async (product) => await ProductMapper.toDto(product),
            ),
          )
        : undefined,
    };
  }
}
