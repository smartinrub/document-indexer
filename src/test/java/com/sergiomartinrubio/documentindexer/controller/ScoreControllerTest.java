package com.sergiomartinrubio.documentindexer.controller;

import com.sergiomartinrubio.documentindexer.model.Score;
import com.sergiomartinrubio.documentindexer.service.ScoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ScoreController.class)
class ScoreControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScoreService scoreService;

    @Test
    void shouldReturnScores() throws Exception {
        given(scoreService.getScores("hello world"))
                .willReturn(List.of(
                        new Score("hello world", "doc1.txt", 80),
                        new Score("hello world", "doc2.txt", 50)
                ));

        mockMvc.perform(get("/v1/scores")
                        .param("search", "hello world")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].words").value("hello world"))
                .andExpect(jsonPath("$[0].fileName").value("doc1.txt"))
                .andExpect(jsonPath("$[0].score").value(80))
                .andExpect(jsonPath("$[1].words").value("hello world"))
                .andExpect(jsonPath("$[1].fileName").value("doc2.txt"))
                .andExpect(jsonPath("$[1].score").value(50));
    }
}