package com.antoniuk.hackerrank_prep.controller;

import com.antoniuk.hackerrank_prep.service.FootballCompetitionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/competitions")
public class FootballCompetitionController {

    private final FootballCompetitionService competitionService;

    public FootballCompetitionController(FootballCompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    /**
     * 8. Football Competition – Winners Goals
     * Implement a REST service that takes a competition name and a year as input, and returns
     * the total number of goals scored by the winning team in all matches of that competition
     * during the given year
      */
    @GetMapping("/winner/goals")
    public ResponseEntity<?> getDrawMatchesCountNew(@RequestParam(required = false) Integer year
                                                    , @RequestParam(required = false) String competition) {

        if (year == null) {
            return ResponseEntity.badRequest().body("Missing required parameter: year");
        }

        if (year < 1900 || year > LocalDate.now().getYear()) {
            return ResponseEntity.badRequest().body("Invalid year value: " + year);
        }

        if (competition == null) {
            return ResponseEntity.badRequest().body("Missing required parameter: competition");
        }

        return ResponseEntity.ok(competitionService.getWinnerGoals(year, competition));
    }

}
