package com.antoniuk.hackerrank_prep.controller;


import com.antoniuk.hackerrank_prep.service.FootballMatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
@RequestMapping("/api/matches")
public class FootballMatchController {
    private final FootballMatchService matchService;

    public FootballMatchController(FootballMatchService matchService) {
        this.matchService = matchService;
    }




    /** 2. Total Goals by a Team in a Year
    Calculate the total number of goals scored by a team
    in a given year — as both team1 and team2.
    */
    @GetMapping("/goalsbyteam")
    public ResponseEntity<?> getGoalsByYearTeam(@RequestParam(required = false) Integer year,
                                             @RequestParam(required = false) String team) {
        if (year == null) {
            return ResponseEntity.badRequest().body("Missing required parameter: year");
        }

        if (year < 1900 || year > LocalDate.now().getYear()) {
            return ResponseEntity.badRequest().body("Invalid year value: " + year);
        }

        if (team == null) {
            return ResponseEntity.badRequest().body("Missing required parameter: team");
        }

        return ResponseEntity.ok(matchService.getGoalsByYearTeam(year, team));

    }











}
