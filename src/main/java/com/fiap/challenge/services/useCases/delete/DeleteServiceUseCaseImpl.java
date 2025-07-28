package com.fiap.challenge.services.useCases.delete;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.challenge.services.repository.ServiceRepository;
import com.fiap.challenge.shared.exception.serice.ServiceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteServiceUseCaseImpl implements DeleteServiceUseCase {

    private final ServiceRepository serviceRepository;

    @Override
    public void execute(UUID id) {
        if (!this.serviceRepository.existsById(id)) {
            throw new ServiceNotFoundException(id);
        }

        this.serviceRepository.deleteById(id);
    }
}