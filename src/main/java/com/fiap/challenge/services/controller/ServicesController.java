package com.fiap.challenge.services.controller;

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

import com.fiap.challenge.services.dto.InputServiceDTO;
import com.fiap.challenge.services.dto.ServiceResponseDTO;
import com.fiap.challenge.services.useCases.create.CreateServiceUseCase;
import com.fiap.challenge.services.useCases.delete.DeleteServiceUseCase;
import com.fiap.challenge.services.useCases.find.FindServiceByIdUseCase;
import com.fiap.challenge.services.useCases.find.FindServicesByIdsUseCase;
import com.fiap.challenge.services.useCases.update.UpdateServiceUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServicesController {

	private final CreateServiceUseCase createServiceUseCase;
	private final UpdateServiceUseCase updateServiceUseCase;
	private final FindServiceByIdUseCase findServiceByIdUseCase;
	private final FindServicesByIdsUseCase findServicesByIdsUseCase;
	private final DeleteServiceUseCase deleteServiceUseCase;

    @PostMapping
    public ResponseEntity<ServiceResponseDTO> create(@RequestBody InputServiceDTO createServiceDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createServiceUseCase.execute(createServiceDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponseDTO> update(@PathVariable UUID id, @RequestBody InputServiceDTO updateServiceDTO) {
        return ResponseEntity.ok(updateServiceUseCase.execute(id, updateServiceDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(findServiceByIdUseCase.execute(id));
    }

    @GetMapping
    public ResponseEntity<List<ServiceResponseDTO>> findByIds(@RequestParam("ids") List<UUID> ids) {
        return ResponseEntity.ok(findServicesByIdsUseCase.execute(ids));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteServiceUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

}
