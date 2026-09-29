# RoomBook – Meeting Room Booking with Conflict Detection

Complete college-level Spring Boot + MySQL + Vanilla JS project.

## Requirements
- Java 17
- Spring Boot 3.5.6
- Maven
- MySQL 8.x
- IntelliJ IDEA

## 1. Database
You can create the database manually:

```sql
CREATE DATABASE roombook;
```

The application also has `createDatabaseIfNotExist=true`, but the MySQL user must have permission to create databases.

## 2. MySQL password
Open `src/main/resources/application.properties`.

Option A — directly set it for local college use:
```properties
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

Option B — use an environment variable:
```text
MYSQL_PASSWORD=YOUR_MYSQL_PASSWORD
```

If your MySQL root account has no password, leave it empty.

## 3. Run in IntelliJ
1. Extract ZIP.
2. Open the `RoomBook` folder in IntelliJ.
3. Set Project SDK to Java 17.
4. Wait for Maven dependencies to download.
5. Make sure MySQL is running.
6. Set the MySQL password.
7. Run `RoomBookApplication.java`.

## URLs
Frontend: http://localhost:8080/
Swagger: http://localhost:8080/swagger-ui/index.html
OpenAPI: http://localhost:8080/v3/api-docs

## API
Rooms: POST/GET `/api/rooms`, GET/PUT/DELETE `/api/rooms/{id}`
Employees: POST/GET `/api/employees`, GET/PUT/DELETE `/api/employees/{id}`
Bookings: POST/GET `/api/bookings`, GET/PUT/DELETE `/api/bookings/{id}`
Booking actions: PUT `/api/bookings/{id}/cancel`, PUT `/api/bookings/{id}/check-in`
Filters: GET `/api/bookings/room/{roomId}`, GET `/api/bookings/organizer/{employeeId}`

## Business rules
- Only CONFIRMED bookings block a room.
- Conflict condition: existing.start < requested.end AND existing.end > requested.start.
- Exact back-to-back times are allowed.
- Conflict returns HTTP 400 with: `Room is already booked for the selected time range.`
- Cancellation sets CANCELLED.
- Check-in sets checkInTime and keeps CONFIRMED.
- CANCELLED/RELEASED bookings cannot be checked in.
- A CONFIRMED booking without check-in is RELEASED at or after start time + 10 minutes.
- Scheduler checks every 60 seconds.

## Structure
`src/main/java/com/roombook/` contains Room, Employee, Booking and common exception packages.
Frontend is served directly by Spring Boot from `src/main/resources/static/`.
