# HealthRx BFHL Java Solution

This Spring Boot app performs the required flow automatically on startup:

1. Calls the `generateWebhook` API.
2. Resolves the SQL answer based on the registration number.
3. Submits the final SQL query to the returned webhook URL using the returned JWT token in the `Authorization` header.

## Tech Stack

- Java 21
- Spring Boot
- RestTemplate

## Project Structure

- `StartupFlowRunner` — starts the full flow when the app boots.
- `WebhookClient` — handles both outbound HTTP calls.
- `SqlSolutionService` — returns the final SQL query.
- `application.yml` — contains candidate details and API URL.
- `sql/final-query-q1.sql` — the final SQL query submitted for Question 1.

## Update Before Running

Edit `src/main/resources/application.yml` and replace the placeholder values:

```yaml
app:
  candidate:
    name: Your Name
    regNo: Your Registration Number
    email: your-email@example.com
```

## Run

```bash
mvn spring-boot:run
```

or

```bash
mvn clean package
java -jar target/bfhl-java-solution-1.0.0.jar
```

## Notes

- No controller or custom endpoint is used.
- The full flow runs from `ApplicationRunner`.
- The second API call sends the JWT token exactly in the `Authorization` header.
- The current implementation handles Question 1, which is assigned to odd registration numbers like `REG12347`.
