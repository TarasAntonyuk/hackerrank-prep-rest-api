package com.antoniuk.hackerrank_prep.service;

import com.antoniuk.hackerrank_prep.client.FootballCompetitionApiClient;
import com.antoniuk.hackerrank_prep.client.FootballMatchApiClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FootballCompetitionService {

    FootballCompetitionApiClient competitionClient;
    FootballMatchApiClient matchClient;


    public FootballCompetitionService(FootballCompetitionApiClient competitionClient
            , FootballMatchApiClient matchClient) {
        this.competitionClient = competitionClient;
        this.matchClient = matchClient;
    }

    public int getWinnerGoals(Integer year, String competition){
        //1. get winner;

        int goals;
        try {
            String winner = getWinner(year, competition);
            goals = getGoals(year, competition, winner);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //2. calculate goals team1 + calculate goals team2
        return goals;
    }

    String getWinner(Integer year, String competition) throws IOException {

        JSONObject winnerPage = competitionClient.getWinnerByYearCompetition(year, competition);

        JSONArray winners = winnerPage.getJSONArray("data");

        JSONObject winner = winners.getJSONObject(0);

        return winner.getString("winner");

    }

    int getGoals(Integer year, String competition, String team){
        return getGoalsByTeamType( year,  competition,  team, "team1")
                + getGoalsByTeamType( year,  competition,  team, "team2");
    }

    int getGoalsByTeamType(Integer year, String competition, String team, String teamType){

        String keyGoal = "";
        int counter = 0;

        if(teamType.equals("team1")) keyGoal = "team1goals";
        else if (teamType.equals("team2")) keyGoal = "team2goals";

        try {
            JSONObject page = matchClient.fetchMatchesByYearCompetitionTeam(year, competition, team, teamType, 1);
            int pages = page.getInt("total_pages");
            counter = getGoalCountByJsonObject(page, keyGoal);

            for (int i = 2; i <= pages; i++) {
                page = matchClient.fetchMatchesByYearCompetitionTeam(year, competition, team, teamType, i);
                counter += getGoalCountByJsonObject(page, keyGoal);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return counter;
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
}
