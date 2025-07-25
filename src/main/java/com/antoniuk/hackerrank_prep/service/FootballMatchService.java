package com.antoniuk.hackerrank_prep.service;

import com.antoniuk.hackerrank_prep.client.FootballMatchApiClient;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;


@Service
public class FootballMatchService {

    private final FootballMatchApiClient apiClient;

    public FootballMatchService(FootballMatchApiClient apiClient) {
        this.apiClient = apiClient;
    }

    //2. Total Goals by a Team in a Year
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

        //int year, String team, String typeTeam;
        //System.out.println("year = " + year + ", team = " + team + ", typeTeam = " + typeTeam + ", count = " + count);

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


}
