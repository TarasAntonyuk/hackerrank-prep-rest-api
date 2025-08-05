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

## ✅ Completed Tasks and Endpoints

| # | Problem Title                                     | Status | Endpoint                  		                      |
|---|---------------------------------------------------|--------|---------------------------------------------------|
| 1 | Draw Matches in a Given Year	                     | ✅ Done | `/api/matches/draws?year=2011`                    |
| 2 | Total Goals by a Team in a Year                   | ✅ Done | `/api/matches/goalsbyteam?year=2014&team=Chelsea` |       
| 3 | Total Wins by a Team in a Year                    | ✅ Done | `/api/matches/winsbyteam?year=2014&team=Arsenal`  |
| 4 | Team with the Most Wins in a Year[¹](#footnote-1) | ✅ Done | `/api/matches/top-winner?year=2012`               |
| 5 | Highest Scoring Match in a Year                   | ✅ Done | `/api/matches/highest-scoring?year=2012`          |
| 6 | Matches with a Specific Scoreline                 | 🚧 WIP | `/api/matches/...`                                |
| 7 | Average Goals per Match by a Team                 | 🚧 WIP | `/api/matches/...`                                |
| 8 | Football Competition – Winners Goals              | 🚧 WIP | `/api/matches/...`                                |
> More endpoints will be added as new tasks are solved.
---

### Footnotes

[¹](#footnote-1): [Handling Team Name Variations in HackerRank Test Data](./docs/team-win-analysis.md)

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
│   │   |            |	└── FootballMatchApiClient.java 	# REST client for external APIs
│   │   │            ├───config
│   │   │            ├───controller
│   │   |            |	└── FootballMatchController.java 	# all endpoints here
│   │   │            ├───model
│   │   │            ├───repository
│   │   │            └───service
│   │   │               └── FootballMatchService.java     	# Business logic and task processing
...
├───postman 	# Postman collections for testing endpoints
├───pom.xml
└───README.md
```

🔍 Example Endpoint Usage
Request total goals for a team in a year:

```bash
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

