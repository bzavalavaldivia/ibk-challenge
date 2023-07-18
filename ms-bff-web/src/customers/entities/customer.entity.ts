import { DocumentType } from '../enums/document-type.enum';
import { Product } from './product.entity';

export class Customer {
  public id: number;

  public uuid: string;

  public firstName: string;

  public lastName: string;

  public documentType: DocumentType;

  public documentNumber: string;

  public products?: Product[];
}
