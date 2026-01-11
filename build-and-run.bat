@echo off
REM Build and Run Script for Eskimi Backend Assignment (Windows)
REM This script provides an easy way to build and run the application on Windows

setlocal enabledelayedexpansion

REM Configuration
set IMAGE_NAME=eskimi-backend-assignment
set CONTAINER_NAME=eskimi-backend
set PORT=8080

echo =====================================
echo   Eskimi Backend Assignment
echo =====================================
echo.

REM Check if Docker is installed
where docker >nul 2>nul
if %errorlevel% neq 0 (
    echo [ERROR] Docker is not installed. Please install Docker Desktop first.
    echo Visit: https://docs.docker.com/desktop/install/windows-install/
    pause
    exit /b 1
)
echo [OK] Docker is installed

REM Stop existing container if running
docker ps -q -f name=%CONTAINER_NAME% >nul 2>nul
if %errorlevel% equ 0 (
    echo [INFO] Stopping existing container...
    docker stop %CONTAINER_NAME% >nul 2>nul
    docker rm %CONTAINER_NAME% >nul 2>nul
    echo [OK] Existing container removed
)

REM Build Docker image
echo [INFO] Building Docker image (this may take a few minutes)...
echo [INFO] Running tests as part of the build process...
docker build -t %IMAGE_NAME%:latest .
if %errorlevel% neq 0 (
    echo [ERROR] Build failed
    pause
    exit /b 1
)
echo [OK] Docker image built successfully
echo [OK] All tests passed

REM Run container
echo [INFO] Starting container...
docker run -d --name %CONTAINER_NAME% -p %PORT%:8080 %IMAGE_NAME%:latest >nul
if %errorlevel% neq 0 (
    echo [ERROR] Failed to start container
    pause
    exit /b 1
)
echo [OK] Container started

REM Wait for application to start
echo [INFO] Waiting for application to start...
timeout /t 5 /nobreak >nul

set "APP_READY=0"
for /L %%i in (1,1,30) do (
    curl -s http://localhost:%PORT%/actuator/health >nul 2>nul
    if !errorlevel! equ 0 (
        set "APP_READY=1"
        goto :app_started
    )
    timeout /t 2 /nobreak >nul
    echo|set /p=.
)

:app_started
echo.
if %APP_READY% equ 1 (
    echo [OK] Application is ready!
) else (
    echo [ERROR] Application failed to start within 60 seconds
    echo Check logs with: docker logs %CONTAINER_NAME%
    pause
    exit /b 1
)

echo.
echo =====================================
echo Application is running!
echo =====================================
echo.
echo API Base URL: http://localhost:%PORT%
echo.
echo Available endpoints:
echo   - POST /api/v1/dates/difference
echo   - POST /api/v1/number/number-to-words
echo   - POST /api/v1/weather/dhaka-stats
echo   - GET  /actuator/health
echo.
echo Useful commands:
echo   View logs:        docker logs -f %CONTAINER_NAME%
echo   Stop application: docker stop %CONTAINER_NAME%
echo   Start again:      docker start %CONTAINER_NAME%
echo   Remove container: docker rm -f %CONTAINER_NAME%
echo.
echo Test the API:
echo   curl http://localhost:%PORT%/actuator/health
echo.
echo =====================================
echo Example API calls:
echo =====================================
echo.
echo 1. Calculate days between dates:
echo    curl -X POST http://localhost:%PORT%/api/v1/dates/difference ^
echo      -H "Content-Type: application/json" ^
echo      -d "{\"startDate\":\"2024-01-01\",\"endDate\":\"2024-12-31\"}"
echo.
echo 2. Convert number to words:
echo    curl -X POST http://localhost:%PORT%/api/v1/number/number-to-words ^
echo      -H "Content-Type: application/json" ^
echo      -d "{\"number\":36.40}"
echo.
echo 3. Get temperature stats:
echo    curl -X POST http://localhost:%PORT%/api/v1/weather/dhaka-stats ^
echo      -H "Content-Type: application/json" ^
echo      -d "{\"startDate\":\"2026-01-01\",\"endDate\":\"2026-01-10\"}"
echo.
echo =====================================

pause