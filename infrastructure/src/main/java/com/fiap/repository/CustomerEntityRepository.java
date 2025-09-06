package com.fiap.repository;

import com.fiap.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, UUID> {

    Boolean existsByDocumentNumber(String taxNumber);

    Boolean existsByEmail(String email);
}
