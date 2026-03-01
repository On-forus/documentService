package com.service.document.service.impl;

import com.service.document.persistense.entity.Document;
import com.service.document.persistense.entity.History;
import com.service.document.persistense.repository.DocumentRepository;
import com.service.document.persistense.repository.RegistryRepository;
import com.service.document.service.DocumentService;
import com.service.document.usecases.dto.*;
import com.service.document.usecases.dto.enums.Action;
import com.service.document.usecases.dto.enums.ResultStatus;
import com.service.document.usecases.dto.enums.Status;
import com.service.document.usecases.error.DocumentNotFoundException;
import com.service.document.usecases.error.RegistryRollbackException;
import com.service.document.usecases.mapper.DocumentMapper;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.swing.plaf.ProgressBarUI;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final RegistryRepository registryRepository;

    @Override
    @Transactional
    public DocumentCreateResponseDTO createDocument(DocumentCreateRequestDTO documentCreateRequestDTO) {
        Document document = documentMapper.requestToEntity(documentCreateRequestDTO);
        document.setStatus(Status.DRAFT);
        document.setUuid(UUID.randomUUID());
        document.setCreateDate(LocalDate.now());
        document.setUpdateDate(LocalDate.now());

        documentRepository.save(document);
        //log.info("Document created successfully {}, {}", document.getId(), document.getStatus());
        return documentMapper.entityToResponse(document);
    }

    @Override
    @Transactional
    public List<DocumentCreateResponseDTO> createDocumentByList(List<DocumentCreateRequestDTO> documentCreateRequestDTOs) {
        List<Document> documents = documentMapper.requestListToDocumnetList(documentCreateRequestDTOs);
        documentRepository.saveAll(documents);
        return documentMapper.listDocumentToListResponse(documents);
    }


    @Override
    @Transactional
    public DocumentHistoryDTO findDocumentById(Long requestId) {
        log.info("Search for a document by ID {}", requestId);
        Document document = documentRepository
                .findById(requestId).orElseThrow(() -> new DocumentNotFoundException("Document with id: " + requestId + " not found!"));

        return documentMapper.entityToDocumentHistoryDto(document);
    }

    @Override
    @Transactional
    public List<DocumentHistoryDTO> getDocuments(List<Long> listId) {

        return documentMapper.listEntityToDocumentHistoryDto(documentRepository.findAllById(listId));
    }

    @Override
    @Transactional
    public List<DocumentHistoryDTO> getDocuments(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        List<Document> allPageableSorted = documentRepository.findAllByOrderByCreateDate(pageRequest).toList();
        return documentMapper.listEntityToDocumentHistoryDto(allPageableSorted);
    }

    @Override
    @Transactional
    public List<DocumentHistoryDTO> getDocumentsFilter(DocumentFilter filter) {
        List<Document> allByFilter = documentRepository.findAllByFilter(filter);
        return documentMapper.listEntityToDocumentHistoryDto(allByFilter);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public List<DocumentIdStatusDTO> submitStatus(List<Long> listId) {
        log.info("Send to submit status {}", listId);
        return changeStatus(listId, Status.DRAFT, Status.SUBMITTED, Action.SUBMIT);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public List<DocumentIdStatusDTO> approveStatus(List<Long> listId) {
        log.info("Send to approve status {}", listId);

        return changeStatus(listId, Status.SUBMITTED, Status.APPROVED, Action.APPROVE);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = RuntimeException.class)
    public List<DocumentIdStatusDTO> changeStatus(List<Long> listId, Status fromStatus, Status toStatus, Action action) {
        List<DocumentIdStatusDTO> documentIdStatusDTOList = new ArrayList<>();
        log.info("Change status {} -> {}", fromStatus, toStatus);

        List<Document> documents = getListDocumentsByStatus(listId, fromStatus);
        for (Document document : documents) {
            try {
                document.setStatus(toStatus);
                document.getHistory().add(History.builder()
                        .author(document.getAuthor())
                        .action(action)
                        .comments("")
                        .updateDate(LocalDate.now())
                        .document(document)
                        .build());

                if (toStatus.equals(Status.APPROVED)) {
                    saveRegistry(document);
                }

                documentIdStatusDTOList.add(setResultStatus(document.getId(), ResultStatus.SUCCESS));

            } catch (RuntimeException e) {
                log.error("{} : {}", e.getClass().getName(), e.getMessage());
                documentIdStatusDTOList.add(setResultStatus(document.getId(), ResultStatus.ERROR));
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }

        Set<Long> foundIds = documents.stream()
                .map(Document::getId)
                .collect(Collectors.toSet());
        Set<Long> idNotFound = new HashSet<>(listId);
        idNotFound.removeAll(foundIds);

        for (Long l : idNotFound) {
            documentIdStatusDTOList.add(setResultStatus(l, ResultStatus.NOT_FOUND));
        }
        return documentIdStatusDTOList;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<Document> getListDocumentsByStatus(List<Long> listId, Status status) {
        return documentRepository.findAllByStatus(listId, status);
    }

    public void saveRegistry(Document document) {
        registryRepository.save(documentMapper.documentToRegistry(document));
    }

    private DocumentIdStatusDTO setResultStatus(Long id, ResultStatus resultStatus) {
        return DocumentIdStatusDTO.builder()
                .id(id)
                .resultStatus(resultStatus)
                .build();
    }
}