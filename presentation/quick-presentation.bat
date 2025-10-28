@echo off
REM ============================================================================
REM JUMP BALL API - QUICK PRESENTATION
REM ============================================================================

color 0A
cls

echo.
echo ================================================================================
echo   JUMP BALL API - PROJECT PRESENTATION
echo ================================================================================
echo.
echo   Project: NBA Game Management System
echo   Repository: TuffAaron/project2_backend
echo   Branch: presentation
echo.
echo ================================================================================
echo   1. PROJECT OVERVIEW
echo ================================================================================
echo.
echo   Technology Stack:
echo     - Spring Boot 3.2.5
echo     - Java 17 + Groovy
echo     - Gradle Build Tool
echo     - H2 / MySQL Database
echo     - Spring Security + OAuth2 + JWT
echo     - JUnit 5 + Spock Framework
echo.
echo   Key Features:
echo     - RESTful API for NBA game management
echo     - OAuth2 authentication (Google, GitHub)
echo     - JWT token-based mobile authentication
echo     - 55+ comprehensive tests (~40%% coverage)
echo     - CORS-enabled cross-origin requests
echo     - Heroku deployment ready
echo.
pause

cls
echo ================================================================================
echo   2. TEST SUITE - 55+ TESTS
echo ================================================================================
echo.
echo   Test Files:
echo     - GameTest.groovy (162 lines - Model tests)
echo     - TeamTest.groovy (227 lines - Model tests)
echo     - GameRepositoryTest.groovy (Repository tests)
echo     - TeamRepositoryTest.groovy (Repository tests)
echo     - GameServiceTest.groovy (215 lines - Business logic)
echo     - TeamServiceTest.groovy (Service tests)
echo     - GameControllerTest.groovy (205 lines - API tests)
echo.
echo   Test Coverage:
echo     - Model Layer: Unit tests for entities
echo     - Repository Layer: Data access integration tests
echo     - Service Layer: Business logic tests
echo     - Controller Layer: REST API tests
echo     - Security Layer: Authentication tests
echo.
echo   Metrics:
echo     - Total Tests: 55+
echo     - Code Coverage: ~40%%
echo     - Pass Rate: 100%%
echo.
pause

cls
echo ================================================================================
echo   3. AUTHENTICATION SYSTEMS
echo ================================================================================
echo.
echo   OAuth2 Implementation:
echo     - Google OAuth2 integration
echo     - GitHub OAuth2 integration
echo     - Secure redirect URIs
echo     - Session management
echo     - CSRF protection
echo.
echo   JWT Authentication:
echo     - Custom JwtTokenProvider (56 lines)
echo     - JwtAuthenticationFilter
echo     - HS512 signature algorithm
echo     - Configurable token expiration
echo     - Mobile app support
echo.
echo   Security Files:
echo     - src\main\groovy\com\example\demo\security\JwtTokenProvider.java
echo     - src\main\groovy\com\example\demo\security\JwtAuthenticationFilter.java
echo     - Multiple SecurityConfig implementations
echo.
pause

cls
echo ================================================================================
echo   4. REST API ENDPOINTS
echo ================================================================================
echo.
echo   GAME MANAGEMENT:
echo     GET    /api/games/              - API documentation
echo     GET    /api/games/test          - Test endpoint with auth
echo     GET    /api/games/all           - Get all games
echo     GET    /api/games/team/{id}     - Get games by team
echo     GET    /api/games/{id}          - Get specific game
echo     POST   /api/games               - Create new game
echo     PUT    /api/games/{id}          - Update entire game
echo     PATCH  /api/games/{id}          - Partial update
echo     DELETE /api/games/{id}          - Delete game
echo.
echo   TEAM MANAGEMENT:
echo     GET    /api/teams/all           - Get all teams
echo     GET    /api/teams/{id}          - Get specific team
echo     POST   /api/teams               - Create new team
echo     PUT/PATCH/DELETE                - Update/Delete operations
echo.
echo   AUTHENTICATION:
echo     GET    /oauth2/authorization/google  - Google OAuth2
echo     GET    /oauth2/authorization/github  - GitHub OAuth2
echo     POST   /api/auth/mobile/login        - JWT mobile login
echo.
pause

cls
echo ================================================================================
echo   5. TEAM CONTRIBUTIONS - GIT HISTORY
echo ================================================================================
echo.

git shortlog -sn --all

echo.
echo ================================================================================
echo   ANDREW BROWN (Brown-doge) - 52 COMMITS
echo ================================================================================
echo.
echo   KEY CONTRIBUTIONS:
echo.
echo   OAuth2 Implementation:
echo     - Complete Google and GitHub OAuth2 integration
echo     - Security configuration with CORS support
echo     - Multiple OAuth2 setup documentation guides
echo     - Automated setup scripts (setup-oauth.bat/sh)
echo.
echo   Test Suite Development (~800 lines):
echo     - Game and Team model unit tests (162 + 227 lines)
echo     - GameController REST API tests (205 lines)
echo     - GameService business logic tests (215 lines)
echo     - Repository integration tests (385 lines)
echo     - Achieved ~40%% code coverage with 55+ tests
echo.
echo   JWT Authentication System:
echo     - Custom JWT token provider implementation
echo     - Mobile authentication endpoints
echo     - Token validation and filtering
echo     - HS512 algorithm with configurable expiration
echo.
echo   Documentation (7 comprehensive guides):
echo     - OAUTH2_SETUP.md
echo     - DATABASE_SETUP.md
echo     - DEPLOY_TO_HEROKU.md
echo     - SIMPLE_OAUTH_SETUP.md
echo     - IP_ADDRESS_OAUTH_SETUP.md
echo     - OAUTH_REDIRECT_SETUP.md
echo     - POSTMAN_OAUTH_SETUP.md
echo.
pause

cls
echo ================================================================================
echo   5. TEAM CONTRIBUTIONS (continued)
echo ================================================================================
echo.
echo   Development and Deployment:
echo     - Local development environment setup
echo     - H2 database configuration for testing
echo     - Heroku deployment optimization
echo     - Environment variable management
echo     - Production profile configuration
echo.
echo   Bug Fixes and Optimization (10+ fixes):
echo     - Fixed OAuth2 authentication errors
echo     - Resolved CSRF token issues
echo     - Fixed project structure (removed nested folders)
echo     - Postman OAuth2 testing configuration
echo     - Session management improvements
echo     - Dashboard CSRF error fixes
echo.
echo   OTHER CONTRIBUTORS:
echo     - Micah Heneveld: 45 commits - Core API development
echo     - Janniel Tan: 13 commits - Feature contributions
echo     - Aaron Perez: 15 commits - Project management
echo.
pause

cls
echo ================================================================================
echo   6. PROJECT METRICS
echo ================================================================================
echo.
echo   Code Statistics:
echo     - Total Commits: 125+
echo     - Contributors: 4 main developers
echo     - Lines of Code: 5,000+
echo     - Test Coverage: ~40%% (55+ tests)
echo     - API Endpoints: 20+
echo     - Documentation Files: 10+
echo.
echo   File Breakdown:
echo     - Java/Groovy Files: 26+
echo     - Test Files: 7
echo     - Configuration Files: 5+
echo     - Documentation: 10+
echo     - Scripts: 5+
echo.
echo   Features Implemented:
echo     - Full CRUD REST API
echo     - Dual authentication (OAuth2 + JWT)
echo     - Database integration (H2/MySQL)
echo     - Comprehensive test suite
echo     - CORS configuration
echo     - Production deployment
echo     - Health monitoring
echo     - API documentation
echo.
pause

cls
echo ================================================================================
echo   7. HOW TO RUN THE PROJECT
echo ================================================================================
echo.
echo   Start the Application:
echo     .\gradlew.bat bootRun
echo.
echo   Run Tests:
echo     .\gradlew.bat test
echo.
echo   Build for Production:
echo     .\gradlew.bat build
echo.
echo   Access the API:
echo     http://localhost:8080
echo.
echo   Key Endpoints to Try:
echo     http://localhost:8080/api/status/health
echo     http://localhost:8080/api/games/all
echo     http://localhost:8080/api/teams/all
echo     http://localhost:8080/login
echo.
echo   View HTML Presentation:
echo     start presentation.html
echo.
echo   Read Documentation:
echo     start PRESENTATION_README.md
echo.
pause

cls
echo ================================================================================
echo   PROJECT SUMMARY
echo ================================================================================
echo.
echo   JUMP BALL API - Key Achievements:
echo.
echo     - Fully functional RESTful API with CRUD operations
echo     - Dual authentication system (OAuth2 + JWT)
echo     - Comprehensive test suite with 55+ tests
echo     - Professional documentation and setup guides
echo     - Production-ready deployment configuration
echo     - Cross-origin resource sharing (CORS) enabled
echo     - Secure environment variable management
echo     - Multi-profile configuration (dev, test, prod)
echo.
echo   Next Steps:
echo     1. Open presentation.html for visual presentation
echo     2. Run .\gradlew.bat bootRun to start the API
echo     3. Visit http://localhost:8080 to test endpoints
echo     4. Deploy to Heroku with git push heroku main
echo.
echo   GitHub Repository:
echo     https://github.com/TuffAaron/project2_backend
echo.
echo ================================================================================
echo   Thank you for reviewing the Jump Ball API project!
echo ================================================================================
echo.
pause
