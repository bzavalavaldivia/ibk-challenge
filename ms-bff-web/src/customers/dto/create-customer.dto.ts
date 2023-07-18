import { ApiProperty } from '@nestjs/swagger';

enum DocumentType {
  RUC = 'RUC',
  DNI = 'DNI',
}

export class CreateCustomerDto {
  @ApiProperty()
  public firstName: string;

  @ApiProperty()
  public lastName: string;

  @ApiProperty({ enum: DocumentType })
  public documentType: DocumentType;

  @ApiProperty()
  public documentNumber: string;
}
