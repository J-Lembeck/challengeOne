package com.fiap.repository;

import com.fiap.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, UUID> {

    Boolean existsByDocumentNumber(String taxNumber);

    Boolean existsByEmail(String email);
}
