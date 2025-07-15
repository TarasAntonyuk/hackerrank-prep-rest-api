package com.antoniuk.hackerrank_prep.controller;


import com.antoniuk.hackerrank_prep.service.FootballMatchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



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
    public int getGoalsByYearTeam(@RequestParam Integer year,
                                  @RequestParam String team) {
        if (year == null) {
            throw new IllegalArgumentException("Year - parameter is required");
        }

        return matchService.getGoalsByYearTeam(year, team);
    }











}
