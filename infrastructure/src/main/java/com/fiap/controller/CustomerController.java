package com.fiap.controller;

import com.fiap.core.exception.DocumentNumberException;
import com.fiap.core.exception.EmailException;
import com.fiap.core.exception.InternalServerErrorException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.dto.request.CreateCustomerRequest;
import com.fiap.dto.request.UpdateCustomerRequest;
import com.fiap.dto.response.CustomerResponse;
import com.fiap.mapper.CustomerMapper;
import com.fiap.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Cria um novo cliente",
            description = "Endpoint para criar um novo cliente.")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso.") })
    @PostMapping("/create")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CreateCustomerRequest request) throws DocumentNumberException, EmailException, InternalServerErrorException {
        var customer = createCustomerUseCase.execute(customerMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(customerMapper.toResponse(customer));
    }

    @Operation(
            summary = "Atualiza um cliente existente",
            description = "Endpoint para atualizar um cliente pelo ID.")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso.") })
    @PutMapping("/update")
    public ResponseEntity<CustomerResponse> updateCustomer(@RequestBody UpdateCustomerRequest request) throws DocumentNumberException, EmailException, NotFoundException, InternalServerErrorException {
        var customer = updateCustomerUseCase.execute(customerMapper.toDomainUpdate(request));
        return ResponseEntity.ok().body(customerMapper.toResponse(customer));
    }

    @Operation(
            summary = "Busca um cliente pelo ID",
            description = "Endpoint para buscar um cliente pelo ID.")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso.") })
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findCustomerById(@PathVariable UUID id) throws DocumentNumberException, NotFoundException {
        var customer = findCustomerByIdUseCase.execute(id);
        return ResponseEntity.ok().body(customerMapper.toResponse(customer));
    }

    @Operation(
            summary = "Busca um cliente pelo documento.",
            description = "Endpoint para buscar um cliente pelo documento.")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso.") })
    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<CustomerResponse> findCustomerById(@PathVariable String documentNumber) throws DocumentNumberException, NotFoundException {
        var customer = findCustomerByDocumentUseCase.execute(documentNumber);
        return ResponseEntity.ok().body(customerMapper.toResponse(customer));
    }

    @Operation(
            summary = "Deleta um cliente pelo ID",
            description = "Endpoint para deletar um cliente pelo ID.")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso.") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) throws DocumentNumberException, NotFoundException {
        deleteCustomerUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
