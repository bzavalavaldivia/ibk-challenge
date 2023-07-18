import { Injectable } from '@nestjs/common';
import { CreateCustomerDto } from './dto/create-customer.dto';
import { UpdateCustomerDto } from './dto/update-customer.dto';
import { Customer } from './entities/customer.entity';
import { HttpService } from '@nestjs/axios';
import { firstValueFrom } from 'rxjs';
import { CustomerDto } from './dto/customer.dto';
import { CustomerMapper } from './mappers/customer.mapper';
import { Encrypter } from 'src/utils/encrypter.util';

@Injectable()
export class CustomersService {
  public constructor(private readonly http: HttpService) {}

  public async createCustomer(
    createCustomerDto: CreateCustomerDto,
    userId: string,
  ) {
    const customer: Customer = {
      uuid: userId,
      firstName: createCustomerDto.firstName,
      lastName: createCustomerDto.lastName,
      documentType: createCustomerDto.documentType,
      documentNumber: createCustomerDto.documentNumber,
    };

    const res = await firstValueFrom(
      this.http.post<Customer>(
        'http://localhost:8081/api/v1/customers',
        customer,
      ),
    );

    return await CustomerMapper.toDto(res.data);
  }

  public async findAllCustomers(): Promise<CustomerDto[]> {
    const res = await firstValueFrom(
      this.http.get<Customer[]>('http://localhost:8081/api/v1/customers'),
    );

    const customers = res.data.map(
      async (customer) => await CustomerMapper.toDto(customer),
    );

    return Promise.all(customers);
  }

  public async findCustomerById(id: string): Promise<CustomerDto> {
    const customerId = await Encrypter.decrypt(id);
    const res = await firstValueFrom(
      this.http.get<Customer>(
        `http://localhost:8081/api/v1/customers/${customerId}/products`,
      ),
    );
    const customer = await CustomerMapper.toDto(res.data);
    return customer;
  }

  update(id: number, updateCustomerDto: UpdateCustomerDto) {
    return `This action updates a #${id} customer`;
  }

  remove(id: number) {
    return `This action removes a #${id} customer`;
  }
}
