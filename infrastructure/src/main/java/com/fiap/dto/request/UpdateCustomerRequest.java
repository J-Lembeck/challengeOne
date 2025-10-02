package com.fiap.dto.request;

import java.util.UUID;

public record UpdateCustomerRequest(UUID id, String name, String documentNumber, String email, String phone){

}
