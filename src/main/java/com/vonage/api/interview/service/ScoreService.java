package com.vonage.api.interview.service;

import com.vonage.api.interview.model.Score;
import com.vonage.api.interview.repository.DocumentIndexRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScoreService {
    private final DocumentIndexRepository documentIndexRepository;

    public ScoreService(DocumentIndexRepository documentIndexRepository) {
        this.documentIndexRepository = documentIndexRepository;
    }

    public List<Score> getScores(String sentence) {
        // naive assumption that the provided sentence has spaces as separator
        // and each element is a word
        Map<String, List<String>> documentToWords = new HashMap<>();
        String[] words = sentence.split(" ");
        for (String word : words) {
            Set<String> documents = documentIndexRepository.findIndex(word);
            for (String document : documents) {
                documentToWords.computeIfAbsent(document, k -> new ArrayList<>()).add(word);
            }
        }

        List<Score> scores = new ArrayList<>();
        for (Map.Entry<String, List<String>> document : documentToWords.entrySet()) {
            double scoreValue = (float) document.getValue().size() / words.length * 100;
            scores.add(new Score(
                    String.join(" ", document.getValue()),
                    document.getKey(),
                    scoreValue
            ));
        }
        // TODO: if score is the same maybe we can order by file name
        return scores.stream()
                .sorted(Comparator.comparingDouble(Score::score).reversed())
                .toList();
    }
}
