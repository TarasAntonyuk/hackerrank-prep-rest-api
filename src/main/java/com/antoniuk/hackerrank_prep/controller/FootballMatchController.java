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

    /**
     * 1. Draw Matches in a Given Year
     * Count the number of matches in a given year when the scores were equal
     * (i.e., team1goals == team2goals).
     */
    @GetMapping("/draws")
    public ResponseEntity<?> getDrawMatchesCountNew(@RequestParam(required = false) Integer year) {

        if (year == null) {
            return ResponseEntity.badRequest().body("Missing required parameter: year");
        }

        if (year < 1900 || year > LocalDate.now().getYear()) {
            return ResponseEntity.badRequest().body("Invalid year value: " + year);
        }
        return ResponseEntity.ok(matchService.countDrawsMatches(year));
    }

    /**
     * 2. Total Goals by a Team in a Year
     * Calculate the total number of goals scored by a team
     * in a given year — as both team1 and team2.
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

    /**
     * 3. Total Wins by a Team in a Year
     * Count how many matches a specific team won:
     * • If the team is team1, check team1goals > team2goals
     * • If the team is team2, check team2goals > team1goals
     */
    @GetMapping("/winsbyteam")
    public ResponseEntity<?> getWinsByTeam(@RequestParam(required = false) Integer year,
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

        return ResponseEntity.ok(matchService.getWinsByTeamYear(year, team));

    }

    /**
     * 4. Team with the Most Wins in a Year
     * Determine which team won the most matches in a season (year in our case) by aggregating wins for each
     * team and returning the one with the highest count.
     */
    @GetMapping("/top-winner")
    public ResponseEntity<?> getTopWinner(@RequestParam(required = false) Integer year) {
        if (year == null) {
            return ResponseEntity.badRequest().body("Missing required parameter: year");
        }

        if (year < 1900 || year > LocalDate.now().getYear()) {
            return ResponseEntity.badRequest().body("Invalid year value: " + year);
        }

        return ResponseEntity.ok(matchService.getTeamMaximumWin(year));
    }

    /**
     * 5. Highest Scoring Match in a Year:
     * Find the match with the highest total goals (i.e., team1goals + team2goals) in a
     * given year.
     */
    @GetMapping("/highest-scoring")
    public ResponseEntity<?> getHighestScoringMatch(@RequestParam(required = false) Integer year) {
        if (year == null) {
            return ResponseEntity.badRequest().body("Missing required parameter: year");
        }

        if (year < 1900 || year > LocalDate.now().getYear()) {
            return ResponseEntity.badRequest().body("Invalid year value: " + year);
        }

        return ResponseEntity.ok(matchService.getMatchMaxGoals(year));
    }

    /**
     * 6. Matches with a Specific Scoreline
     * Count how many matches ended with a specific score, e.g., 3–3, in a given year:
     * • URL includes team1goals=3&team2goals=3
     */
    @GetMapping
    public ResponseEntity<?> getMatchCountByScoreline(
            @RequestParam(required = false) Integer year,
            @RequestParam int team1goals,
            @RequestParam int team2goals
    ) {

        if (year < 1900 || year > LocalDate.now().getYear()) {
            return ResponseEntity.badRequest().body("Invalid year value: " + year);
        }
        int count = matchService.countMatchesByScoreline(year, team1goals, team2goals);

        return ResponseEntity.ok(count);
    }

    /**
     * 7. Average Goals per Match by a Team
     * Calculate the average number of goals scored per match by a given team in a season.
     * • Include both team1goals and team2goals
     * • Divide total goals by number of matches
     */
    @GetMapping("/average-goals")
    public ResponseEntity<?> getAverageGoals(@RequestParam(required = false) Integer year,
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

        return ResponseEntity.ok(matchService.getAverageGoalsByTeamYear(year, team));
    }

}
