package com.service.document.persistense.repository;

import com.service.document.persistense.entity.Document;
import com.service.document.usecases.dto.DocumentFilter;

import java.util.List;

public interface FilterDocumentRepository {
    List<Document> findAllByFilter(DocumentFilter documentFilter);
}
