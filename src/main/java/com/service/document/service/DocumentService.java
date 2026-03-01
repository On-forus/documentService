package com.service.document.service;

import com.service.document.usecases.dto.*;

import java.util.List;

public interface DocumentService {

    DocumentCreateResponseDTO createDocument(DocumentCreateRequestDTO documentCreateRequestDTO);

    List<DocumentCreateResponseDTO> createDocumentByList(List<DocumentCreateRequestDTO> documentCreateRequestDTOS);

    DocumentHistoryDTO findDocumentById(Long requestId);

    List<DocumentHistoryDTO> getDocuments(List<Long> listId);

    List<DocumentHistoryDTO> getDocuments(int page, int size);

    List<DocumentHistoryDTO> getDocumentsFilter(DocumentFilter filter);

    List<DocumentIdStatusDTO> submitStatus(List<Long> listId);

    List<DocumentIdStatusDTO> approveStatus(List<Long> listId);

}
