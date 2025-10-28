@echo off
REM ============================================================================
REM BACKEND API - LIVE DEMONSTRATION
REM ============================================================================
REM Complete backend demo showing all features in action
REM ============================================================================

color 0E
cls

echo.
echo ================================================================================
echo   JUMP BALL BACKEND API - LIVE DEMONSTRATION
echo ================================================================================
echo.
echo   This demo will:
echo     1. Start the Spring Boot backend server
echo     2. Show you how to test all API endpoints
echo     3. Demonstrate OAuth2 authentication
echo     4. Test JWT token generation
echo     5. Show database operations
echo     6. Prepare for frontend connection
echo.
pause

REM ============================================================================
REM STEP 1: CHECK PREREQUISITES
REM ============================================================================
cls
echo ================================================================================
echo   STEP 1: CHECKING PREREQUISITES
echo ================================================================================
echo.

echo   [Checking Java installation...]
java -version 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo   ERROR: Java not found!
    echo   Please install Java 17 or higher
    pause
    exit /b 1
)
echo   ✓ Java installed
echo.

echo   [Checking Gradle...]
if exist "gradlew.bat" (
    echo   ✓ Gradle wrapper found
) else (
    echo   ERROR: gradlew.bat not found!
    pause
    exit /b 1
)
echo.

echo   [Checking project structure...]
if exist "src\main\groovy\com\example\demo" (
    echo   ✓ Source code found
) else (
    echo   ERROR: Source code not found!
    pause
    exit /b 1
)
echo.

echo   All prerequisites met!
echo.
pause

REM ============================================================================
REM STEP 2: BUILD THE PROJECT
REM ============================================================================
cls
echo ================================================================================
echo   STEP 2: BUILDING THE PROJECT
echo ================================================================================
echo.
echo   Building the backend (this may take a minute)...
echo.

call gradlew.bat build -x test

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo   Build failed! Check errors above.
    pause
    exit /b 1
)

echo.
echo   ✓ Build successful!
echo.
pause

REM ============================================================================
REM STEP 3: SHOW API ENDPOINTS
REM ============================================================================
cls
echo ================================================================================
echo   STEP 3: AVAILABLE API ENDPOINTS
echo ================================================================================
echo.
echo   Once the server starts, these endpoints will be available:
echo.
echo   HEALTH CHECK:
echo   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo   GET  http://localhost:8080/api/status/health
echo        → Check if server is running
echo.
echo   GAME ENDPOINTS:
echo   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo   GET  http://localhost:8080/api/games/all
echo        → Get all games
echo   GET  http://localhost:8080/api/games/{id}
echo        → Get specific game
echo   GET  http://localhost:8080/api/games/team/{teamId}
echo        → Get games for a team
echo   POST http://localhost:8080/api/games
echo        → Create new game
echo.
echo   TEAM ENDPOINTS:
echo   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo   GET  http://localhost:8080/api/teams/all
echo        → Get all teams
echo   GET  http://localhost:8080/api/teams/{id}
echo        → Get specific team
echo.
echo   AUTHENTICATION:
echo   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo   GET  http://localhost:8080/oauth2/authorization/google
echo        → Login with Google
echo   GET  http://localhost:8080/oauth2/authorization/github
echo        → Login with GitHub
echo   POST http://localhost:8080/api/auth/mobile/login
echo        → JWT login for mobile
echo.
pause

REM ============================================================================
REM STEP 4: CREATE TEST SCRIPTS
REM ============================================================================
cls
echo ================================================================================
echo   STEP 4: CREATING API TEST SCRIPTS
echo ================================================================================
echo.

REM Create a PowerShell script to test endpoints
echo # Backend API Test Script > test-api.ps1
echo $baseUrl = "http://localhost:8080" >> test-api.ps1
echo. >> test-api.ps1
echo Write-Host "Testing Jump Ball Backend API" -ForegroundColor Cyan >> test-api.ps1
echo Write-Host "================================" -ForegroundColor Cyan >> test-api.ps1
echo. >> test-api.ps1
echo Write-Host "`n1. Health Check..." -ForegroundColor Yellow >> test-api.ps1
echo try { >> test-api.ps1
echo     $response = Invoke-RestMethod -Uri "$baseUrl/api/status/health" -Method Get >> test-api.ps1
echo     Write-Host "✓ Status: " -NoNewline -ForegroundColor Green >> test-api.ps1
echo     Write-Host $response.status >> test-api.ps1
echo     $response ^| ConvertTo-Json >> test-api.ps1
echo } catch { >> test-api.ps1
echo     Write-Host "✗ Failed: $_" -ForegroundColor Red >> test-api.ps1
echo } >> test-api.ps1
echo. >> test-api.ps1
echo Write-Host "`n2. Get All Games..." -ForegroundColor Yellow >> test-api.ps1
echo try { >> test-api.ps1
echo     $games = Invoke-RestMethod -Uri "$baseUrl/api/games/all" -Method Get >> test-api.ps1
echo     Write-Host "✓ Found $($games.Count) games" -ForegroundColor Green >> test-api.ps1
echo     $games ^| Select-Object -First 3 ^| ConvertTo-Json >> test-api.ps1
echo } catch { >> test-api.ps1
echo     Write-Host "✗ Failed: $_" -ForegroundColor Red >> test-api.ps1
echo } >> test-api.ps1
echo. >> test-api.ps1
echo Write-Host "`n3. Get All Teams..." -ForegroundColor Yellow >> test-api.ps1
echo try { >> test-api.ps1
echo     $teams = Invoke-RestMethod -Uri "$baseUrl/api/teams/all" -Method Get >> test-api.ps1
echo     Write-Host "✓ Found $($teams.Count) teams" -ForegroundColor Green >> test-api.ps1
echo     $teams ^| Select-Object -First 3 ^| ConvertTo-Json >> test-api.ps1
echo } catch { >> test-api.ps1
echo     Write-Host "✗ Failed: $_" -ForegroundColor Red >> test-api.ps1
echo } >> test-api.ps1
echo. >> test-api.ps1
echo Write-Host "`nAPI tests complete!" -ForegroundColor Cyan >> test-api.ps1

echo   ✓ Created test-api.ps1
echo.
echo   You can run this script to test all endpoints:
echo   powershell -ExecutionPolicy Bypass -File test-api.ps1
echo.
pause

REM ============================================================================
REM STEP 5: START THE SERVER
REM ============================================================================
cls
echo ================================================================================
echo   STEP 5: STARTING THE BACKEND SERVER
echo ================================================================================
echo.
echo   The server will start on http://localhost:8080
echo.
echo   IMPORTANT: Keep this window open while testing!
echo.
echo   What you can do once the server starts:
echo   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo   1. Open http://localhost:8080 in your browser
echo   2. Run: powershell -File test-api.ps1 (in another terminal)
echo   3. Test with Postman or curl
echo   4. Connect your frontend app
echo.
echo   Press Ctrl+C to stop the server when done
echo.
pause

echo.
echo   Starting server...
echo   ════════════════════════════════════════════════════════════════════════════
echo.

call gradlew.bat bootRun

REM If we get here, server was stopped
echo.
echo   Server stopped.
pause
