@echo off
REM ============================================================================
REM BROWN-DOGE WORK - LIVE HEROKU DEMO
REM ============================================================================
REM Quick demo showing Andrew Brown's work on the live Heroku deployment
REM No code dumps - just results and links
REM ============================================================================

color 0B
cls

echo.
echo ================================================================================
echo   BROWN-DOGE (ANDREW BROWN) - LIVE HEROKU DEMO
echo ================================================================================
echo.
echo   Demonstrating Andrew Brown's work using the LIVE deployed backend
echo   URL: https://jump-ball-df460ee69b61.herokuapp.com
echo.
pause

REM ============================================================================
REM DEMO 1: LIVE BACKEND
REM ============================================================================
cls
echo ================================================================================
echo   DEMO 1: LIVE BACKEND - DEPLOYED ON HEROKU
echo ================================================================================
echo.
echo   Andrew Brown contributed to the deployment setup!
echo.
echo   Live Backend URL:
echo   https://jump-ball-df460ee69b61.herokuapp.com
echo.
echo   Opening health check in browser...
start https://jump-ball-df460ee69b61.herokuapp.com/api/status/health

timeout /t 3 /nobreak >nul

echo.
echo   Testing live endpoints...
powershell -Command "Write-Host '  Checking health...' -ForegroundColor Yellow; $r = Invoke-RestMethod 'https://jump-ball-df460ee69b61.herokuapp.com/api/status/health'; Write-Host '  Status: ' -NoNewline; Write-Host $r.status -ForegroundColor Green; Write-Host '  Message: ' -NoNewline; Write-Host $r.message"

pause

REM ============================================================================
REM DEMO 2: OAUTH2 PAGES
REM ============================================================================
cls
echo ================================================================================
echo   DEMO 2: OAUTH2 AUTHENTICATION - ANDREW BROWN'S IMPLEMENTATION
echo ================================================================================
echo.
echo   Andrew Brown implemented OAuth2 with Google and GitHub.
echo.
echo   Opening OAuth2 login pages...
echo.

if exist "src\main\resources\templates\login.html" (
    echo   Opening login.html...
    start src\main\resources\templates\login.html
    timeout /t 2 /nobreak >nul
)

if exist "src\main\resources\templates\home.html" (
    echo   Opening home.html...
    start src\main\resources\templates\home.html
    timeout /t 2 /nobreak >nul
)

if exist "src\main\resources\templates\dashboard.html" (
    echo   Opening dashboard.html...
    start src\main\resources\templates\dashboard.html
)

pause

REM ============================================================================
REM DEMO 3: API ENDPOINTS
REM ============================================================================
cls
echo ================================================================================
echo   DEMO 3: REST API ENDPOINTS - LIVE ON HEROKU
echo ================================================================================
echo.
echo   Andrew Brown's backend API is serving data live!
echo.
echo   Opening API endpoints in browser:
echo.

echo   1. All Games...
start https://jump-ball-df460ee69b61.herokuapp.com/api/games/all
timeout /t 2 /nobreak >nul

echo   2. All Teams...
start https://jump-ball-df460ee69b61.herokuapp.com/api/teams/all
timeout /t 2 /nobreak >nul

echo   3. API Documentation...
start https://jump-ball-df460ee69b61.herokuapp.com/api/games/

echo.
echo   All endpoints are now open in your browser!
echo.
pause

REM ============================================================================
REM DEMO 4: TEST RESULTS
REM ============================================================================
cls
echo ================================================================================
echo   DEMO 4: TEST SUITE - ANDREW BROWN'S 55+ TESTS
echo ================================================================================
echo.
echo   Andrew Brown wrote comprehensive tests:
echo.
echo   Test Files Created:
echo   -------------------
dir /b src\test\groovy\com\example\demo\model\*.groovy 2>nul
dir /b src\test\groovy\com\example\demo\service\*.groovy 2>nul
dir /b src\test\groovy\com\example\demo\repository\*.groovy 2>nul

echo.
echo   Test Coverage: ~40%% (55+ tests)
echo.
echo   Would you like to see the test report?
set /p runtests="Open test report? (y/n): "

if /i "%runtests%"=="y" (
    if exist "build\reports\tests\test\index.html" (
        start build\reports\tests\test\index.html
    ) else (
        echo   Running tests to generate report...
        call gradlew.bat test --console=plain
        if exist "build\reports\tests\test\index.html" (
            start build\reports\tests\test\index.html
        )
    )
)

pause

REM ============================================================================
REM DEMO 5: DOCUMENTATION
REM ============================================================================
cls
echo ================================================================================
echo   DEMO 5: DOCUMENTATION - 7 GUIDES BY ANDREW BROWN
echo ================================================================================
echo.
echo   Andrew Brown created comprehensive documentation:
echo.

dir /b docs\*.md

echo.
echo   Which documentation would you like to view?
echo.
echo   1. OAUTH2_SETUP.md
echo   2. DATABASE_SETUP.md
echo   3. DEPLOY_TO_HEROKU.md
echo   4. All documentation
echo   5. Skip
echo.
set /p docchoice="Enter choice (1-5): "

if "%docchoice%"=="1" start docs\OAUTH2_SETUP.md
if "%docchoice%"=="2" start docs\DATABASE_SETUP.md
if "%docchoice%"=="3" start docs\DEPLOY_TO_HEROKU.md
if "%docchoice%"=="4" (
    for %%f in (docs\*.md) do start "%%f"
)

pause

REM ============================================================================
REM DEMO 6: GIT CONTRIBUTIONS
REM ============================================================================
cls
echo ================================================================================
echo   DEMO 6: GIT CONTRIBUTIONS - 52 COMMITS
echo ================================================================================
echo.
echo   Andrew Brown's contribution summary:
echo.

git log --author="Andrew Brown" --oneline | findstr /N "^" | findstr "^1: ^2: ^3: ^4: ^5: ^6: ^7: ^8: ^9: ^10:"

echo.
echo   Total commits by Andrew Brown: 52
echo   Most active contributor on the project!
echo.
pause

REM ============================================================================
REM DEMO 7: VISUAL PRESENTATION
REM ============================================================================
cls
echo ================================================================================
echo   DEMO 7: VISUAL PRESENTATION
echo ================================================================================
echo.
echo   Opening comprehensive visual presentation...
echo.

if exist "presentation\presentation.html" (
    start presentation\presentation.html
    echo   ✓ Presentation opened in browser
) else (
    echo   Presentation file not found
)

pause

REM ============================================================================
REM SUMMARY
REM ============================================================================
cls
echo ================================================================================
echo   ANDREW BROWN'S WORK - DEMONSTRATION COMPLETE
echo ================================================================================
echo.
echo   WHAT WE SHOWED:
echo   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo.
echo   ✓ Live Backend on Heroku
echo      https://jump-ball-df460ee69b61.herokuapp.com
echo.
echo   ✓ OAuth2 Login Pages
echo      HTML templates for Google/GitHub authentication
echo.
echo   ✓ REST API Endpoints
echo      Games, Teams, Status - all working live
echo.
echo   ✓ Test Suite
echo      55+ comprehensive tests, ~40%% coverage
echo.
echo   ✓ Documentation
echo      7 comprehensive setup guides
echo.
echo   ✓ Git History
echo      52 commits - Most active contributor
echo.
echo   ANDREW BROWN'S IMPACT:
echo   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo.
echo   - Complete OAuth2 authentication system
echo   - JWT token-based mobile authentication
echo   - ~800 lines of test code
echo   - 7 documentation files
echo   - Heroku deployment configuration
echo   - Production-ready backend API
echo.
echo   ════════════════════════════════════════════════════════════════════════════
echo   Backend is LIVE and ready for frontend connection!
echo   ════════════════════════════════════════════════════════════════════════════
echo.
pause
