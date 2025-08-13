package com.antoniuk.hackerrank_prep.client;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
public class FootballCompetitionApiClient {

    private static final String BASE_URL = "https://jsonmock.hackerrank.com/api/football_competitions";

    public JSONObject getWinnerByYearCompetition(Integer year, String competition) throws IOException {

        String stringUrl = BASE_URL
                + "?year=" + year
                + "&name="+ URLEncoder.encode(competition, StandardCharsets.UTF_8);

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
