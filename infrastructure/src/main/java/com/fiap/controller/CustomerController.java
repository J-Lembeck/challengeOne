package com.fiap.controller;

import com.fiap.core.exception.DocumentNumberException;
import com.fiap.core.exception.EmailException;
import com.fiap.core.exception.InternalServerErrorException;
import com.fiap.dto.request.CreateCustomerRequest;
import com.fiap.dto.response.BaseResponse;
import com.fiap.mapper.CustomerMapper;
import com.fiap.usecase.CreateCustomerUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/customer")
public class CustomerController {

    final CreateCustomerUseCase createCustomerUseCase;
    final CustomerMapper customerMapper;

    public CustomerController(CreateCustomerUseCase createCustomerUseCase, CustomerMapper customerMapper) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.customerMapper = customerMapper;
    }

    @PostMapping("/createCustomer")
    public BaseResponse<String> createUser(@RequestBody CreateCustomerRequest request) throws DocumentNumberException, EmailException, InternalServerErrorException {
        createCustomerUseCase.execute(customerMapper.toDomain(request));
        return BaseResponse.<String>builder().success(true).message("Cliente criado com sucesso").build();
    }
}
