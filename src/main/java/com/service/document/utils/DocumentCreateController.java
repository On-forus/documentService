package com.service.document.utils;

import com.service.document.controller.DocumentController;
import com.service.document.usecases.dto.DocumentCreateRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/test")
@RequiredArgsConstructor
@Slf4j
public class DocumentCreateController {

    @Value("${SIZE_DOCUMENT}")
    private String sizeDocument;

    private final DocumentController documentController;

    @PostMapping("/createDocuments")
    public ResponseEntity<String> createDocuments() {
        log.info("Create {} documents", sizeDocument);
        for (int i = 0; i < Integer.parseInt(sizeDocument); i++) {
            documentController.createDocument(new DocumentCreateRequestDTO(
                    "author_" + i,
                    "title_" + i
            ));
        }
        return ResponseEntity.ok().body("Documents create successful. Size " + sizeDocument);
    }

}
