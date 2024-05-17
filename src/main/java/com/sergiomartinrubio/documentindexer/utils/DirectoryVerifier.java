package com.sergiomartinrubio.documentindexer.utils;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Optional;

// TODO: this requires testing
@Component
public class DirectoryVerifier {
    public Optional<File[]> verify(String directory) {
        File file = new File(directory);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                // TODO: check
                //  - each file is not a directory. Discard directories
                //  - the format of each file must be .txt, and discard non-txt files
                //    (we could allow PDF by using a library that allows reading PDFs)
                //  - there is at least one text file
                return Optional.of(files);
            }
        }
        return Optional.empty();
    }
}
