package com.fiap.challenge.parts.controller;

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

import com.fiap.challenge.parts.dto.CreatePartRequestDTO;
import com.fiap.challenge.parts.dto.PartResponseDTO;
import com.fiap.challenge.parts.dto.UpdatePartRequestDTO;
import com.fiap.challenge.parts.useCases.create.CreatePartUseCase;
import com.fiap.challenge.parts.useCases.delete.DeletePartUseCase;
import com.fiap.challenge.parts.useCases.find.FindPartByIdUseCase;
import com.fiap.challenge.parts.useCases.find.FindPartsByIdsUseCase;
import com.fiap.challenge.parts.useCases.update.UpdatePartUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/parts")
public class PartsController {

	private final CreatePartUseCase createPartUseCase;
	private final UpdatePartUseCase updatePartUseCase;
	private final FindPartByIdUseCase findPartByIdUseCase;
	private final FindPartsByIdsUseCase findPartsByIdsUseCase;
	private final DeletePartUseCase deletePartUseCase;

	@PostMapping
	public ResponseEntity<PartResponseDTO> createPart(@RequestBody CreatePartRequestDTO cratePart) {
		return ResponseEntity.status(HttpStatus.CREATED).body(createPartUseCase.execute(cratePart));
	}

	@PutMapping("/{id}")
    public ResponseEntity<PartResponseDTO> updatePart(@PathVariable UUID id,@RequestBody UpdatePartRequestDTO requestDTO) {
        PartResponseDTO updatedPart = updatePartUseCase.execute(id, requestDTO);
        return ResponseEntity.ok(updatedPart);
    }

	@GetMapping("/{id}")
    public ResponseEntity<PartResponseDTO> findPartById(@PathVariable UUID id) {
        PartResponseDTO part = findPartByIdUseCase.execute(id);
        return ResponseEntity.ok(part);
    }

    @GetMapping
    public ResponseEntity<List<PartResponseDTO>> findPartsByIds(@RequestParam List<UUID> ids) {
        List<PartResponseDTO> parts = findPartsByIdsUseCase.execute(ids);
        return ResponseEntity.ok(parts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deletePartUseCase.execute(id);
        return ResponseEntity.ok().build();
    }
}
