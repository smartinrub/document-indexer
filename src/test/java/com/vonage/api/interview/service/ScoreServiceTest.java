package com.vonage.api.interview.service;

import com.vonage.api.interview.model.Score;
import com.vonage.api.interview.repository.DocumentIndexRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ScoreServiceTest {

    @Mock
    private DocumentIndexRepository documentIndexRepository;

    @InjectMocks
    private ScoreService service;

    @Test
    void shouldReturnScores() {
        // GIVEN
        given(documentIndexRepository.findIndex("hello")).willReturn(Set.of("doc1"));
        given(documentIndexRepository.findIndex("world")).willReturn(Set.of("doc1", "doc2", "doc3"));
        given(documentIndexRepository.findIndex("foo")).willReturn(Set.of("doc1", "doc3"));
        given(documentIndexRepository.findIndex("bar")).willReturn(Set.of());

        // WHEN
        List<Score> scores = service.getScores("hello world foo bar");

        // THEN
        assertThat(scores).containsExactly(
                new Score("hello world foo", "doc1", 75),
                new Score("world foo", "doc3", 50),
                new Score("world", "doc2", 25)
        );
    }

    // TODO: we should test other scenarios and edge cases like when not matches are found
}