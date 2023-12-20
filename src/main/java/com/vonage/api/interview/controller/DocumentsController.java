package com.vonage.api.interview.controller;

import com.vonage.api.interview.exception.InvalidDirectoryException;
import com.vonage.api.interview.service.DocumentsIndexService;
import com.vonage.api.interview.utils.DirectoryVerifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

// TODO: secure using some kind of token-based authentication like JWTs, and maybe
//  define an ADMIN role for this endpoint
@RestController
@RequestMapping("/v1/documents")
public class DocumentsController {

    private final DirectoryVerifier directoryVerifier;
    private final DocumentsIndexService documentsIndexService;

    public DocumentsController(DirectoryVerifier directoryVerifier, DocumentsIndexService documentsIndexService) {
        this.directoryVerifier = directoryVerifier;
        this.documentsIndexService = documentsIndexService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    void indexDocuments(@RequestBody DocumentsIndexRequest request) throws IOException {
        File[] files = directoryVerifier.verify(request.directory()).orElseThrow(InvalidDirectoryException::new);

        // TODO: Should we allow processing the same documents multiple times?
        //  probably not, we should verify that the documents has not been process yet, and return 4xx if so

        documentsIndexService.indexDocuments(files);
    }


    // TODO: remove indexed document. This can be useful in case
    //  we consider the document is not relevant anymore

    record DocumentsIndexRequest(String directory) {
    }
}
