package com.fiap.persistence.repository;

import com.fiap.persistence.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {}
