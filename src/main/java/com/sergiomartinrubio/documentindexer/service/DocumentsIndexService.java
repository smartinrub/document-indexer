package com.sergiomartinrubio.documentindexer.service;

import com.sergiomartinrubio.documentindexer.repository.DocumentIndexRepository;
import com.sergiomartinrubio.documentindexer.utils.WordsExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class DocumentsIndexService {
    private static final Logger log = LoggerFactory.getLogger(DocumentsIndexService.class.getName());

    private final WordsExtractor wordsExtractor;
    private final DocumentIndexRepository repository;

    public DocumentsIndexService(WordsExtractor wordsExtractor, DocumentIndexRepository repository) {
        this.wordsExtractor = wordsExtractor;
        this.repository = repository;
    }

    public void indexDocuments(File[] documents) throws IOException {
        // Using a Set data structure for the filenames and assuming duplicate files are not allowed
        Map<String, Set<String>> wordsToDocuments = new HashMap<>();
        for (File document : documents) {
            Set<String> words = wordsExtractor.extractWords(document);
            for (String word : words) {
                wordsToDocuments.computeIfAbsent(word, k -> new HashSet<>())
                        .add(document.getName());
            }
        }
        if (wordsToDocuments.isEmpty()) {
            log.info("Documents provided were empty");
            return;
        }
        repository.save(wordsToDocuments);
    }
}
