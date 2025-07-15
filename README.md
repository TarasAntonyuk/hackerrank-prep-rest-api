# Total Goals by a Team in a Year — Java + Spring Boot

## Problem: Total Goals by a Team in a Year
Description:
You are given football match data for a specific year. 
Your task is to calculate the total number of goals scored by a given team 
during that year — counting matches where the team played as both team1 and team2.

Input Parameters:
team — the name of the team (string)
year — the year to search in (integer)

Output:
A single integer — the total number of goals scored by the given team in the specified year.

## Tech Stack
- Java 17+
- Spring Boot
- Maven

## How to Run
Make sure you have **Java 17+** and **Maven** installed.
Run the project from terminal:
```bash
mvn spring-boot:run
```

## Testing with Postman
You can test the API endpoints using Postman:
1. Import the provided Postman collection (`postman_collection.json`) into Postman.
2. Update the environment variables (e.g., `baseUrl`) if needed.
3. Send requests to test the functionality.


### Postman Collection

The Postman collection file is located in the `postman` folder:  
`postman/total-goals.postman_collection.json`
---

### Example Request
- **GET** `/total-goals?team=Barcelona&year=2011`

Response:
  39




