enum DocumentType {
  RUC = 'RUC',
  DNI = 'DNI',
}

export class CreateCustomerDto {
  public firstName: string;

  public lastName: string;

  public documentType: DocumentType;

  public documentNumber: string;
}
