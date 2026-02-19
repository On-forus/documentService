package com.service.document.persistense.repository;

import com.service.document.persistense.entity.Registry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistryRepository extends JpaRepository<Registry, Long> {

}
