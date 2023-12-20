package com.vonage.api.interview.repository;

import java.util.Map;
import java.util.Set;

// This interface is used an adapter pattern for plugging in
// different kind of storage types (e.g. in-memory, database, local file...)
public interface DocumentIndexRepository {
    void save(Map<String, Set<String>> wordsToDocuments);

    Set<String> findIndex(String word);
}
