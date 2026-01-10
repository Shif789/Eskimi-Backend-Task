# Quick Start Guide

## For Complete Beginners (No Java/Maven Required)

### Prerequisites
Only Docker is needed! Install from: https://docs.docker.com/get-docker/

### Steps

#### 1. Clone the Repository
```bash
git clone https://github.com/Shif789/Eskimi-Backend-Task.git
cd Eskimi-Backend-Task
```

#### 2. Make the Script Executable (Linux/Mac only)
```bash
chmod +x build-and-run.sh
```

#### 3. Run the Application

**On Linux/Mac:**
```bash
./build-and-run.sh
```

**On Windows (using PowerShell):**
```powershell
docker build -t eskimi-backend-assignment:latest .
docker run -d --name eskimi-backend -p 8080:8080 eskimi-backend-assignment:latest
```

#### 4. Test the Application
Wait 30-40 seconds for the app to start, then:

```bash
curl http://localhost:8080/actuator/health
```

Expected response:
```json
{"status":"UP"}
```

### Try the API

**Calculate Days:**
```bash
curl -X POST http://localhost:8080/api/v1/days/calculate \
  -H "Content-Type: application/json" \
  -d '{"startDate":"2024-01-01","endDate":"2024-12-31"}'
```

**Convert Number:**
```bash
curl -X POST http://localhost:8080/api/v1/convert \
  -H "Content-Type: application/json" \
  -d '{"number":36.40}'
```

**Temperature Stats:**
```bash
curl -X POST http://localhost:8080/api/v1/temperature/stats \
  -H "Content-Type: application/json" \
  -d '{"startDate":"2025-01-01","endDate":"2025-01-07"}'
```

### Stop the Application
```bash
docker stop eskimi-backend
```

---

## Alternative: Using Docker Compose

Even easier!

```bash
# Start
docker-compose up -d

# Stop
docker-compose down
```

That's it! 🎉