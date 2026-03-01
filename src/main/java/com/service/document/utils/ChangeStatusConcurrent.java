package com.service.document.utils;

import com.service.document.controller.DocumentController;
import com.service.document.persistense.repository.DocumentRepository;
import com.service.document.usecases.dto.DocumentIdStatusDTO;
import com.service.document.usecases.dto.enums.ResultStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Component
@EnableAsync
@Slf4j
@RequiredArgsConstructor
public class ChangeStatusConcurrent {

    private final DocumentController documentController;

    @Value("${document-api.config.attempts}")
    String attemptSize;

    int submit = 0;
    int approve = 0;
    int errorOrConflict = 0;

    @Async("fixedThreadPool")
    public void changeStatus(Long documentId) {

        for (int i = 0; i < Integer.parseInt(attemptSize); i++) {
            submitStatus(documentId);
        }

        for (int i = 0; i < Integer.parseInt(attemptSize); i++) {
            approveStatus(documentId);
        }

        log.info("RESULT CURRENT THREAD__ Submit: {} Approve: {}, Error/Conflict {}", submit, approve, errorOrConflict);
    }


    public void approveStatus(Long documentId) {

        List<DocumentIdStatusDTO> approveStatusBody = documentController.approveStatusRequest(List.of(documentId)).getBody();
        DocumentIdStatusDTO documentIdStatusDTO = approveStatusBody.get(0);

        if (documentIdStatusDTO.resultStatus().equals(ResultStatus.SUCCESS)) {
            approve++;
        } else if(!documentIdStatusDTO.resultStatus().equals(ResultStatus.NOT_FOUND)){
            errorOrConflict++;
        }

        log.info("APPROVE STATUS: {}", approveStatusBody);
    }


    public void submitStatus(Long documentId) {

        List<DocumentIdStatusDTO> submitStatusBody = documentController.submitStatusRequest(List.of(documentId)).getBody();
        DocumentIdStatusDTO documentIdStatusDTO = submitStatusBody.get(0);

        if (documentIdStatusDTO.resultStatus().equals(ResultStatus.SUCCESS)) {
            submit++;
        } else if(!documentIdStatusDTO.resultStatus().equals(ResultStatus.NOT_FOUND)){
            errorOrConflict++;
        }
        log.info("SUBMIT STATUS: {}", submitStatusBody);
    }
}
