# hackerrank-prep-rest-api

This repository contains solutions to multiple tasks from the **HackerRank REST API Certification (Intermediate Level)**.  
All tasks are implemented in **Java 21** using **Spring Boot** within a **single package**, each task exposed via a different REST endpoint.

---

## 🎯 Project Goal

To solve and document multiple REST API challenges using:

- REST API consumption with pagination
- Data aggregation & filtering
- Java Streams & Collections
- Spring Boot REST controllers with multiple endpoints
- Clean architecture and testing

---
## 🚀 Preparing for the HackerRank Java REST API Test? Here’s
What You Can and Can’t Use

### ✅ Allowed:
- org.json.JSONObject
- org.json.JSONArray
- HttpURLConnection

### ❌ Not allowed:
- ObjectMapper
- Gson
- RestTemplate
- HttpClient
- Jackson
- Spring
- DTO mapping via external libraries

### 🔍 Confirmed facts:
- These tests are limited to standard JDK 8
- You cannot add external dependencies (pom.xml or build.gradle are not available)
- The code must run in an isolated HackerRank environment without imports like
  `com.fasterxml.*`
---

## 🌐 External Resources Provided by HackerRank
The following mock APIs are provided by HackerRank as part of the test environment.
Your REST service must consume them to compute the required result.
### 📗 1. https://jsonmock.hackerrank.com/api/football_matches
Used to fetch all matches played by the winner during the given year to calculate total goals.
- Endpoint format:
    - `https://jsonmock.hackerrank.com/api/football_matches?year={year}&team1={winner}&page={n}`
    - `https://jsonmock.hackerrank.com/api/football_matches?year={year}&team2={winner}&page={n}`
- Returns: A paginated list of matches with team1, team2, team1goals, team2goals.
### 📘 2. https://jsonmock.hackerrank.com/api/football_competitions
Used to fetch the winning team of a given football competition in a specific year.
- Endpoint format:
    - `https://jsonmock.hackerrank.com/api/football_competitions?name={competition}&year={year}`
- Returns: Competition metadata including the "winner" field.

***📌 Note:*** Both APIs are paginated. You must iterate over all pages to gather full data.

## 📝 Top 8 Java REST API Coding Questions on HackerRank

### 1. Draw Matches in a Given Year
Count the number of matches in a specified year when the scores were equal (i.e., `team1goals == team2goals`).

### 2. Total Goals by a Team in a Year
Calculate the total number of goals scored by a team in a given year, considering matches where the team was either `team1` or `team2`.

### 3. Total Wins by a Team in a Year
Count how many matches a specific team won in a given year:
- If the team is `team1`, check if `team1goals > team2goals`
- If the team is `team2`, check if `team2goals > team1goals`

### 4. Team with the Most Wins in a Year
Determine the team that won the most matches in a season by aggregating wins for each team and returning the one with the highest count.

### 5. Highest Scoring Match in a Year
Find the match with the highest total goals scored (i.e., `team1goals + team2goals`) in a given year.

### 6. Matches with a Specific Scoreline
Count the number of matches that ended with a specific scoreline, for example, `3–3` in a given year:
- Use URL parameters like `team1goals=3&team2goals=3` for filtering

### 7. Average Goals per Match by a Team
Calculate the average number of goals scored per match by a given team during a season:
- Include goals scored as both `team1` and `team2`
- Divide the total goals by the number of matches played

### 8. Football Competition – Winners Goals
Implement a REST service that takes a competition name and a year as input, and returns the total number of goals scored by the winning team across all matches of that competition during the specified year.



## ✅ Completed Tasks and Endpoints

| # | Problem Title                         | Status | Endpoint                  		                                      |
|---|---------------------------------------|--------|-------------------------------------------------------------------|
| 1 | Draw Matches in a Given Year	         | ✅ Done | `/api/matches/draws?year=2011`                                    |
| 2 | Total Goals by a Team in a Year       | ✅ Done | `/api/matches/goalsbyteam?year=2014&team=Chelsea`                 |       
| 3 | Total Wins by a Team in a Year        | ✅ Done | `/api/matches/winsbyteam?year=2014&team=Arsenal`                  |
| 4 | Team with the Most Wins in a Year[^1] | ✅ Done | `/api/matches/top-winner?year=2012`                               |
| 5 | Highest Scoring Match in a Year       | ✅ Done | `/api/matches/highest-scoring?year=2012`                          |
| 6 | Matches with a Specific Scoreline     | ✅ Done | `/api/matches/?year=2012&page=1&team1goals=3&team2goals=1`        |
| 7 | Average Goals per Match by a Team     | ✅ Done | `/api/matches/average-goals?year=2012&team=Barcelona`             |
| 8 | Football Competition – Winners Goals  | ✅ Done | `/api/competitions/winner/goals?year=2012&competition=Bundesliga` |
> More endpoints will be added as new tasks are solved.

---

## 🛠️ Technologies Used

- Java 21
- Spring Boot 3.x
- Maven
- org.json (for lightweight JSON manipulation)
- Postman (testing)


---

## 🚀 Getting Started

⬇️ Clone the project:
```bash
git clone https://github.com/TarasAntoniuk/hackerrank-prep-rest-api.git
cd hackerrank-prep-rest-api
```

🛠️ Build and run:
```bash
mvn clean install
mvn spring-boot:run
```

📁 Project Structure
```
├───src
│   ├───main
│   │   ├───java
│   │   │   └─com
│   │   │       └─antoniuk
│   │   │           └─hackerrank_prep
│   │   │            ├───HackerrankPrepApplication.java 	# Main Spring Boot application class
│   │   │            ├───client
│   │   |            |		├── FootballCompetitionApiClient.java 	# REST client for competition APIs
│   │   |            |		└── FootballMatchApiClient.java 		# REST client for matches APIs
│   │   │            ├───config
│   │   │            ├───controller
│   │   |            |		├── FootballCompetitionController.java 	# endpoint for problem #8 here
│   │   |            |		└── FootballMatchController.java 		# endpoints for problems 1-7 here
│   │   │            ├───model
│   │   │            ├───repository
│   │   │            └───service
│   │   │               ├── FootballMatchService.java     			# logic and task processing for problems 1-7
│   │   │               └── FootballCompetitionService.java 		# logic and task processing for problem 8
...
├───postman 	# Postman collections for testing endpoints
├───docs 	    # Notes and explanations for edge cases and test data quirks
├───pom.xml
└───README.md
```

🔍 Example Endpoint Usage
Request total goals for a team in a year:

```plaintext
GET http://localhost:8080/api/matches/draws?year=2011
```
---

## 👤 About the Author

**Taras Antoniuk**  
📧 [bronya2004@gmail.com](mailto:bronya2004@gmail.com)  
🔗 [LinkedIn](https://www.linkedin.com/in/taras-antoniuk-7a550816a/)  
💻 [HackerRank Profile](https://www.hackerrank.com/profile/bronya2004)

---

🤝 Contributions and suggestions are welcome!  
Feel free to fork this project, open issues, or reach out with ideas for improvement.

---

## Footnotes

[^1]:[Handling Team Name Variations in HackerRank Test Data](./docs/team-win-analysis.md)

