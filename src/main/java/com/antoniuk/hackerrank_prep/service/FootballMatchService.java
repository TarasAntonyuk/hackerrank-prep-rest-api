package com.antoniuk.hackerrank_prep.service;

import com.antoniuk.hackerrank_prep.client.FootballMatchApiClient;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Service
public class FootballMatchService {

    private final FootballMatchApiClient apiClient;

    private static final Map<String, String> TEAM_ALIASES = Map.of(
            "FC Barcelona", "Barcelona",
            "Barcelona FC", "Barcelona",
            "Barcelona", "Barcelona"
    );

    public FootballMatchService(FootballMatchApiClient apiClient) {
        this.apiClient = apiClient;
    }

    //PROBLEM 1. Count the number of matches in a given year
    public int countDrawsMatches(int year) {
        final int MAX_SCORE = 10;

        int drawCount = 0;
        try {

            for (int i = 0; i < MAX_SCORE; i++) {
                JSONObject jsonPage = apiClient.fetchDrawMatchesPageByYear(year, 1, i);
                drawCount += jsonPage.getInt("total");
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to get data from remote API. " + e.getMessage());
        }

        return drawCount;
    }
    //END PROBLEM 1

    //PROBLEM 2. Total Goals by a Team in a Year
    public int getGoalsByYearTeam(int year, String team) {

        return getGoalsByYearTeamType(year, team, "team1")
                + getGoalsByYearTeamType(year, team, "team2");
    }

    int getGoalsByYearTeamType(int year, String team, String typeTeam) {
        int count;
        int totalPages;

        String keyGoal;
        if (typeTeam.equals("team1")) {
            keyGoal = "team1goals";
        } else {
            keyGoal = "team2goals";
        }

        try {
            JSONObject json = apiClient.fetchMatchesByYearTeam(year, team, typeTeam, 1);

            count = getGoalCountByJsonObject(json, keyGoal);

            totalPages = json.getInt("total_pages");
            for (int i = 2; i <= totalPages; i++) {
                json = apiClient.fetchMatchesByYearTeam(year, team, typeTeam, i);
                count += getGoalCountByJsonObject(json, keyGoal);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to get data from HR " + e);
        }

        return count;
    }

    int getGoalCountByJsonObject(JSONObject json, String keyGoal) {
        int count = 0;
        JSONArray data = json.getJSONArray("data");

        for (int j = 0; j <= data.length() - 1; j++) {
            JSONObject match = data.getJSONObject(j);
            count += match.getInt(keyGoal);
        }
        return count;
    }
    //END PROBLEM 2.

    //PROBLEM 3
    public int getWinsByTeamYear(int year, String team) {

        return getWinsByTeamYearRole(year, team, "team1")
                + getWinsByTeamYearRole(year, team, "team2");

    }

    public int getWinsByTeamYearRole(int year, String team, String typeTeam) {

        int winsCount;
        //get first page
        try {
            JSONObject page = apiClient.fetchMatchesByYearTeam(year, team, typeTeam, 1);
            winsCount = getWinsByPage(page, typeTeam);

            int pages = page.getInt("total_pages");

            for (int i = 2; i <= pages; i++) {
                page = apiClient.fetchMatchesByYearTeam(year, team, typeTeam, i);
                winsCount = winsCount + getWinsByPage(page, typeTeam);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to get data from remote API" + e.getMessage());
        }

        return winsCount;

    }

    int getWinsByPage(JSONObject page, String typeTeam) {

        String otherTeam;
        if (typeTeam.equals("team1")) {
            otherTeam = "team2";
        } else {
            otherTeam = "team1";
        }


        String keyOurTeamGoals = typeTeam + "goals";
        String keyOtherTeamGoals = otherTeam + "goals";

        int winsCount = 0;
        JSONArray data = page.getJSONArray("data");

        for (int i = 0; i < data.length(); i++) {
            JSONObject match = data.getJSONObject(i);
            if (Integer.parseInt(match.getString(keyOurTeamGoals)) >
                    Integer.parseInt(match.getString(keyOtherTeamGoals))) {
                winsCount++;
            }
        }
        return winsCount;
    }
    //END PROBLEM 3

    //PROBLEM 4
    public String getTeamMaximumWin(int year) {

        Map<String, Integer> map = new HashMap<>();

        try {
            JSONObject page = apiClient.fetchMatchesByYear(year, 1);
            int pages = page.getInt("total_pages");
            addWins(page, map);

            for (int i = 2; i <= pages; i++) {
                page = apiClient.fetchMatchesByYear(year, i);
                addWins(page, map);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to get data from remote API. " + e.getMessage());
        }

        int maxValue = Integer.MIN_VALUE;
        String maxKey = "";

        for (Map.Entry<String, Integer> element : map.entrySet()) {
            if (element.getValue() > maxValue) {
                maxValue = element.getValue();
                maxKey = element.getKey();
            }
        }
        return "Team : " + maxKey + " with wins : " + maxValue;
    }

    void addWins(JSONObject page, Map<String, Integer> map) {
        JSONArray data = page.getJSONArray("data");

        for (int i = 0; i < data.length(); i++) {


            JSONObject match = data.getJSONObject(i);

            int team1goals = Integer.parseInt(match.getString("team1goals"));
            int team2goals = Integer.parseInt(match.getString("team2goals"));

            if (team1goals == team2goals) continue;

            String winner = (team1goals > team2goals) ? match.getString("team1") : match.getString("team2");


            String normWinner = normalizeTeamName(winner);
            map.put(normWinner, map.getOrDefault(normWinner, 0) + 1);

        }

    }

    String normalizeTeamName(String teamName) {
        return TEAM_ALIASES.getOrDefault(teamName, teamName);
    }
    //END PROBLEM 4

    /**
     * 5. Highest Scoring Match in a Year
     * Find the match with the highest total goals (i.e., team1goals + team2goals) in a
     * given year.
     */
    //PROBLEM 5

    static class ResultHolder {
        int maxGoals = -1;
        JSONObject bestMatch = null;
    }

    public String getMatchMaxGoals(int year) {

        ResultHolder res = new ResultHolder();

        try {

            JSONObject page = apiClient.fetchMatchesByYear(year, 1);
            checkMaxGoals(page, res);

            int pages = page.getInt("total_pages");
            for (int i = 2; i < pages; i++) {
                page = apiClient.fetchMatchesByYear(year, i);
                checkMaxGoals(page, res);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to get data from remote API. " + e.getMessage());
        }

        return res.bestMatch != null ? res.bestMatch.toString() : null;

    }

    void checkMaxGoals(JSONObject page, ResultHolder res) {

        JSONArray matches = page.getJSONArray("data");

        for (int i = 0; i < matches.length(); i++) {
            JSONObject match = matches.getJSONObject(i);

            int team1goals = Integer.parseInt(match.getString("team1goals"));
            int team2goals = Integer.parseInt(match.getString("team2goals"));

            if (team1goals + team2goals >= res.maxGoals) {
                res.maxGoals = team1goals + team2goals;
                res.bestMatch = match;
            }
        }
    }

    //END PROBLEM 5

    //PROBLEM 6

    /**
     * 6. Matches with a Specific Scoreline
     * Count how many matches ended with a specific score, e.g., 3–3, in a given year:
     * • URL includes team1goals=3&team2goals=3
     */
    public int countMatchesByScoreline(int year, int team1goals, int team2goals) {

        int counter;
        try {

            JSONObject page = apiClient.fetchMatchesByScoreLineYear(year, team1goals, team2goals, 1);
            counter = page.getInt("total");

        } catch (IOException e) {
            throw new RuntimeException("Failed to get data from remote API! " + e.getMessage());
        }

        return counter;
    }
    //END PROBLEM 6

    //PROBLEM 7
    /**
     * 7. Average Goals per Match by a Team
     * Calculate the average number of goals scored per match by a given team in a season.
     * • Include both team1goals and team2goals
     * • Divide total goals by number of matches
     */
    static class AverageResult{
        int goals = 0;
        int matches = 0;
    }

    public double getAverageGoalsByTeamYear(Integer year, String team){

        AverageResult result = new AverageResult();

        getGoalsMatchesByRole(year, team, "team1", result);
        getGoalsMatchesByRole(year, team, "team2", result);

        return result.matches == 0 ? 0: (double) result.goals / result.matches;

    }

    void getGoalsMatchesByRole(Integer year, String team, String role, AverageResult result){

        String keyGoal;
        if (role.equals("team1")) {
            keyGoal = "team1goals";
        } else {
            keyGoal = "team2goals";
        }

        try {
            JSONObject page = apiClient.fetchMatchesByYearTeam(year, team, role, 1);
            int pages = page.getInt("total_pages");
            result.matches += page.getInt("total");
            result.goals += getGoalCountByJsonObject(page, keyGoal);

            for (int i = 2; i < pages; i++) {
                page = apiClient.fetchMatchesByYearTeam(year, team, role, i);
                result.goals += getGoalCountByJsonObject(page, keyGoal);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to get data from remote API! " + e.getMessage());
        }

    }







}
