import {
  Controller,
  Get,
  Post,
  Body,
  Param,
  Req,
  UseInterceptors,
} from '@nestjs/common';
import { CustomersService } from './customers.service';
import { CreateCustomerDto } from './dto/create-customer.dto';
import { CustomerDto } from './dto/customer.dto';
import { Request } from 'express';
import { LoggingInterceptor } from 'src/interceptors/logging.interceptor';

@UseInterceptors(LoggingInterceptor)
@Controller('api/v1/customers')
export class CustomersController {
  constructor(private readonly customersService: CustomersService) {}

  @Post()
  public async create(
    @Req() req: Request,
    @Body() createCustomerDto: CreateCustomerDto,
  ) {
    const userId = req.userId;
    return await this.customersService.createCustomer(
      createCustomerDto,
      userId,
    );
  }

  @Get()
  public async findAll(): Promise<CustomerDto[]> {
    return await this.customersService.findAllCustomers();
  }

  @Get(':id')
  public async findOne(@Param('id') id: string): Promise<CustomerDto> {
    return this.customersService.findCustomerById(id);
  }
}
