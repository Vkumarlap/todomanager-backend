# To-Do Manager Backend Setup

The backend has been added to this Spring Boot project. The existing `pom.xml` has been preserved exactly as uploaded; no dependency or version changes were made.

## Configure MySQL
Edit `src/main/resources/application.properties` or set these environment variables:
- `DB_URL` (default: `jdbc:mysql://localhost:3306/todo_manager?...`)
- `DB_USERNAME` (default: `root`)
- `DB_PASSWORD` (default: `change_me`)

The URL enables database creation with `createDatabaseIfNotExist=true` when the MySQL account has permission. You can also create it manually:
```sql
CREATE DATABASE todo_manager;
```

## Run
From the `todomanager` project directory:
```bash
./mvnw spring-boot:run
```
Windows:
```bat
mvnw.cmd spring-boot:run
```
If Maven Wrapper cannot download Maven, install Maven and run `mvn spring-boot:run`.

Base API: `http://localhost:8080/api/tasks`

## Endpoints
- `POST /api/tasks` — create a task
- `GET /api/tasks` — list tasks
- `GET /api/tasks?status=PENDING` — filter by status
- `GET /api/tasks?priority=HIGH` — filter by priority
- `GET /api/tasks?search=meeting` — search title/description
- `GET /api/tasks/{id}` — get one task
- `PUT /api/tasks/{id}` — update task
- `PATCH /api/tasks/{id}/status` — update status
- `DELETE /api/tasks/{id}` — delete task

Create/update JSON:
```json
{
  "title": "Finish project",
  "description": "Complete and test the app",
  "dueDate": "2026-10-20",
  "priority": "HIGH",
  "status": "PENDING"
}
```

PATCH JSON:
```json
{ "status": "COMPLETED" }
```

No validation dependency was added to the POM; the required-title validation is implemented in the service layer.
