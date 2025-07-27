package com.fiap.challenge.parts.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.challenge.parts.entity.PartModel;

public interface PartsRepository extends JpaRepository<PartModel, UUID>{

}
