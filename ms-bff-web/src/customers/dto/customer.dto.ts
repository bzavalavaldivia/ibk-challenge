import { ProductDto } from './product.dto.';

export class CustomerDto {
  public customerId: string;

  public name: string;

  public documentType: string;

  public documentNumber: string;

  public products?: ProductDto[];
}
