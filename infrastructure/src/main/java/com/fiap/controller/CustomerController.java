package com.fiap.controller;

import com.fiap.core.exception.DocumentNumberException;
import com.fiap.core.exception.EmailException;
import com.fiap.core.exception.InternalServerErrorException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.dto.request.CreateCustomerRequest;
import com.fiap.dto.request.UpdateCustomerRequest;
import com.fiap.dto.response.CustomerResponse;
import com.fiap.mapper.CustomerMapper;
import com.fiap.usecase.CreateCustomerUseCase;
import com.fiap.usecase.DeleteCustomerUseCase;
import com.fiap.usecase.FindCustomerByIdUseCase;
import com.fiap.usecase.UpdateCustomerUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("v1/customer")
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final FindCustomerByIdUseCase findCustomerByIdUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    private final CustomerMapper customerMapper;

    public CustomerController(CreateCustomerUseCase createCustomerUseCase, FindCustomerByIdUseCase findCustomerByIdUseCase, UpdateCustomerUseCase updateCustomerUseCase, DeleteCustomerUseCase deleteCustomerUseCase, CustomerMapper customerMapper) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.findCustomerByIdUseCase = findCustomerByIdUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
        this.customerMapper = customerMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CreateCustomerRequest request) throws DocumentNumberException, EmailException, InternalServerErrorException {
        var customer = createCustomerUseCase.execute(customerMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(customerMapper.toResponse(customer));
    }

    @PutMapping("/update")
    public ResponseEntity<CustomerResponse> updateCustomer(@RequestBody UpdateCustomerRequest request) throws DocumentNumberException, EmailException, NotFoundException, InternalServerErrorException {
        var customer = updateCustomerUseCase.execute(customerMapper.toDomainUpdate(request));
        return ResponseEntity.ok().body(customerMapper.toResponse(customer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findCustomerById(@PathVariable UUID id) throws DocumentNumberException, NotFoundException {
        var customer = findCustomerByIdUseCase.execute(id);
        return ResponseEntity.ok().body(customerMapper.toResponse(customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) throws DocumentNumberException, NotFoundException {
        deleteCustomerUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
