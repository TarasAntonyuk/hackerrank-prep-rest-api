package com.antoniuk.hackerrank_prep.service;

import com.antoniuk.hackerrank_prep.client.FootballMatchApiClient;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;



@Service
public class FootballMatchService {

    private final FootballMatchApiClient apiClient;

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
    public int getGoalsByYearTeam(int year, String team){

        return getGoalsByYearTeamType(year, team, "team1")
                + getGoalsByYearTeamType(year, team, "team2");
    }

    int getGoalsByYearTeamType(int year, String team, String typeTeam) {
        int count;
        int totalPages;

        String keyGoal;
        if ( typeTeam.equals("team1")) {
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

    int getGoalCountByJsonObject(JSONObject json, String keyGoal){
        int count = 0;
        JSONArray data = json.getJSONArray("data");

        for (int j = 0; j <= data.length() - 1; j++) {
            JSONObject match = data.getJSONObject(j);
            count += match.getInt(keyGoal);
        }
        return count;
    }
    //END PROBLEM 2.

}
