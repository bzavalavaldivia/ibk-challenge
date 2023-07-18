import {
  Controller,
  Get,
  Post,
  Body,
  Patch,
  Param,
  Delete,
  Req,
} from '@nestjs/common';
import { CustomersService } from './customers.service';
import { CreateCustomerDto } from './dto/create-customer.dto';
import { UpdateCustomerDto } from './dto/update-customer.dto';
import { CustomerDto } from './dto/customer.dto';
import { Request } from 'express';

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

  @Patch(':id')
  update(
    @Param('id') id: string,
    @Body() updateCustomerDto: UpdateCustomerDto,
  ) {
    return this.customersService.update(+id, updateCustomerDto);
  }

  @Delete(':id')
  remove(@Param('id') id: string) {
    return this.customersService.remove(+id);
  }
}
