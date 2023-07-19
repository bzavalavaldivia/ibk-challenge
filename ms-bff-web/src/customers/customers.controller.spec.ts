import { Test, TestingModule } from '@nestjs/testing';
import { CustomersController } from './customers.controller';
import { CustomersService } from './customers.service';
import { CreateCustomerDto } from './dto/create-customer.dto';
import { DocumentType } from './enums/document-type.enum';
import * as mocks from 'node-mocks-http';

describe('CustomersController', () => {
  let controller: CustomersController;

  const mockCustomersService = {
    createCustomer: jest.fn((createCustomerDto: CreateCustomerDto) => ({
      customerId: expect.any(String),
      name: `${createCustomerDto.firstName} ${createCustomerDto.lastName}`,
      documentType: createCustomerDto.documentType,
      documentNumber: createCustomerDto.documentNumber,
    })),
    findAllCustomers: jest.fn(() => [
      {
        customerId:
          '149859fff67aaacd68e9b6cc3a98d517$c0afb0bdf4a1d32c42479508bded33ff008d1f33f36e3bb3c879e18c759aa3f9$a525d81da42d85b0841f63ff0cb142ee$3dc04a339999ac856b8b2c5b8912decaa7b44295664848a57f11329f78db7b2fa3e58671',
        name: 'Bryan Zavala',
        documentType: 'DNI',
        documentNumber: '12345678',
      },
    ]),
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    findCustomerById: jest.fn((id: string) => ({
      customerId:
        'f30c5654d701b53b0fd1dcd0dea6bfe2$945c294e99a7c392ea5213ad96a1630da0db1172c7c19f7a763ec034182637d9$85e588f6ad7be5163b8651af80e680e0$3744165833b90218239480989f6ef1eebe4626ef8abde670485e57354730d4fa5eb903f7',
      name: 'Bryan Zavala',
      documentType: 'DNI',
      documentNumber: '12345678',
      products: [
        {
          type: 'CUENTA_AHORROS',
          name: 'Cuenta de ahorros',
          balance: 100,
        },
        {
          type: 'TARJETA_CREDITO_AMEX',
          name: 'Tarjeta de credito AMEX',
          balance: 3000,
        },
      ],
    })),
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [CustomersController],
      providers: [CustomersService],
    })
      .overrideProvider(CustomersService)
      .useValue(mockCustomersService)
      .compile();

    controller = module.get<CustomersController>(CustomersController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });

  it('should create a customer', async () => {
    const req = mocks.createRequest();
    const customerRequest: CreateCustomerDto = {
      firstName: 'Bryan',
      lastName: 'Zavala',
      documentType: DocumentType.DNI,
      documentNumber: '12345678',
    };

    const storedCustomer = await controller.create(req, customerRequest);

    expect(storedCustomer).toEqual({
      customerId: expect.any(String),
      name: `${customerRequest.firstName} ${customerRequest.lastName}`,
      documentType: customerRequest.documentType,
      documentNumber: customerRequest.documentNumber,
    });
  });

  it('should get all customers', async () => {
    const customers = await controller.findAll();

    expect(customers).toEqual([
      {
        customerId:
          '149859fff67aaacd68e9b6cc3a98d517$c0afb0bdf4a1d32c42479508bded33ff008d1f33f36e3bb3c879e18c759aa3f9$a525d81da42d85b0841f63ff0cb142ee$3dc04a339999ac856b8b2c5b8912decaa7b44295664848a57f11329f78db7b2fa3e58671',
        name: 'Bryan Zavala',
        documentType: 'DNI',
        documentNumber: '12345678',
      },
    ]);
  });

  it('should get a customer by id', async () => {
    const customer = await controller.findOne(
      '149859fff67aaacd68e9b6cc3a98d517$c0afb0bdf4a1d32c42479508bded33ff008d1f33f36e3bb3c879e18c759aa3f9$a525d81da42d85b0841f63ff0cb142ee$3dc04a339999ac856b8b2c5b8912decaa7b44295664848a57f11329f78db7b2fa3e58671',
    );

    expect(customer).toEqual({
      customerId:
        'f30c5654d701b53b0fd1dcd0dea6bfe2$945c294e99a7c392ea5213ad96a1630da0db1172c7c19f7a763ec034182637d9$85e588f6ad7be5163b8651af80e680e0$3744165833b90218239480989f6ef1eebe4626ef8abde670485e57354730d4fa5eb903f7',
      name: 'Bryan Zavala',
      documentType: 'DNI',
      documentNumber: '12345678',
      products: [
        {
          type: 'CUENTA_AHORROS',
          name: 'Cuenta de ahorros',
          balance: 100,
        },
        {
          type: 'TARJETA_CREDITO_AMEX',
          name: 'Tarjeta de credito AMEX',
          balance: 3000,
        },
      ],
    });
  });
});
