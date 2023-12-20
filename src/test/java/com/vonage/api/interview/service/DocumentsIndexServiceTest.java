package com.vonage.api.interview.service;

import com.vonage.api.interview.repository.DocumentIndexRepository;
import com.vonage.api.interview.utils.WordsExtractor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class DocumentsIndexServiceTest {

    @Mock
    private WordsExtractor wordsExtractor;

    @Mock
    private DocumentIndexRepository repository;

    @InjectMocks
    private DocumentsIndexService service;

    @Test
    void shouldSaveWords() throws IOException {
        // GIVEN
        File[] documents = new File[2];
        File file1 = new ClassPathResource("docs/doc1.txt").getFile();
        File file2 = new ClassPathResource("docs/doc2.txt").getFile();
        documents[0] = file1;
        documents[1] = file2;
        given(wordsExtractor.extractWords(file1)).willReturn(Set.of("Hello", "World"));
        given(wordsExtractor.extractWords(file2)).willReturn(Set.of("Hello", "World", "Foo", "Bar"));

        // WHEN
        service.indexDocuments(documents);

        // THEN
        verify(repository).save(Map.of(
                        "Hello", Set.of("doc1.txt", "doc2.txt"),
                        "World", Set.of("doc1.txt", "doc2.txt"),
                        "Foo", Set.of("doc2.txt"),
                        "Bar", Set.of("doc2.txt")
                )
        );
    }

    @Test
    void shouldNotSaveWhenNoFiles() throws IOException {
        // GIVEN
        File[] documents = new File[0];

        // WHEN
        service.indexDocuments(documents);

        // THEN
        verifyNoInteractions(repository);
    }

    @Test
    void shouldNotSaveWhenDocumentsAreEmpty() throws IOException {
        // GIVEN
        File[] documents = new File[2];
        File file = new ClassPathResource("docs/emptyDoc.txt").getFile();
        documents[0] = file;
        given(wordsExtractor.extractWords(file)).willReturn(Set.of());

        // WHEN
        service.indexDocuments(documents);

        // THEN
        verifyNoInteractions(repository);
    }
}