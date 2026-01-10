# Eskimi Backend Assignment

A Spring Boot REST API application that provides three main functionalities:
1. **Number of Days Calculator** - Calculate days between two dates
2. **Number to Words Converter** - Convert numbers to English words
3. **Temperature Statistics** - Get temperature stats for Dhaka with text conversion

## Table of Contents
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)
- [Build & Run Instructions](#build--run-instructions)
    - [Option 1: Using Docker (Recommended)](#option-1-using-docker-recommended)
    - [Option 2: Using Docker Compose](#option-2-using-docker-compose)
    - [Option 3: Using Maven (Local)](#option-3-using-maven-local)
- [Running Tests](#running-tests)
- [API Usage Examples](#api-usage-examples)
- [Health Check](#health-check)

---

## Prerequisites

### For Docker (Recommended)
- Docker 20.10+ ([Install Docker](https://docs.docker.com/get-docker/))
- Docker Compose 1.29+ (usually included with Docker Desktop)

### For Local Development
- Java 17 or higher ([Download JDK](https://adoptium.net/))
- Maven 3.6+ ([Download Maven](https://maven.apache.org/download.cgi))
- Git ([Download Git](https://git-scm.com/downloads))

---

## Project Structure

```
eskimi-backend-assignment/
├── src/
│   ├── main/
│   │   ├── java/com/eskimi/backend_assignment/
│   │   │   ├── controller/          # REST Controllers
│   │   │   ├── service/             # Business Logic
│   │   │   ├── model/               # DTOs and Models
│   │   │   ├── exception/           # Custom Exceptions
│   │   │   └── BackendAssignmentApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/                        # Unit Tests
├── Dockerfile
├── docker-compose.yml
├── .dockerignore
├── pom.xml
└── README.md
```

---

## API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/v1/days/calculate` | POST | Calculate days between two dates |
| `/api/v1/convert` | POST | Convert number to words |
| `/api/v1/temperature/stats` | POST | Get temperature statistics for Dhaka |
| `/actuator/health` | GET | Application health check |

---

## Build & Run Instructions

### Option 1: Using Docker (Recommended)

This is the **easiest way** to run the application without installing Java or Maven.

#### Step 1: Clone the Repository
```bash
git clone https://github.com/Shif789/Eskimi-Backend-Task.git
cd Eskimi-Backend-Task
```

#### Step 2: Build the Docker Image
```bash
docker build -t eskimi-backend-assignment:latest .
```

This command:
- Builds the application
- Runs all unit tests
- Creates an optimized Docker image

#### Step 3: Run the Container
```bash
docker run -d \
  --name eskimi-backend \
  -p 8080:8080 \
  eskimi-backend-assignment:latest
```

#### Step 4: Verify the Application is Running
```bash
# Check container logs
docker logs eskimi-backend

# Check health status
curl http://localhost:8080/actuator/health
```

#### Stop and Remove Container
```bash
docker stop eskimi-backend
docker rm eskimi-backend
```

---

### Option 2: Using Docker Compose

Docker Compose provides an easier way to manage the container.

#### Step 1: Clone the Repository
```bash
git clone <your-repository-url>
cd eskimi-backend-assignment
```

#### Step 2: Start the Application
```bash
docker-compose up -d
```

This will:
- Build the image
- Run all tests
- Start the container in detached mode

#### Step 3: View Logs
```bash
docker-compose logs -f
```

#### Step 4: Stop the Application
```bash
docker-compose down
```

---

### Option 3: Using Maven (Local)

If you prefer to run the application locally without Docker.

#### Step 1: Verify Prerequisites
```bash
java -version    # Should show Java 17+
mvn -version     # Should show Maven 3.6+
```

#### Step 2: Clone the Repository
```bash
git clone <your-repository-url>
cd eskimi-backend-assignment
```

#### Step 3: Build and Test
```bash
mvn clean install
```

This will:
- Download all dependencies
- Compile the code
- Run all unit tests
- Create a JAR file in `target/` directory

#### Step 4: Run the Application
```bash
mvn spring-boot:run
```

Or run the JAR directly:
```bash
java -jar target/backend-assignment-1.0.0.jar
```

#### Step 5: Verify
```bash
curl http://localhost:8080/actuator/health
```

---

## Running Tests

### Using Docker
Tests are automatically run during the Docker build process.

### Using Maven
```bash
# Run all tests
mvn test

# Run tests with coverage report
mvn test jacoco:report

# Run specific test class
mvn test -Dtest=DaysCalculatorServiceTest

# Skip tests (not recommended)
mvn clean install -DskipTests
```

### Test Coverage
The project includes comprehensive unit tests for:
- DaysCalculatorService
- NumberToWordsService
- TemperatureStatsService
- TemperatureTextConverter
- All REST Controllers

---

## API Usage Examples

### 1. Calculate Days Between Dates

**Request:**
```bash
curl -X POST http://localhost:8080/api/v1/days/calculate \
  -H "Content-Type: application/json" \
  -d '{
    "startDate": "2024-01-01",
    "endDate": "2024-12-31"
  }'
```

**Response:**
```json
{
  "numberOfDays": 365
}
```

---

### 2. Convert Number to Words

**Request:**
```bash
curl -X POST http://localhost:8080/api/v1/convert \
  -H "Content-Type: application/json" \
  -d '{
    "number": 36.40
  }'
```

**Response:**
```json
{
  "words": "thirty six point four zero"
}
```

---

### 3. Get Temperature Statistics

**Request:**
```bash
curl -X POST http://localhost:8080/api/v1/temperature/stats \
  -H "Content-Type: application/json" \
  -d '{
    "startDate": "2025-01-01",
    "endDate": "2025-01-07"
  }'
```

**Response:**
```json
{
  "min": 15.2,
  "max": 28.5,
  "average": 21.85,
  "minText": "positive fifteen point two zero",
  "maxText": "positive twenty eight point five zero",
  "averageText": "positive twenty one point eight five"
}
```

---

## Health Check

Check if the application is running properly:

```bash
curl http://localhost:8080/actuator/health
```

**Expected Response:**
```json
{
  "status": "UP"
}
```

---

## Docker Commands Cheat Sheet

```bash
# Build image
docker build -t eskimi-backend-assignment:latest .

# Run container
docker run -d --name eskimi-backend -p 8080:8080 eskimi-backend-assignment:latest

# View logs
docker logs eskimi-backend
docker logs -f eskimi-backend  # Follow logs

# Check container status
docker ps

# Stop container
docker stop eskimi-backend

# Start stopped container
docker start eskimi-backend

# Remove container
docker rm eskimi-backend

# Remove image
docker rmi eskimi-backend-assignment:latest

# Access container shell (for debugging)
docker exec -it eskimi-backend sh
```

---

## Docker Compose Commands

```bash
# Start application
docker-compose up -d

# View logs
docker-compose logs -f

# Stop application
docker-compose down

# Rebuild and start
docker-compose up -d --build

# View container status
docker-compose ps
```

---

## Troubleshooting

### Port 8080 Already in Use
```bash
# Find and kill the process using port 8080
# On Linux/Mac:
lsof -ti:8080 | xargs kill -9

# On Windows:
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Or use a different port:
docker run -d --name eskimi-backend -p 9090:8080 eskimi-backend-assignment:latest
```

### Application Not Starting
```bash
# Check logs
docker logs eskimi-backend

# Check if container is running
docker ps -a

# Restart container
docker restart eskimi-backend
```

### Tests Failing During Build
```bash
# Run tests locally to see detailed errors
mvn test

# Check specific test
mvn test -Dtest=DaysCalculatorServiceTest
```

---

## Technology Stack

- **Java 17** - Programming Language
- **Spring Boot 3.2.0** - Application Framework
- **Maven** - Build Tool
- **JUnit 5** - Testing Framework
- **Mockito** - Mocking Framework
- **Docker** - Containerization
- **Open-Meteo API** - Weather Data Source

---