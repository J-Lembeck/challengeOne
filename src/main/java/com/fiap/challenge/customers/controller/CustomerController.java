package com.fiap.challenge.customers.controller;

import java.util.List;
import java.util.UUID;

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
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final FindCustomerByIdUseCase findCustomerByIdUseCase;
    private final FindCustomersByIdsUseCase findCustomersByIdsUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;

    @PostMapping()
    public ResponseEntity<CustomerResponseDTO> create(@RequestBody CreateCustomerRequestDTO requestDTO) {
    	return ResponseEntity.status(HttpStatus.CREATED).body(createCustomerUseCase.execute(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable UUID id, @RequestBody UpdateCustomerRequestDTO requestDTO) {
        CustomerResponseDTO updatedCustomer = updateCustomerUseCase.execute(id, requestDTO);
        return ResponseEntity.ok(updatedCustomer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable UUID id) {
        CustomerResponseDTO customer = findCustomerByIdUseCase.execute(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping()
    public ResponseEntity<List<CustomerResponseDTO>> findById(@RequestParam List<UUID> ids) {
        List<CustomerResponseDTO> customer = findCustomersByIdsUseCase.execute(ids);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteCustomerUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}