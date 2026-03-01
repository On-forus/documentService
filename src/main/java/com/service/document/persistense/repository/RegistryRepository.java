package com.service.document.persistense.repository;

import com.service.document.persistense.entity.Registry;
import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface RegistryRepository extends JpaRepository<Registry, Long> {

}
