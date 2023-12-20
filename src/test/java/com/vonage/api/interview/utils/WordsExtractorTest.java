package com.vonage.api.interview.utils;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class WordsExtractorTest {

    private final WordsExtractor wordsExtractor = new WordsExtractor();

    @Test
    void shouldExtractWords() throws IOException {
        File file = new ClassPathResource("docs/doc2.txt").getFile();

        // WHEN
        Set<String> words = wordsExtractor.extractWords(file);

        // THEN
        assertThat(words).containsExactlyInAnyOrder("Hello", "World", "Foo", "Bar");
    }

}