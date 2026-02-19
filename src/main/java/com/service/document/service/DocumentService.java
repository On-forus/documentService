package com.service.document.service;

import com.service.document.usecases.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface DocumentService {

    DocumentCreateResponseDTO createDocument(DocumentCreateRequestDTO documentCreateRequestDTO);

    List<DocumentCreateResponseDTO> createDocumentList(List<DocumentCreateRequestDTO> documentCreateRequestDTOs);

    DocumentHistoryDTO findDocumentById(Long requestId);

    List<DocumentHistoryDTO> getDocuments(List<Long> listId);

    List<DocumentHistoryDTO> getDocuments(int page, int size);

    List<DocumentHistoryDTO> getDocumentsFilter(DocumentFilter filter);

    List<DocumentIdStatusDTO> submitStatus(List<Long> listId);

    List<DocumentIdStatusDTO> approveStatus(List<Long> listId);


}
