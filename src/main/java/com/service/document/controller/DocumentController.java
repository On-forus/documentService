package com.service.document.controller;

import com.service.document.service.DocumentService;
import com.service.document.usecases.dto.*;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/document")
@RequiredArgsConstructor
@Slf4j
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/create")
    public ResponseEntity<DocumentCreateResponseDTO> createDocument(
            @RequestBody DocumentCreateRequestDTO documentCreateRequestDTO) {
        log.info("Create document request {}", documentCreateRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(documentService.createDocument(documentCreateRequestDTO));
    }

    @GetMapping("/getDocument/{requestId}")
    public ResponseEntity<DocumentHistoryDTO> getOneDocumentWithHistory(@PathVariable Long requestId) {
        log.info("Get document with history by id {}", requestId);

        return ResponseEntity.status(HttpStatus.OK).body(documentService.findDocumentById(requestId));
    }

    @GetMapping("/getDocuments")
    public ResponseEntity<List<DocumentHistoryDTO>> getListDocumentWithHistory(@RequestBody List<Long> listId) {
        log.info("Get document with history by id: {}", listId);
        List<DocumentHistoryDTO> listDocument = documentService.getDocuments(listId);
        return ResponseEntity.status(HttpStatus.OK).body(listDocument);
    }

    @GetMapping("/getDocumentPage")
    public ResponseEntity<List<DocumentHistoryDTO>> getListDocumentWithPagination(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {

        return ResponseEntity.status(HttpStatus.OK).body(documentService.getDocuments(page, size));
    }

    @GetMapping("/getDocumentsByFilter")
    public ResponseEntity<List<DocumentHistoryDTO>> getListDocumentWithHistory(@RequestBody DocumentFilter filter) {
        return ResponseEntity.status(HttpStatus.OK).body(documentService.getDocumentsFilter(filter));
    }

    @PutMapping("/submit")
    public ResponseEntity<List<DocumentIdStatusDTO>> submitStatusRequest(
            @RequestBody @Size(min = 0, max = 1000) List<Long> listId) {

        List<DocumentIdStatusDTO> listDocumentIdStatusDTO = documentService.submitStatus(listId);
        return ResponseEntity.status(HttpStatus.OK).body(listDocumentIdStatusDTO);
    }

    @PutMapping("/approve")
    public ResponseEntity<List<DocumentIdStatusDTO>> approveStatusRequest(
            @RequestBody @Size(min = 0, max = 1000) List<Long> listId) {

        List<DocumentIdStatusDTO> listDocumentIdStatusDTO = documentService.approveStatus(listId);
        return ResponseEntity.status(HttpStatus.OK).body(listDocumentIdStatusDTO);
    }


}
