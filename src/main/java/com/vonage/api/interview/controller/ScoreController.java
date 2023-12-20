package com.vonage.api.interview.controller;

import com.vonage.api.interview.model.Score;
import com.vonage.api.interview.service.ScoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// TODO: secure using some kind of token-based authentication like JWTs, and maybe
//  define an USER role for this endpoint
@RestController
@RequestMapping("/v1/scores")
public class ScoreController {
    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping
    List<Score> getScores(@RequestParam String search) {
        return scoreService.getScores(search);
    }
}
