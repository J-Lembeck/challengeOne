package com.fiap.challenge.services.controller;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Tag(name = "Serviços", description = "Controlador para Serviços.")
public class ServicesController {

	private final CreateServiceUseCase createServiceUseCase;
	private final UpdateServiceUseCase updateServiceUseCase;
	private final FindServiceByIdUseCase findServiceByIdUseCase;
	private final FindServicesByIdsUseCase findServicesByIdsUseCase;
	private final DeleteServiceUseCase deleteServiceUseCase;

    @Operation(
        summary = "Cria um novo serviço",
        description = "Endpoint para criar um novo serviço.")
    @ApiResponse(responseCode = "201", description = "Serviço criado com sucesso.")
    @PostMapping
    public ResponseEntity<ServiceResponseDTO> create(@RequestBody InputServiceDTO createServiceDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createServiceUseCase.execute(createServiceDTO));
    }

    @Operation(
        summary = "Atualiza um serviço existente",
        description = "Endpoint para atualizar um serviço pelo ID.")
    @ApiResponse(responseCode = "200", description = "Serviço atualizado com sucesso.")
    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponseDTO> update(@PathVariable UUID id, @RequestBody InputServiceDTO updateServiceDTO) {
        return ResponseEntity.ok(updateServiceUseCase.execute(id, updateServiceDTO));
    }

    @Operation(
        summary = "Busca um serviço pelo ID",
        description = "Endpoint para buscar um serviço pelo ID.")
    @ApiResponse(responseCode = "200", description = "Serviço encontrado com sucesso.")
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(findServiceByIdUseCase.execute(id));
    }

    @Operation(
        summary = "Busca serviços por uma lista de IDs",
        description = "Endpoint para buscar serviços por uma lista de IDs.")
    @ApiResponse(responseCode = "200", description = "Serviços encontrados com sucesso.")
    @GetMapping
    public ResponseEntity<List<ServiceResponseDTO>> findByIds(@RequestParam("ids") List<UUID> ids) {
        return ResponseEntity.ok(findServicesByIdsUseCase.execute(ids));
    }

    @Operation(
        summary = "Deleta um serviço pelo ID",
        description = "Endpoint para deletar um serviço pelo ID.")
    @ApiResponse(responseCode = "204", description = "Serviço deletado com sucesso.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteServiceUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

}
