package com.fiap.core.domain.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private UUID id;
    private String name;
    private DocumentNumber documentNumber;
    private String phone;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Customer customer)) return false;

        return getId().equals(customer.getId()) && getName().equals(customer.getName()) && getDocumentNumber().equals(customer.getDocumentNumber()) && getPhone().equals(customer.getPhone()) && getEmail().equals(customer.getEmail()) && getCreatedAt().equals(customer.getCreatedAt()) && Objects.equals(getUpdatedAt(), customer.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getDocumentNumber().hashCode();
        result = 31 * result + getPhone().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getCreatedAt().hashCode();
        result = 31 * result + Objects.hashCode(getUpdatedAt());
        return result;
    }
}
