package com.fiap.challenge.vehicles.controller;

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
public class VehicleController {

    private final CreateVehicleUseCase createVehicleUseCase;
    private final UpdateVehicleUseCase updateVehicleUseCase;
    private final FindVehicleByIdUseCase findVehicleByIdUseCase;
    private final FindVehiclesByIdsUseCase findVehiclesByIdsUseCase;
    private final FindVehicleByPlateUseCase findVehicleByPlateUseCase;
    private final FindVehiclesByCustomerDocumentUseCase findVehiclesByCustomerDocumentUseCase;
    private final DeleteVehicleUseCase deleteVehicleUseCase;

    @PostMapping
    public ResponseEntity<VehicleResponseDTO> create(@Valid @RequestBody InputVehicleDTO createVehicleDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createVehicleUseCase.execute(createVehicleDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> update(@PathVariable("id") UUID vehicleId, @RequestBody InputVehicleDTO updateVehicleDTO) {
        return ResponseEntity.ok(updateVehicleUseCase.execute(vehicleId, updateVehicleDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(findVehicleByIdUseCase.execute(id));
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponseDTO>> findByIds(@RequestParam("ids") List<UUID> ids) {
        return ResponseEntity.ok(findVehiclesByIdsUseCase.execute(ids));
    }

    @GetMapping("/by-plate")
    public ResponseEntity<VehicleResponseDTO> findByPlate(@RequestParam("plate") String plate) {
        return ResponseEntity.ok(findVehicleByPlateUseCase.execute(plate));
    }
    
    @GetMapping("/by-customer-document")
    public ResponseEntity<List<VehicleResponseDTO>> findByCustomerDocument(@RequestParam("cpfCnpj") String cpfCnpj) {
        return ResponseEntity.ok(findVehiclesByCustomerDocumentUseCase.execute(cpfCnpj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteVehicleUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}