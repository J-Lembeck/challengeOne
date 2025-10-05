package com.fiap.controller;

import com.fiap.core.domain.customer.Customer;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.core.exception.EmailException;
import com.fiap.core.exception.InternalServerErrorException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.dto.customer.CreateCustomerRequest;
import com.fiap.dto.customer.CustomerResponse;
import com.fiap.dto.customer.UpdateCustomerRequest;
import com.fiap.mapper.customer.CustomerMapper;
import com.fiap.usecase.customer.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("v1/customer")
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final FindCustomerByIdUseCase findCustomerByIdUseCase;
    private final FindCustomerByDocumentUseCase findCustomerByDocumentUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    private final CustomerMapper customerMapper;

    public CustomerController(CreateCustomerUseCase createCustomerUseCase, FindCustomerByIdUseCase findCustomerByIdUseCase, FindCustomerByDocumentUseCase findCustomerByDocumentUseCase, UpdateCustomerUseCase updateCustomerUseCase, DeleteCustomerUseCase deleteCustomerUseCase, CustomerMapper customerMapper) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.findCustomerByIdUseCase = findCustomerByIdUseCase;
        this.findCustomerByDocumentUseCase = findCustomerByDocumentUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
        this.customerMapper = customerMapper;
    }

    @Operation(summary = "Cria um novo cliente")
    @PostMapping("/create")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CreateCustomerRequest request) throws DocumentNumberException, EmailException, InternalServerErrorException {
        Customer customer = createCustomerUseCase.execute(customerMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(customerMapper.toResponse(customer));
    }

    @Operation(summary = "Atualiza um cliente existente")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable UUID id, @RequestBody UpdateCustomerRequest request) throws DocumentNumberException, EmailException, NotFoundException, InternalServerErrorException {
        Customer customer = updateCustomerUseCase.execute(customerMapper.toDomain(id, request));
        return ResponseEntity.ok().body(customerMapper.toResponse(customer));
    }

    @Operation(summary = "Busca um cliente pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findCustomerById(@PathVariable UUID id) throws DocumentNumberException, NotFoundException {
        Customer customer = findCustomerByIdUseCase.execute(id);
        return ResponseEntity.ok().body(customerMapper.toResponse(customer));
    }

    @Operation(summary = "Busca um cliente pelo documento")
    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<CustomerResponse> findCustomerByDocument(@PathVariable String documentNumber) throws DocumentNumberException, NotFoundException {
        Customer customer = findCustomerByDocumentUseCase.execute(documentNumber);
        return ResponseEntity.ok().body(customerMapper.toResponse(customer));
    }

    @Operation(summary = "Deleta um cliente pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) throws DocumentNumberException, NotFoundException {
        deleteCustomerUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}