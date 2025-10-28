@echo off
REM ============================================================================
REM BROWN-DOGE WORK SHOWCASE - DETAILED IMPLEMENTATION DEMO
REM ============================================================================

color 0B
cls

echo.
echo ================================================================================
echo   BROWN-DOGE (ANDREW BROWN) - DETAILED WORK SHOWCASE
echo ================================================================================
echo.
echo   This demo will show:
echo     1. Actual code implementations
echo     2. OAuth2 login pages (HTML templates)
echo     3. Test code examples
echo     4. Configuration files
echo     5. Live demonstrations
echo.
pause

REM ============================================================================
REM DEMO 1: OAUTH2 LOGIN PAGES
REM ============================================================================
cls
echo ================================================================================
echo   DEMO 1: OAUTH2 LOGIN PAGES - IMPLEMENTATION
echo ================================================================================
echo.
echo   Andrew Brown created OAuth2 login pages and templates:
echo.

if exist "src\main\resources\templates\login.html" (
    echo   === LOGIN.HTML (OAuth2 Login Page) ===
    echo   File: src\main\resources\templates\login.html
    echo   ========================================
    type "src\main\resources\templates\login.html"
    echo.
    echo   ========================================
    echo.
) else (
    echo   [Note: Login template may be in different location]
    echo.
)

pause

if exist "src\main\resources\templates\home.html" (
    cls
    echo   === HOME.HTML (Landing Page) ===
    echo   File: src\main\resources\templates\home.html
    echo   ========================================
    type "src\main\resources\templates\home.html"
    echo.
    echo   ========================================
    echo.
)

pause

if exist "src\main\resources\templates\dashboard.html" (
    cls
    echo   === DASHBOARD.HTML (User Dashboard) ===
    echo   File: src\main\resources\templates\dashboard.html
    echo   ========================================
    type "src\main\resources\templates\dashboard.html"
    echo.
    echo   ========================================
    echo.
)

pause

REM ============================================================================
REM DEMO 2: OAUTH2 CONTROLLER IMPLEMENTATION
REM ============================================================================
cls
echo ================================================================================
echo   DEMO 2: OAUTH2 AUTHENTICATION CONTROLLER
echo ================================================================================
echo.
echo   Andrew Brown implemented the AuthController for OAuth2:
echo.

if exist "src\main\groovy\com\example\demo\controller\AuthController.groovy" (
    echo   === AUTHCONTROLLER.GROOVY ===
    echo   File: src\main\groovy\com\example\demo\controller\AuthController.groovy
    echo   ========================================
    type "src\main\groovy\com\example\demo\controller\AuthController.groovy"
    echo.
    echo   ========================================
    echo.
    echo   KEY FEATURES:
    echo     - OAuth2 user endpoint (/api/auth/user)
    echo     - User authentication handling
    echo     - OAuth2User principal integration
    echo.
) else (
    echo   [AuthController not found in expected location]
)

pause

REM ============================================================================
REM DEMO 3: SECURITY CONFIGURATION
REM ============================================================================
cls
echo ================================================================================
echo   DEMO 3: SECURITY CONFIGURATION IMPLEMENTATION
echo ================================================================================
echo.

if exist "src\main\groovy\com\example\demo\config\SecurityConfig.groovy" (
    echo   === SECURITYCONFIG.GROOVY ===
    echo   File: src\main\groovy\com\example\demo\config\SecurityConfig.groovy
    echo   ========================================
    type "src\main\groovy\com\example\demo\config\SecurityConfig.groovy"
    echo.
    echo   ========================================
    echo.
    echo   KEY FEATURES:
    echo     - OAuth2 login configuration
    echo     - CORS settings for cross-origin requests
    echo     - CSRF protection
    echo     - Security filter chain
    echo     - Login/logout configuration
    echo.
)

pause

REM ============================================================================
REM DEMO 4: JWT IMPLEMENTATION - FULL CODE
REM ============================================================================
cls
echo ================================================================================
echo   DEMO 4: JWT TOKEN PROVIDER - COMPLETE IMPLEMENTATION
echo ================================================================================
echo.

if exist "src\main\groovy\com\example\demo\security\JwtTokenProvider.java" (
    echo   === JWTTOKENPROVIDER.JAVA ===
    echo   File: src\main\groovy\com\example\demo\security\JwtTokenProvider.java
    echo   ========================================
    type "src\main\groovy\com\example\demo\security\JwtTokenProvider.java"
    echo.
    echo   ========================================
    echo.
    echo   IMPLEMENTATION HIGHLIGHTS:
    echo     - generateToken(String username) - Creates JWT token
    echo     - validateToken(String token) - Validates token signature
    echo     - getUsernameFromToken(String token) - Extracts username
    echo     - Uses HS512 signature algorithm
    echo     - Configurable expiration time
    echo.
)

pause

cls
if exist "src\main\groovy\com\example\demo\security\JwtAuthenticationFilter.java" (
    echo   === JWTAUTHENTICATIONFILTER.JAVA ===
    echo   File: src\main\groovy\com\example\demo\security\JwtAuthenticationFilter.java
    echo   ========================================
    type "src\main\groovy\com\example\demo\security\JwtAuthenticationFilter.java"
    echo.
    echo   ========================================
    echo.
    echo   IMPLEMENTATION HIGHLIGHTS:
    echo     - Filters incoming requests for JWT tokens
    echo     - Validates tokens on each request
    echo     - Sets authentication in SecurityContext
    echo     - Integrates with Spring Security
    echo.
)

pause

REM ============================================================================
REM DEMO 5: TEST IMPLEMENTATIONS
REM ============================================================================
cls
echo ================================================================================
echo   DEMO 5: TEST IMPLEMENTATIONS - ACTUAL TEST CODE
echo ================================================================================
echo.
echo   Andrew Brown wrote comprehensive tests. Let's view the actual code:
echo.

if exist "src\test\groovy\com\example\demo\model\GameTest.groovy" (
    echo   === GAMETEST.GROOVY (162 lines) ===
    echo   File: src\test\groovy\com\example\demo\model\GameTest.groovy
    echo   ========================================
    type "src\test\groovy\com\example\demo\model\GameTest.groovy"
    echo.
    echo   ========================================
    echo.
)

pause

cls
if exist "src\test\groovy\com\example\demo\service\GameServiceTest.groovy" (
    echo   === GAMESERVICETEST.GROOVY (215 lines) ===
    echo   File: src\test\groovy\com\example\demo\service\GameServiceTest.groovy
    echo   ========================================
    type "src\test\groovy\com\example\demo\service\GameServiceTest.groovy"
    echo.
    echo   ========================================
    echo.
)

pause

REM ============================================================================
REM DEMO 6: CONFIGURATION FILES
REM ============================================================================
cls
echo ================================================================================
echo   DEMO 6: OAUTH2 CONFIGURATION - APPLICATION.PROPERTIES
echo ================================================================================
echo.

if exist "src\main\resources\application.properties" (
    echo   === APPLICATION.PROPERTIES ===
    echo   File: src\main\resources\application.properties
    echo   ========================================
    type "src\main\resources\application.properties"
    echo.
    echo   ========================================
    echo.
    echo   CONFIGURATION HIGHLIGHTS:
    echo     - Google OAuth2 client configuration
    echo     - GitHub OAuth2 client configuration
    echo     - JWT secret and expiration settings
    echo     - Database configuration
    echo     - Server port and settings
    echo.
)

pause

REM ============================================================================
REM DEMO 7: ENVIRONMENT SETUP
REM ============================================================================
cls
echo ================================================================================
echo   DEMO 7: ENVIRONMENT CONFIGURATION - .ENV.EXAMPLE
echo ================================================================================
echo.

if exist ".env.example" (
    echo   === .ENV.EXAMPLE ===
    echo   File: .env.example
    echo   ========================================
    type ".env.example"
    echo.
    echo   ========================================
    echo.
    echo   Andrew Brown set up secure environment variable management
    echo   for OAuth2 credentials and JWT secrets.
    echo.
)

pause

REM ============================================================================
REM DEMO 8: RUN TESTS LIVE
REM ============================================================================
cls
echo ================================================================================
echo   DEMO 8: LIVE TEST EXECUTION
echo ================================================================================
echo.
echo   Would you like to run Andrew Brown's tests live?
echo.
set /p runtests="Run tests now? (y/n): "

if /i "%runtests%"=="y" (
    echo.
    echo   Executing test suite...
    echo   ================================================================================
    echo.
    call gradlew.bat test --tests "*Test" --console=plain
    echo.
    echo   ================================================================================
    echo.
    echo   Opening test report...
    start build\reports\tests\test\index.html
    echo.
)

pause

REM ============================================================================
REM DEMO 9: OPEN OAUTH2 PAGES IN BROWSER
REM ============================================================================
cls
echo ================================================================================
echo   DEMO 9: VIEW OAUTH2 PAGES IN BROWSER
echo ================================================================================
echo.
echo   Andrew Brown created HTML templates for OAuth2 login.
echo   Would you like to open them in your browser?
echo.
set /p openpages="Open HTML pages? (y/n): "

if /i "%openpages%"=="y" (
    if exist "src\main\resources\templates\login.html" (
        echo   Opening login.html...
        start src\main\resources\templates\login.html
    )
    if exist "src\main\resources\templates\home.html" (
        echo   Opening home.html...
        start src\main\resources\templates\home.html
    )
    if exist "src\main\resources\templates\dashboard.html" (
        echo   Opening dashboard.html...
        start src\main\resources\templates\dashboard.html
    )
    if exist "src\main\resources\templates\profile.html" (
        echo   Opening profile.html...
        start src\main\resources\templates\profile.html
    )
)

pause

REM ============================================================================
REM DEMO 10: DOCUMENTATION CREATED
REM ============================================================================
cls
echo ================================================================================
echo   DEMO 10: DOCUMENTATION FILES
echo ================================================================================
echo.
echo   Andrew Brown created 7 comprehensive documentation files.
echo   Select one to view:
echo.
echo   1. OAUTH2_SETUP.md
echo   2. DATABASE_SETUP.md
echo   3. DEPLOY_TO_HEROKU.md
echo   4. SIMPLE_OAUTH_SETUP.md
echo   5. POSTMAN_OAUTH_SETUP.md
echo   6. View all documentation
echo   7. Skip
echo.
set /p docchoice="Enter choice (1-7): "

if "%docchoice%"=="1" start docs\OAUTH2_SETUP.md
if "%docchoice%"=="2" start docs\DATABASE_SETUP.md
if "%docchoice%"=="3" start docs\DEPLOY_TO_HEROKU.md
if "%docchoice%"=="4" start docs\SIMPLE_OAUTH_SETUP.md
if "%docchoice%"=="5" start docs\POSTMAN_OAUTH_SETUP.md
if "%docchoice%"=="6" (
    start docs\OAUTH2_SETUP.md
    start docs\DATABASE_SETUP.md
    start docs\DEPLOY_TO_HEROKU.md
    start docs\SIMPLE_OAUTH_SETUP.md
    start docs\POSTMAN_OAUTH_SETUP.md
    start docs\OAUTH_REDIRECT_SETUP.md
    start docs\IP_ADDRESS_OAUTH_SETUP.md
)

pause

REM ============================================================================
REM DEMO 11: GIT CONTRIBUTIONS
REM ============================================================================
cls
echo ================================================================================
echo   DEMO 11: ANDREW BROWN'S GIT CONTRIBUTIONS
echo ================================================================================
echo.
echo   Viewing detailed commit history with code statistics:
echo.

git log --author="Andrew Brown" --stat --oneline | more

pause

REM ============================================================================
REM DEMO 12: START THE APPLICATION
REM ============================================================================
cls
echo ================================================================================
echo   DEMO 12: RUN THE APPLICATION LIVE
echo ================================================================================
echo.
echo   Would you like to start the application to see OAuth2 in action?
echo.
echo   Once started, you can:
echo     - Visit http://localhost:8080
echo     - Click "Login" to try OAuth2
echo     - Test the API endpoints
echo     - See the authentication flow
echo.
set /p runapp="Start application? (y/n): "

if /i "%runapp%"=="y" (
    echo.
    echo   Starting Spring Boot application...
    echo   ================================================================================
    echo.
    echo   Access the application at: http://localhost:8080
    echo   Press Ctrl+C to stop the application
    echo.
    pause
    call gradlew.bat bootRun
)

REM ============================================================================
REM SUMMARY
REM ============================================================================
cls
echo ================================================================================
echo   ANDREW BROWN'S WORK SUMMARY
echo ================================================================================
echo.
echo   IMPLEMENTATIONS SHOWN:
echo   ========================================
echo.
echo   1. OAuth2 Login Pages
echo      - login.html, home.html, dashboard.html, profile.html
echo.
echo   2. OAuth2 Authentication
echo      - AuthController.groovy (OAuth2 endpoints)
echo      - SecurityConfig.groovy (Security configuration)
echo      - application.properties (OAuth2 config)
echo.
echo   3. JWT Token System
echo      - JwtTokenProvider.java (Token generation/validation)
echo      - JwtAuthenticationFilter.java (Request filtering)
echo.
echo   4. Comprehensive Tests (55+ tests)
echo      - GameTest.groovy (162 lines)
echo      - TeamTest.groovy (227 lines)
echo      - GameServiceTest.groovy (215 lines)
echo      - GameControllerTest.groovy (205 lines)
echo      - Repository integration tests
echo.
echo   5. Documentation (7 guides)
echo      - Complete OAuth2 setup guides
echo      - Database configuration
echo      - Deployment instructions
echo.
echo   6. Environment Configuration
echo      - .env.example (Secure credentials)
echo      - Multi-profile setup
echo.
echo   TOTAL IMPACT:
echo   ========================================
echo   - 52 commits (Most active contributor)
echo   - ~800 lines of test code
echo   - 7 documentation files
echo   - Complete authentication system
echo   - Production-ready deployment
echo.
echo ================================================================================
echo.
pause
