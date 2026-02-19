package com.service.document;

import com.service.document.service.DocumentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class DocumentApplicationTests {

    @Autowired
     MockMvc mockMvc;

    @Autowired
     DocumentService documentService;

    @Test
    void handleGetAllDocumentsReturnValidResponseEntity() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/document/getDocuments");
        this.mockMvc.perform(requestBuilder)

                .andExpectAll(
                        status().is4xxClientError()
                );

    }

}
