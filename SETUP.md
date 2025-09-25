# BloodMate Setup Guide

## Quick Start with Docker

1. **Install Docker Desktop**
2. **Run the application:**
   ```bash
   docker-compose up -d
   ```
3. **Access:**
   - Web: http://localhost:8080
   - Database: http://localhost:8081 (phpMyAdmin)

## Manual Setup

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.0+

### Steps
1. **Create database:**
   ```sql
   CREATE DATABASE bloodmate;
   ```

2. **Update credentials in `application.properties`**

3. **Build and run:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Access:** http://localhost:8080

## Default Credentials
- **phpMyAdmin:** bloodmate / bloodmate123
- **Admin:** admin / admin123

## API Testing
```bash
# Get donors
curl http://localhost:8080/api/donors

# Register donor
curl -X POST http://localhost:8080/api/donors/register \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","age":25,"email":"john@example.com"}'
```

## Troubleshooting
- Check MySQL is running
- Verify Java 17+ is installed
- Check port 8080 is available
- Review application logs for errors
