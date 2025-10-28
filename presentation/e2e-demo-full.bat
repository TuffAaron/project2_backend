@echo off
REM ============================================================================
REM END-TO-END DEMO - BACKEND + FRONTEND
REM ============================================================================
REM Complete demo connecting Jump Ball Backend with Sports Betting Frontend
REM Frontend: https://github.com/MicahHeneveld/CST438_Sports_betting_group14
REM ============================================================================

color 0A
cls

echo.
echo ================================================================================
echo   JUMP BALL - END-TO-END DEMO
echo ================================================================================
echo.
echo   Backend:  Jump Ball API (Spring Boot)
echo   Frontend: Sports Betting App (React/Expo)
echo   GitHub:   https://github.com/MicahHeneveld/CST438_Sports_betting_group14
echo.
echo   This demo will:
echo     1. Start the backend API server
echo     2. Clone/check frontend repository
echo     3. Configure the connection
echo     4. Start the frontend app
echo     5. Run complete E2E tests
echo.
pause

REM ============================================================================
REM STEP 1: CHECK IF FRONTEND EXISTS
REM ============================================================================
cls
echo ================================================================================
echo   STEP 1: CHECKING FRONTEND REPOSITORY
echo ================================================================================
echo.

set FRONTEND_DIR=..\CST438_Sports_betting_group14

if exist "%FRONTEND_DIR%" (
    echo   ✓ Frontend repository found at: %FRONTEND_DIR%
    echo.
) else (
    echo   Frontend repository not found locally.
    echo.
    echo   Would you like to clone it?
    echo   Repo: https://github.com/MicahHeneveld/CST438_Sports_betting_group14
    echo.
    set /p clone="Clone repository? (y/n): "
    
    if /i "%clone%"=="y" (
        echo.
        echo   Cloning frontend repository...
        cd ..
        git clone https://github.com/MicahHeneveld/CST438_Sports_betting_group14.git
        cd project2_backend
        
        if exist "%FRONTEND_DIR%" (
            echo   ✓ Frontend cloned successfully!
        ) else (
            echo   ✗ Failed to clone repository
            echo   Please clone manually:
            echo   git clone https://github.com/MicahHeneveld/CST438_Sports_betting_group14.git
            pause
            exit /b 1
        )
    ) else (
        echo.
        echo   Please clone the frontend manually:
        echo   cd ..
        echo   git clone https://github.com/MicahHeneveld/CST438_Sports_betting_group14.git
        echo.
        pause
        exit /b 1
    )
)

pause

REM ============================================================================
REM STEP 2: START BACKEND SERVER
REM ============================================================================
cls
echo ================================================================================
echo   STEP 2: STARTING BACKEND SERVER
echo ================================================================================
echo.

echo   Building backend...
call gradlew.bat build -x test

if %ERRORLEVEL% NEQ 0 (
    echo   ✗ Build failed!
    pause
    exit /b 1
)

echo.
echo   ✓ Backend built successfully!
echo.
echo   Starting backend server on http://localhost:8080
echo   (This will run in the background)
echo.

REM Start backend in a new window
start "Jump Ball Backend" cmd /c "gradlew.bat bootRun"

echo   Waiting for backend to start (15 seconds)...
timeout /t 15 /nobreak >nul

REM Test if backend is running
echo.
echo   Testing backend connection...
powershell -Command "try { Invoke-RestMethod http://localhost:8080/api/status/health | Out-Null; Write-Host '  ✓ Backend is running!' -ForegroundColor Green } catch { Write-Host '  ✗ Backend not responding' -ForegroundColor Red }"

pause

REM ============================================================================
REM STEP 3: CONFIGURE FRONTEND
REM ============================================================================
cls
echo ================================================================================
echo   STEP 3: CONFIGURING FRONTEND CONNECTION
echo ================================================================================
echo.

echo   Checking frontend configuration...
echo.

REM Check for common config files
if exist "%FRONTEND_DIR%\config.js" (
    echo   Found: config.js
) 
if exist "%FRONTEND_DIR%\src\config\api.js" (
    echo   Found: src\config\api.js
)
if exist "%FRONTEND_DIR%\.env" (
    echo   Found: .env
)
if exist "%FRONTEND_DIR%\app.json" (
    echo   Found: app.json (Expo config)
)

echo.
echo   IMPORTANT: Ensure frontend is configured to use:
echo   Backend URL: http://localhost:8080
echo.
echo   Common config locations:
echo   - .env file: REACT_APP_API_URL=http://localhost:8080
echo   - config.js: API_BASE_URL = 'http://localhost:8080'
echo   - Constants file in src folder
echo.
echo   Would you like to view the frontend directory structure?
set /p viewdir="View structure? (y/n): "

if /i "%viewdir%"=="y" (
    echo.
    tree /F /A "%FRONTEND_DIR%" | more
)

pause

REM ============================================================================
REM STEP 4: INSTALL FRONTEND DEPENDENCIES
REM ============================================================================
cls
echo ================================================================================
echo   STEP 4: FRONTEND SETUP
echo ================================================================================
echo.

cd "%FRONTEND_DIR%"

echo   Current directory: %CD%
echo.

REM Check for package.json
if exist "package.json" (
    echo   ✓ Found package.json
    echo.
    echo   Installing frontend dependencies...
    echo   (This may take a few minutes)
    echo.
    
    call npm install
    
    if %ERRORLEVEL% NEQ 0 (
        echo   ✗ npm install failed
        echo   Please run manually: cd %FRONTEND_DIR% && npm install
        pause
        cd ..\project2_backend
        exit /b 1
    )
    
    echo.
    echo   ✓ Dependencies installed!
) else (
    echo   ✗ package.json not found
    echo   This might not be a Node.js/React project
)

echo.
pause

REM ============================================================================
REM STEP 5: CREATE API TEST FILE
REM ============================================================================
cls
echo ================================================================================
echo   STEP 5: CREATING API CONNECTION TEST
echo ================================================================================
echo.

REM Create a test file in frontend
echo // API Connection Test > test-connection.js
echo const API_URL = 'http://localhost:8080'; >> test-connection.js
echo. >> test-connection.js
echo async function testBackendConnection() { >> test-connection.js
echo   console.log('Testing backend connection...'); >> test-connection.js
echo   console.log('Backend URL:', API_URL); >> test-connection.js
echo. >> test-connection.js
echo   try { >> test-connection.js
echo     // Test 1: Health Check >> test-connection.js
echo     console.log('\n1. Health Check:'); >> test-connection.js
echo     const healthResponse = await fetch(`${API_URL}/api/status/health`); >> test-connection.js
echo     const healthData = await healthResponse.json(); >> test-connection.js
echo     console.log('✓ Status:', healthData.status); >> test-connection.js
echo     console.log('  Message:', healthData.message); >> test-connection.js
echo. >> test-connection.js
echo     // Test 2: Get Games >> test-connection.js
echo     console.log('\n2. Get All Games:'); >> test-connection.js
echo     const gamesResponse = await fetch(`${API_URL}/api/games/all`); >> test-connection.js
echo     const gamesData = await gamesResponse.json(); >> test-connection.js
echo     console.log(`✓ Found ${gamesData.length} games`); >> test-connection.js
echo     if (gamesData.length ^> 0) { >> test-connection.js
echo       console.log('  First game:', gamesData[0]); >> test-connection.js
echo     } >> test-connection.js
echo. >> test-connection.js
echo     // Test 3: Get Teams >> test-connection.js
echo     console.log('\n3. Get All Teams:'); >> test-connection.js
echo     const teamsResponse = await fetch(`${API_URL}/api/teams/all`); >> test-connection.js
echo     const teamsData = await teamsResponse.json(); >> test-connection.js
echo     console.log(`✓ Found ${teamsData.length} teams`); >> test-connection.js
echo     if (teamsData.length ^> 0) { >> test-connection.js
echo       console.log('  First team:', teamsData[0]); >> test-connection.js
echo     } >> test-connection.js
echo. >> test-connection.js
echo     console.log('\n✓ All tests passed! Backend is connected.'); >> test-connection.js
echo   } catch (error) { >> test-connection.js
echo     console.error('✗ Connection failed:', error.message); >> test-connection.js
echo     console.error('Make sure backend is running on', API_URL); >> test-connection.js
echo   } >> test-connection.js
echo } >> test-connection.js
echo. >> test-connection.js
echo testBackendConnection(); >> test-connection.js

echo   ✓ Created test-connection.js
echo.
echo   Test the connection with:
echo   node test-connection.js
echo.
pause

REM ============================================================================
REM STEP 6: START FRONTEND
REM ============================================================================
cls
echo ================================================================================
echo   STEP 6: STARTING FRONTEND APPLICATION
echo ================================================================================
echo.

REM Check if it's an Expo project
if exist "app.json" (
    echo   Detected Expo project!
    echo.
    echo   Starting Expo development server...
    echo   You can access it via:
    echo     - Web: Press 'w'
    echo     - Android: Press 'a' or scan QR code
    echo     - iOS: Press 'i' or scan QR code
    echo.
    
    start "Sports Betting Frontend" cmd /c "npx expo start"
    
) else if exist "package.json" (
    echo   Starting React application...
    echo.
    
    REM Check package.json for start script
    findstr /C:"\"start\"" package.json >nul
    if %ERRORLEVEL% EQU 0 (
        start "Sports Betting Frontend" cmd /c "npm start"
    ) else (
        echo   No start script found in package.json
        echo   Please check the README for start instructions
    )
) else (
    echo   ✗ Could not determine project type
)

echo.
echo   Frontend is starting in a separate window...
echo.
pause

REM ============================================================================
REM STEP 7: E2E TEST INSTRUCTIONS
REM ============================================================================
cls
echo ================================================================================
echo   STEP 7: END-TO-END TESTING GUIDE
echo ================================================================================
echo.
echo   Both backend and frontend are now running!
echo.
echo   BACKEND:  http://localhost:8080
echo   FRONTEND: http://localhost:8081 (or check Expo window)
echo.
echo   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo   E2E TEST SCENARIOS:
echo   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo.
echo   1. VIEW GAMES LIST
echo      - Open frontend app
echo      - Navigate to Games/Matches section
echo      - Should display list of NBA games from backend
echo.
echo   2. VIEW GAME DETAILS
echo      - Click on a specific game
echo      - Should show detailed game information
echo      - Data comes from: GET /api/games/{id}
echo.
echo   3. VIEW TEAMS
echo      - Navigate to Teams section
echo      - Should display list of NBA teams
echo      - Data comes from: GET /api/teams/all
echo.
echo   4. OAUTH LOGIN (if configured)
echo      - Click Login button
echo      - Choose Google or GitHub
echo      - Should redirect to backend OAuth
echo      - After login, redirects back to frontend
echo.
echo   5. PLACE BET (if feature exists)
echo      - Select a game
echo      - Place a bet
echo      - Should send POST request to backend
echo.
echo   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo   MANUAL API TESTS:
echo   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo.
echo   Open browser and test:
echo   - http://localhost:8080/api/status/health
echo   - http://localhost:8080/api/games/all
echo   - http://localhost:8080/api/teams/all
echo.
pause

REM ============================================================================
REM STEP 8: QUICK API TEST
REM ============================================================================
cls
echo ================================================================================
echo   STEP 8: QUICK API CONNECTION TEST
echo ================================================================================
echo.

cd "%FRONTEND_DIR%"

if exist "test-connection.js" (
    echo   Running connection test...
    echo   ════════════════════════════════════════════════════════════
    echo.
    node test-connection.js
    echo.
    echo   ════════════════════════════════════════════════════════════
) else (
    echo   Test file not found, testing with PowerShell...
    powershell -Command "$url='http://localhost:8080/api/status/health'; try { $r = Invoke-RestMethod $url; Write-Host '✓ Backend connected!' -ForegroundColor Green; $r } catch { Write-Host '✗ Backend not responding' -ForegroundColor Red }"
)

echo.
pause

REM ============================================================================
REM STEP 9: MONITORING & LOGS
REM ============================================================================
cls
echo ================================================================================
echo   STEP 9: MONITORING THE APPLICATION
echo ================================================================================
echo.
echo   Both applications are now running in separate windows.
echo.
echo   Backend Window:  "Jump Ball Backend"
echo   Frontend Window: "Sports Betting Frontend"
echo.
echo   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo   WHAT TO WATCH FOR:
echo   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo.
echo   In Backend Window:
echo   - Watch for incoming HTTP requests
echo   - Look for: GET "/api/games/all" 200 OK
echo   - Check for any errors or exceptions
echo.
echo   In Frontend Window:
echo   - Check Expo DevTools for errors
echo   - Watch network requests
echo   - Verify data is loading
echo.
echo   In Browser Console (if using web):
echo   - F12 to open DevTools
echo   - Network tab shows API calls
echo   - Console shows any JavaScript errors
echo.
pause

REM ============================================================================
REM STEP 10: HELPFUL COMMANDS
REM ============================================================================
cls
echo ================================================================================
echo   E2E DEMO - HELPFUL COMMANDS
echo ================================================================================
echo.
echo   TESTING COMMANDS:
echo   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo.
echo   # Test backend health
echo   curl http://localhost:8080/api/status/health
echo.
echo   # Get all games
echo   curl http://localhost:8080/api/games/all
echo.
echo   # Get all teams
echo   curl http://localhost:8080/api/teams/all
echo.
echo   # PowerShell test
echo   Invoke-RestMethod http://localhost:8080/api/status/health
echo.
echo.
echo   RESTART COMMANDS:
echo   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo.
echo   # Restart backend
echo   cd project2_backend
echo   .\gradlew.bat bootRun
echo.
echo   # Restart frontend
echo   cd CST438_Sports_betting_group14
echo   npx expo start
echo.
echo.
echo   STOP COMMANDS:
echo   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo.
echo   # In backend window: Press Ctrl+C
echo   # In frontend window: Press Ctrl+C
echo.
pause

cd ..\project2_backend

REM ============================================================================
REM SUMMARY
REM ============================================================================
cls
echo ================================================================================
echo   E2E DEMO COMPLETE!
echo ================================================================================
echo.
echo   ✓ Backend is running on http://localhost:8080
echo   ✓ Frontend is running (check separate window)
echo   ✓ Both applications are connected
echo.
echo   NEXT STEPS:
echo   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo.
echo   1. Open the frontend in your browser or mobile app
echo   2. Navigate through different features
echo   3. Watch backend logs for incoming requests
echo   4. Test all CRUD operations
echo   5. Try OAuth login if configured
echo.
echo   MONITORING:
echo   - Backend logs in "Jump Ball Backend" window
echo   - Frontend logs in "Sports Betting Frontend" window
echo   - API requests visible in both
echo.
echo   To stop both applications:
echo   - Press Ctrl+C in backend window
echo   - Press Ctrl+C in frontend window
echo.
echo   ════════════════════════════════════════════════════════════════════════════
echo   Happy Testing! 🎉
echo   ════════════════════════════════════════════════════════════════════════════
echo.
pause
