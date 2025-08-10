package com.fiap.challenge.vehicles.controller;

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

import com.fiap.challenge.vehicles.dto.InputVehicleDTO;
import com.fiap.challenge.vehicles.dto.VehicleResponseDTO;
import com.fiap.challenge.vehicles.useCases.create.CreateVehicleUseCase;
import com.fiap.challenge.vehicles.useCases.delete.DeleteVehicleUseCase;
import com.fiap.challenge.vehicles.useCases.find.FindVehicleByIdUseCase;
import com.fiap.challenge.vehicles.useCases.find.FindVehiclesByIdsUseCase;
import com.fiap.challenge.vehicles.useCases.find.byClientCpf.FindVehiclesByCustomerDocumentUseCase;
import com.fiap.challenge.vehicles.useCases.find.byPlate.FindVehicleByPlateUseCase;
import com.fiap.challenge.vehicles.useCases.update.UpdateVehicleUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
@Tag(name = "Veículos", description = "Controlador para Veículos.")
public class VehicleController {

    private final CreateVehicleUseCase createVehicleUseCase;
    private final UpdateVehicleUseCase updateVehicleUseCase;
    private final FindVehicleByIdUseCase findVehicleByIdUseCase;
    private final FindVehiclesByIdsUseCase findVehiclesByIdsUseCase;
    private final FindVehicleByPlateUseCase findVehicleByPlateUseCase;
    private final FindVehiclesByCustomerDocumentUseCase findVehiclesByCustomerDocumentUseCase;
    private final DeleteVehicleUseCase deleteVehicleUseCase;


    @Operation(
        summary = "Cria um novo veículo",
        description = "Endpoint para criar um novo veículo.")
    @ApiResponse(
        responseCode = "201",
        description = "Veículo criado com sucesso.")
    @PostMapping
    public ResponseEntity<VehicleResponseDTO> create(@Valid @RequestBody InputVehicleDTO createVehicleDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createVehicleUseCase.execute(createVehicleDTO));
    }

    @Operation(
        summary = "Atualiza um veículo existente",
        description = "Endpoint para atualizar um veículo pelo ID.")
    @ApiResponse(
        responseCode = "200",
        description = "Veículo atualizado com sucesso.")
    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> update(@PathVariable("id") UUID vehicleId, @RequestBody @Valid InputVehicleDTO updateVehicleDTO) {
        return ResponseEntity.ok(updateVehicleUseCase.execute(vehicleId, updateVehicleDTO));
    }

    @Operation(
        summary = "Busca um veículo pelo ID",
        description = "Endpoint para buscar um veículo pelo ID.")
    @ApiResponse(
        responseCode = "200",
        description = "Veículo encontrado com sucesso.")
    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(findVehicleByIdUseCase.execute(id));
    }

    @Operation(
        summary = "Busca veículos por IDs",
        description = "Endpoint para buscar veículos por uma lista de IDs.")
    @ApiResponse(
        responseCode = "200",
        description = "Veículos encontrados com sucesso.")
    @GetMapping
    public ResponseEntity<List<VehicleResponseDTO>> findByIds(@RequestParam("ids") List<UUID> ids) {
        return ResponseEntity.ok(findVehiclesByIdsUseCase.execute(ids));
    }

    @Operation(
        summary = "Busca um veículo pelo número da placa",
        description = "Endpoint para buscar um veículo pelo número da placa.")
    @ApiResponse(
        responseCode = "200",
        description = "Veículo encontrado com sucesso.")
    @GetMapping("/by-plate")
    public ResponseEntity<VehicleResponseDTO> findByPlate(@RequestParam("plate") String plate) {
        return ResponseEntity.ok(findVehicleByPlateUseCase.execute(plate));
    }

    @Operation(
        summary = "Busca veículos por documento do cliente",
        description = "Endpoint para buscar veículos associados a um cliente pelo CPF/CNPJ.")
    @ApiResponse(
        responseCode = "200",
        description = "Veículos encontrados com sucesso.")
    @GetMapping("/by-customer-document")
    public ResponseEntity<List<VehicleResponseDTO>> findByCustomerDocument(@RequestParam("cpfCnpj") String cpfCnpj) {
        return ResponseEntity.ok(findVehiclesByCustomerDocumentUseCase.execute(cpfCnpj));
    }

    @Operation(
        summary = "Deleta um veículo pelo ID",
        description = "Endpoint para deletar um veículo pelo ID.")
    @ApiResponse(
        responseCode = "204",
        description = "Veículo deletado com sucesso.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteVehicleUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}