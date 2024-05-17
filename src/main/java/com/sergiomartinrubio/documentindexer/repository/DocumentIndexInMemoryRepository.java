package com.sergiomartinrubio.documentindexer.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Repository
public class DocumentIndexInMemoryRepository implements DocumentIndexRepository {
    private static final Map<String, Set<String>> WORDS_TO_DOCUMENTS = new HashMap<>();

    // TODO: Add unit tests
    @Override
    public void save(Map<String, Set<String>> wordsToDocuments) {
        WORDS_TO_DOCUMENTS.putAll(wordsToDocuments);
    }

    // TODO: Add unit tests
    @Override
    public Set<String> findIndex(String word) {
        Set<String> matches = WORDS_TO_DOCUMENTS.get(word);
        if (matches == null) {
            return Set.of();
        }
        return matches;
    }
}
