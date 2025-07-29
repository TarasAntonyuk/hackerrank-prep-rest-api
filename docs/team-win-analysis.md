# Handling Team Name Variations in HackerRank Test Data

## 🧩 The Problem

While solving a HackerRank coding challenge involving football match data, I encountered a real-world data issue:  
**the same team appeared under different names** in the test dataset.

Examples:
- `FC Barcelona`
- `Barcelona`
- `Barcelona FC`

The HackerRank test case logic treats these as separate teams, which breaks logic based on counting wins or performance per team.

> ✅ This is not a theoretical edge case — it directly affects solution correctness.

---

## 🧠 Why This Fails Without Fixing

When you aggregate statistics like number of wins per team:
- `"FC Barcelona"` might have 18 wins,
- `"Barcelona"` another 6,
- and the total team performance is never recognized correctly.

Even though the solution logic is correct, the result fails due to inconsistent naming.

---

## ✅ The Fix: Normalizing Team Names

To handle this, I introduced a **team alias map** in Java:

```java
private static final Map<String, String> TEAM_ALIASES = Map.of(
        "FC Barcelona", "Barcelona",
        "Barcelona FC", "Barcelona",
        "Man United", "Manchester United"
);

private String normalizeTeamName(String teamName) {
    return TEAM_ALIASES.getOrDefault(teamName, teamName);
}
```
Then, in my logic that processes wins or match results, I apply the normalization:
```java
String normalized = normalizeTeamName(originalTeamName);
map.put(normalized, map.getOrDefault(normalized, 0) + 1);
```
This ensures all name variants are counted under the same logical team.

## 🏭 What Happens in Production
In production systems, this kind of issue is usually avoided by design:
- Teams are stored in a database table with a unique identifier (ID).
- Business logic refers to teams by ID, not name.
- Names are only used for display or translation, and variations don't affect logic.

This ensures data integrity even across international sources or multiple systems.