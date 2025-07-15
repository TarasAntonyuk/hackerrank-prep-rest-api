package com.antoniuk.hackerrank_prep.client;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;


import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

//
import org.json.JSONObject;


@Service
public class FootballMatchApiClient {


    private static final String BASE_URL = "https://jsonmock.hackerrank.com/api/football_matches";

    public JSONObject fetchMatchesByYearTeam(int year, String team, String teamType, int page ) throws IOException {



        String stringUrl = BASE_URL +
                "?year=" +
                year +
                "&page=" +
                page +
                "&" + teamType + "=" + URLEncoder.encode(team, StandardCharsets.UTF_8);


        URL url = new URL(stringUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
        );

        String responseBody = in.lines().collect(Collectors.joining());

        return new JSONObject(responseBody);

    }





}
