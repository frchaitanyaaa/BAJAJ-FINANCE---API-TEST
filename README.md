# Webhook SQL Solver (Spring Boot)

This app runs the challenge flow automatically at startup:

1. Calls `POST /hiring/generateWebhook/JAVA`.
2. Chooses SQL solution based on the last two digits of `regNo`.
3. Submits `finalQuery` to the returned webhook URL using JWT bearer auth.

## Configure

Edit `src/main/resources/application.yml`:

- `challenge.name`
- `challenge.reg-no`
- `challenge.email`
- `challenge.question1-sql`
- `challenge.question2-sql`

## Run

```bash
mvn spring-boot:run
```

No controller/endpoint is needed; the flow is triggered by `ApplicationRunner` on startup.
