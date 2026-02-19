package com.service.document.persistense.repository;

import com.service.document.persistense.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
}
