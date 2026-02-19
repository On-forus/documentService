package com.service.document.persistense.repository;

import com.service.document.persistense.entity.Document;
import com.service.document.persistense.entity.History;
import com.service.document.usecases.dto.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long>,
        FilterDocumentRepository{

//    @Query(value = "SELECT * From documents WHERE id=:id AND status ='DRAFT' ", nativeQuery = true)
//    Optional<Document> findByIdAndStatus(Long id);
    List<Document> history(List<History> history);


    Page<Document> findAllByOrderByCreateDate(Pageable pageable);
    @Query(value = "select d from Document d where d.id IN :listId AND d.status =:status")
    List<Document> findAllByStatus(List<Long> listId, Status status);

    @Query(value = "select d from Document d where d.status =:status")
    List<Document> findDocumentBatchSizeListAndStatus(Status status, Pageable pageable);


}
