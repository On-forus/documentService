package com.service.document.utils;


import com.service.document.controller.DocumentController;
import com.service.document.persistense.entity.Document;
import com.service.document.persistense.repository.DocumentRepository;
import com.service.document.usecases.dto.enums.Status;
import com.service.document.usecases.mapper.DocumentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@EnableAsync
public class ChangeStatus {
    private final DocumentController documentController;
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    @Value("${document-api.config.batch-size}")
    private String batchSize;

    @Async("getExecutor")
    @Scheduled(fixedDelay = 200)
    void submitWorker() {
        List<Document> documents = documentRepository.findDocumentBatchSizeListAndStatus(Status.DRAFT,
                PageRequest.of(0, Integer.parseInt(batchSize)));

        List<Long> listId = documentMapper.fromDocumentToLong(documents);
        if(listId.isEmpty()){
            return;
        }
        documentController.submitStatusRequest(listId);
    }

    @Async("getExecutor")
    @Scheduled(fixedDelay = 200)
    void approveWorker(){
        List<Document> documents = documentRepository.findDocumentBatchSizeListAndStatus(Status.SUBMITTED,
                PageRequest.of(0, Integer.parseInt(batchSize)));

        List<Long> listId = documentMapper.fromDocumentToLong(documents);
        if(listId.isEmpty()){
           return;
        }
        documentController.approveStatusRequest(listId);
    }
}
