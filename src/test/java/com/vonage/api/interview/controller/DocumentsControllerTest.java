package com.vonage.api.interview.controller;

import com.vonage.api.interview.service.DocumentsIndexService;
import com.vonage.api.interview.utils.DirectoryVerifier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DocumentsController.class)
class DocumentsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DirectoryVerifier directoryVerifier;

    @MockBean
    private DocumentsIndexService service;

    @Test
    void shouldReturn201WhenDocumentIsIndexed() throws Exception {
        given(directoryVerifier.verify("/Foo/Bar/"))
                .willReturn(Optional.of(new File[1]));
        String requestBody = """
                {
                    "directory": "/Foo/Bar/"
                }
                """;

        mockMvc.perform(post("/v1/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(service).indexDocuments(new File[1]);
    }

    @Test
    void shouldReturn400WhenInvalidDirectory() throws Exception {
        String requestBody = """
                {
                    "directory": "/Foo/Bar/"
                }
                """;

        mockMvc.perform(post("/v1/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(directoryVerifier).verify("/Foo/Bar/");
        verifyNoInteractions(service);
    }
}