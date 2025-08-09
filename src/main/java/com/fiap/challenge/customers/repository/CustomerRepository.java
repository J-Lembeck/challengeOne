package com.fiap.challenge.customers.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiap.challenge.customers.entity.CustomerModel;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, UUID> {

    public boolean existsByCpfCnpj(String cpfCnpj);
   
    public Optional<CustomerModel> findByCpfCnpj(String cpfCnpj);

    public Optional<CustomerModel> findByCpfCnpjAndIdNot(String cpfCnpj, UUID id);
}