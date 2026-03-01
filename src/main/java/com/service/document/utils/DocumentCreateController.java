package com.service.document.utils;

import com.service.document.controller.DocumentController;
import com.service.document.usecases.dto.DocumentCreateRequestDTO;
import com.service.document.utils.dto.TestStatusDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("api/v1/test")
@RequiredArgsConstructor
@Slf4j
public class DocumentCreateController {

    @Value("${SIZE_DOCUMENT}")
    private String sizeDocument;

    private final DocumentController documentController;
    private final ChangeStatusConcurrent changeStatusConcurrent;

    @PostMapping("/createDocumentList/{sizeId}")
    public ResponseEntity<String> createDocuments(@PathVariable Long sizeId) {
        List<DocumentCreateRequestDTO> documentList = new ArrayList<>();

        long size = Long.parseLong(sizeDocument);

        if (sizeId != null && sizeId > 0) {
            size = sizeId;
        }

        log.info("Create {} documents", size);
        for (long i = 0; i < size; i++) {
            documentList.add(new DocumentCreateRequestDTO(
                    "author_" + i,
                    "title_" + i
            ));
        }
        documentController.createDocumentsByList(documentList);

        return ResponseEntity.ok().body("Documents have been created successful. Size " + size);
    }


    @PostMapping("/changeStatus/{documentId}")
    public void testStatus(@PathVariable Long documentId){
        changeStatusConcurrent.changeStatus(documentId);
    }

}
