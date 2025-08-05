package com.fiap.challenge.customers.controller;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.challenge.customers.dto.CreateCustomerRequestDTO;
import com.fiap.challenge.customers.dto.CustomerResponseDTO;
import com.fiap.challenge.customers.useCases.create.CreateCustomerUseCase;
import com.fiap.challenge.customers.useCases.delete.DeleteCustomerUseCase;
import com.fiap.challenge.customers.useCases.find.FindCustomerByIdUseCase;
import com.fiap.challenge.customers.useCases.find.FindCustomersByIdsUseCase;
import com.fiap.challenge.customers.useCases.update.UpdateCustomerUseCase;
import com.fiap.challenge.users.dto.UpdateCustomerRequestDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Controlador para Clientes.")
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final FindCustomerByIdUseCase findCustomerByIdUseCase;
    private final FindCustomersByIdsUseCase findCustomersByIdsUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;

    @Operation(
        summary = "Cria um novo cliente",
        description = "Endpoint para criar um novo cliente.")
    @ApiResponses(
        value = { @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso.") })
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> create(@RequestBody CreateCustomerRequestDTO requestDTO) {
    	return ResponseEntity.status(HttpStatus.CREATED).body(createCustomerUseCase.execute(requestDTO));
    }

    @Operation(
        summary = "Atualiza um cliente existente",
        description = "Endpoint para atualizar um cliente pelo ID.")
    @ApiResponses(
        value = { @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso.") })
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable UUID id, @RequestBody UpdateCustomerRequestDTO requestDTO) {
        CustomerResponseDTO updatedCustomer = updateCustomerUseCase.execute(id, requestDTO);
        return ResponseEntity.ok(updatedCustomer);
    }

    @Operation(
        summary = "Busca um cliente pelo ID",
        description = "Endpoint para buscar um cliente pelo ID.")
    @ApiResponses(
        value = { @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso.") })
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable UUID id) {
        CustomerResponseDTO customer = findCustomerByIdUseCase.execute(id);
        return ResponseEntity.ok(customer);
    }

    @Operation(
        summary = "Busca clientes por IDs",
        description = "Endpoint para buscar clientes por uma lista de IDs.")
    @ApiResponses(
        value = { @ApiResponse(responseCode = "200", description = "Clientes encontrados com sucesso.") })
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> findById(@RequestParam List<UUID> ids) {
        List<CustomerResponseDTO> customer = findCustomersByIdsUseCase.execute(ids);
        return ResponseEntity.ok(customer);
    }

    @Operation(
        summary = "Deleta um cliente pelo ID",
        description = "Endpoint para deletar um cliente pelo ID.")
    @ApiResponses(
        value = { @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso.") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteCustomerUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}