@echo off
REM ============================================================================
REM BROWN-DOGE (ANDREW BROWN) - WORK DEMONSTRATION
REM ============================================================================
REM Interactive demo of Andrew Brown's contributions to Jump Ball API
REM ============================================================================

color 0B
cls

echo.
echo ================================================================================
echo   BROWN-DOGE (ANDREW BROWN) - CONTRIBUTION DEMO
echo ================================================================================
echo.
echo   Contributor: Andrew Brown (GitHub: Brown-doge)
echo   Total Commits: 52 (Most Active Contributor)
echo   Repository: TuffAaron/project2_backend
echo.
echo   This demo will showcase:
echo     1. OAuth2 Authentication Implementation
echo     2. JWT Token System
echo     3. Test Suite (55+ tests)
echo     4. Documentation
echo     5. Live Code Demonstration
echo.
pause

cls
echo ================================================================================
echo   DEMO 1: OAUTH2 AUTHENTICATION SYSTEM
echo ================================================================================
echo.
echo   Andrew Brown implemented complete OAuth2 authentication:
echo.

echo   [Viewing SecurityConfig files...]
echo.
timeout /t 2 /nobreak >nul

if exist "src\main\groovy\com\example\demo\config\SecurityConfig.groovy" (
    echo   File: SecurityConfig.groovy
    echo   ----------------------------------------
    type "src\main\groovy\com\example\demo\config\SecurityConfig.groovy" | findstr /N "OAuth2" | more
    echo.
)

echo.
echo   OAuth2 Features Implemented:
echo     - Google OAuth2 integration
echo     - GitHub OAuth2 integration  
echo     - CORS configuration for cross-origin requests
echo     - CSRF protection
echo     - Session management
echo.
echo   Setup Scripts Created:
dir /b setup-oauth.* 2>nul
echo.
pause

cls
echo ================================================================================
echo   DEMO 2: JWT TOKEN SYSTEM
echo ================================================================================
echo.
echo   Andrew Brown built custom JWT authentication for mobile apps:
echo.

echo   [Viewing JwtTokenProvider.java...]
echo.
if exist "src\main\groovy\com\example\demo\security\JwtTokenProvider.java" (
    echo   File: JwtTokenProvider.java (56 lines)
    echo   ----------------------------------------
    type "src\main\groovy\com\example\demo\security\JwtTokenProvider.java"
    echo.
    echo   ----------------------------------------
)

echo.
echo   JWT Features:
echo     - Token generation with HS512 algorithm
echo     - Token validation
echo     - Username extraction from tokens
echo     - Configurable expiration
echo     - Secure key management
echo.
pause

cls
echo ================================================================================
echo   DEMO 3: TEST SUITE - 55+ TESTS
echo ================================================================================
echo.
echo   Andrew Brown wrote comprehensive tests achieving ~40%% code coverage:
echo.

echo   [Listing Test Files...]
echo.
dir /b src\test\groovy\com\example\demo\*.groovy 2>nul
dir /b src\test\groovy\com\example\demo\model\*.groovy 2>nul
dir /b src\test\groovy\com\example\demo\service\*.groovy 2>nul
dir /b src\test\groovy\com\example\demo\repository\*.groovy 2>nul

echo.
echo   Test Breakdown:
echo     - GameTest.groovy: 162 lines (Model testing)
echo     - TeamTest.groovy: 227 lines (Model testing)
echo     - GameServiceTest.groovy: 215 lines (Business logic)
echo     - GameControllerTest.groovy: 205 lines (REST API)
echo     - GameRepositoryTest.groovy (Integration tests)
echo     - TeamRepositoryTest.groovy (Integration tests)
echo.
echo   [Previewing GameTest.groovy...]
echo.

if exist "src\test\groovy\com\example\demo\model\GameTest.groovy" (
    type "src\test\groovy\com\example\demo\model\GameTest.groovy" | findstr /N "def test void should" | more
)

echo.
pause

cls
echo ================================================================================
echo   DEMO 4: RUNNING ACTUAL TESTS
echo ================================================================================
echo.
echo   Let's run Andrew Brown's tests to see them in action!
echo.
set /p runtests="Run tests now? (y/n): "

if /i "%runtests%"=="y" (
    echo.
    echo   Running Gradle tests...
    echo   ================================================================================
    echo.
    call gradlew.bat test --console=plain
    echo.
    echo   ================================================================================
    echo   Tests completed! Check results above.
    echo.
) else (
    echo.
    echo   Tests skipped. You can run them later with: .\gradlew.bat test
    echo.
)
pause

cls
echo ================================================================================
echo   DEMO 5: DOCUMENTATION BY ANDREW BROWN
echo ================================================================================
echo.
echo   Andrew Brown created 7 comprehensive documentation files:
echo.

dir /b *OAUTH*.md *DEPLOY*.md *DATABASE*.md 2>nul

echo.
echo   [Previewing OAUTH2_SETUP.md...]
echo.

if exist "OAUTH2_SETUP.md" (
    type "OAUTH2_SETUP.md" | more
)

echo.
pause

cls
echo ================================================================================
echo   DEMO 6: GIT HISTORY - ANDREW BROWN'S COMMITS
echo ================================================================================
echo.
echo   Viewing Andrew Brown's commit history:
echo.
echo   [Last 20 commits by Andrew Brown...]
echo.

git log --author="Andrew Brown" --oneline -20

echo.
echo   [Detailed contribution stats...]
echo.

git log --author="Andrew Brown" --shortstat --oneline | findstr "file changed insertion deletion"

echo.
pause

cls
echo ================================================================================
echo   DEMO 7: LIVE CODE WALKTHROUGH - OAuth2 Implementation
echo ================================================================================
echo.
echo   Let's walk through the OAuth2 implementation step by step:
echo.

echo   STEP 1: AuthController (OAuth2 endpoints)
echo   ----------------------------------------
echo.
if exist "src\main\groovy\com\example\demo\controller\AuthController.groovy" (
    type "src\main\groovy\com\example\demo\controller\AuthController.groovy"
)

echo.
pause

echo.
echo   STEP 2: Application Properties (OAuth2 configuration)
echo   ----------------------------------------
echo.
if exist "src\main\resources\application.properties" (
    type "src\main\resources\application.properties" | findstr /i "oauth2 spring.security google github client"
)

echo.
pause

cls
echo ================================================================================
echo   DEMO 8: LIVE CODE WALKTHROUGH - JWT Implementation  
echo ================================================================================
echo.
echo   JWT Authentication Filter:
echo   ----------------------------------------
echo.
if exist "src\main\groovy\com\example\demo\security\JwtAuthenticationFilter.java" (
    type "src\main\groovy\com\example\demo\security\JwtAuthenticationFilter.java"
)

echo.
pause

cls
echo ================================================================================
echo   DEMO 9: PROJECT STRUCTURE IMPROVEMENTS
echo ================================================================================
echo.
echo   Andrew Brown also fixed project structure issues:
echo.
echo   Key Improvements:
echo     - Removed nested project2_backend folder (commit: 45ecce9)
echo     - Fixed OAuth2 authentication errors
echo     - Resolved CSRF token issues
echo     - Configured local dev environment with H2 database
echo     - Set up Heroku deployment configuration
echo.
echo   [Viewing current clean structure...]
echo.
tree /F /A src 2>nul | more

echo.
pause

cls
echo ================================================================================
echo   DEMO 10: ENVIRONMENT CONFIGURATION
echo ================================================================================
echo.
echo   Andrew Brown set up comprehensive environment management:
echo.
echo   Configuration Files:
echo   ----------------------------------------
echo.

if exist ".env.example" (
    echo   .env.example (Template for environment variables)
    echo   ----------------------------------------
    type ".env.example"
    echo.
)

echo.
echo   Profile-based Configuration:
echo     - application.properties (Default/Dev)
echo     - application-prod.properties (Production)
echo     - application-test.properties (Testing)
echo     - application-postman.properties (API Testing)
echo.
pause

cls
echo ================================================================================
echo   DEMO 11: DEPLOYMENT SETUP
echo ================================================================================
echo.
echo   Heroku deployment configuration by Andrew Brown:
echo.

if exist "Procfile" (
    echo   Procfile:
    echo   ----------------------------------------
    type "Procfile"
    echo.
)

if exist "system.properties" (
    echo   system.properties:
    echo   ----------------------------------------
    type "system.properties"
    echo.
)

echo   Deployment Scripts:
echo   ----------------------------------------
dir /b *start*.bat *setup*.bat 2>nul

echo.
pause

cls
echo ================================================================================
echo   ANDREW BROWN'S CONTRIBUTION SUMMARY
echo ================================================================================
echo.
echo   CATEGORY: OAuth2 Authentication
echo   ----------------------------------------
echo     - Complete Google OAuth2 integration
echo     - Complete GitHub OAuth2 integration
echo     - SecurityConfig with CORS support
echo     - AuthController with OAuth2 endpoints
echo     - Session management and CSRF protection
echo     - Multiple setup scripts and documentation
echo.
echo   CATEGORY: JWT Authentication
echo   ----------------------------------------
echo     - JwtTokenProvider (56 lines)
echo     - JwtAuthenticationFilter
echo     - Mobile authentication endpoint
echo     - HS512 signature algorithm
echo     - Configurable token expiration
echo.
echo   CATEGORY: Testing (~800 lines of test code)
echo   ----------------------------------------
echo     - GameTest.groovy (162 lines)
echo     - TeamTest.groovy (227 lines)
echo     - GameServiceTest.groovy (215 lines)
echo     - GameControllerTest.groovy (205 lines)
echo     - GameRepositoryTest.groovy (Integration tests)
echo     - TeamRepositoryTest.groovy (Integration tests)
echo     - Total: 55+ tests with ~40%% code coverage
echo.
pause

cls
echo.
echo   CATEGORY: Documentation (7 comprehensive guides)
echo   ----------------------------------------
echo     - OAUTH2_SETUP.md
echo     - DATABASE_SETUP.md
echo     - DEPLOY_TO_HEROKU.md
echo     - SIMPLE_OAUTH_SETUP.md
echo     - IP_ADDRESS_OAUTH_SETUP.md
echo     - OAUTH_REDIRECT_SETUP.md
echo     - POSTMAN_OAUTH_SETUP.md
echo.
echo   CATEGORY: Development Environment
echo   ----------------------------------------
echo     - Local H2 database configuration
echo     - Multi-profile setup (dev/test/prod)
echo     - Environment variable management (.env files)
echo     - Heroku deployment optimization
echo     - Build configuration improvements
echo.
echo   CATEGORY: Bug Fixes and Optimization (10+ fixes)
echo   ----------------------------------------
echo     - Fixed OAuth2 authentication errors
echo     - Resolved CSRF token issues
echo     - Fixed project structure (removed nested folders)
echo     - Postman OAuth2 testing configuration
echo     - Session management improvements
echo     - Dashboard CSRF error fixes
echo     - Test configuration for local development
echo.
echo   IMPACT:
echo   ----------------------------------------
echo     - 52 Total Commits (Most active contributor)
echo     - ~800 lines of test code
echo     - 7 documentation files created
echo     - Complete authentication system (OAuth2 + JWT)
echo     - Production-ready deployment setup
echo.
pause

cls
echo ================================================================================
echo   INTERACTIVE DEMO OPTIONS
echo ================================================================================
echo.
echo   What would you like to explore?
echo.
echo     1. View a specific test file
echo     2. View OAuth2 configuration
echo     3. View JWT implementation
echo     4. View documentation files
echo     5. See full git log for Andrew Brown
echo     6. Run the application with OAuth2
echo     7. Exit demo
echo.
set /p choice="Enter choice (1-7): "

if "%choice%"=="1" goto viewtest
if "%choice%"=="2" goto viewoauth
if "%choice%"=="3" goto viewjwt
if "%choice%"=="4" goto viewdocs
if "%choice%"=="5" goto viewgit
if "%choice%"=="6" goto runapp
if "%choice%"=="7" goto end

:viewtest
cls
echo   Select test file to view:
echo     1. GameTest.groovy
echo     2. TeamTest.groovy
echo     3. GameServiceTest.groovy
echo     4. GameControllerTest.groovy
echo.
set /p testfile="Enter choice (1-4): "
if "%testfile%"=="1" type "src\test\groovy\com\example\demo\model\GameTest.groovy" | more
if "%testfile%"=="2" type "src\test\groovy\com\example\demo\model\TeamTest.groovy" | more
if "%testfile%"=="3" type "src\test\groovy\com\example\demo\service\GameServiceTest.groovy" | more
if "%testfile%"=="4" type "src\test\groovy\com\example\demo\controller\GameControllerTest.groovy" | more
pause
goto end

:viewoauth
cls
type "src\main\resources\application.properties" | findstr /i "oauth2 google github spring.security" | more
echo.
type "src\main\groovy\com\example\demo\controller\AuthController.groovy" 2>nul | more
pause
goto end

:viewjwt
cls
type "src\main\groovy\com\example\demo\security\JwtTokenProvider.java" 2>nul | more
echo.
type "src\main\groovy\com\example\demo\security\JwtAuthenticationFilter.java" 2>nul | more
pause
goto end

:viewdocs
cls
dir /b *.md
echo.
set /p docfile="Enter documentation filename to view: "
type "%docfile%" | more
pause
goto end

:viewgit
cls
git log --author="Andrew Brown" --stat | more
pause
goto end

:runapp
cls
echo.
echo   Starting application with OAuth2...
echo.
call gradlew.bat bootRun
goto end

:end
cls
echo ================================================================================
echo   DEMO COMPLETE
echo ================================================================================
echo.
echo   Andrew Brown's (Brown-doge) contributions showcased:
echo     - OAuth2 Authentication System
echo     - JWT Token Implementation
echo     - 55+ Comprehensive Tests
echo     - 7 Documentation Guides
echo     - Deployment Configuration
echo     - Bug Fixes and Optimizations
echo.
echo   Total Impact: 52 commits as the most active contributor
echo.
echo   Thank you for reviewing Andrew Brown's work!
echo.
echo ================================================================================
pause
