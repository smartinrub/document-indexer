package com.sergiomartinrubio.documentindexer.utils;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class WordsExtractor {
    public Set<String> extractWords(File document) throws IOException {
        FileReader file = new FileReader(document.getPath());

        Set<String> words = new HashSet<>();
        BufferedReader reader = new BufferedReader(file);
        String line;
        while ((line = reader.readLine()) != null) {
            // TODO: naive implementation where anything is a word.
            //  Define what a word is and use reg expression.
            words.addAll(Arrays.stream(line.split(" ")).collect(Collectors.toSet()));
        }
        return words;
    }
}
