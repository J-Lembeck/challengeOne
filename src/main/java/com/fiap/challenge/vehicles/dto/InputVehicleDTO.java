package com.fiap.challenge.vehicles.dto;

import java.util.UUID;

public record InputVehicleDTO(
    UUID customerId,
    String licensePlate,
    String brand,
    String model,
    Integer year
) {}