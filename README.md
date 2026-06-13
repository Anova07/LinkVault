# LinkVault 

A URL shortening backend service built using Java Spring Boot and MySQL.

## Features

- Create short URLs
- Redirect to original URLs
- Prevent duplicate URL creation
- Track URL clicks
- View URL analytics
- Request validation
- Global exception handling

## Tech Stack

- Java 21
- Spring Boot
- MySQL
- Maven

## APIs

POST /api/urls

GET /{shortCode}

GET /api/urls/{shortCode}/stats
