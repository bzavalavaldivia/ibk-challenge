import { ProductType } from '../enums/product-type.enum';

export class Product {
  public id: number;

  public customerId: string;

  public type: ProductType;

  public name: string;

  public balance: number;
}
