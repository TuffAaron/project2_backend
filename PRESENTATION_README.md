# 🏀 Jump Ball API - Project Presentation Guide

## Quick Start

### Run the Automated Presentation

```powershell
# Full interactive presentation
.\presentation-demo.ps1

# Quick demo (auto-advance)
.\presentation-demo.ps1 -QuickDemo

# Skip test execution (faster)
.\presentation-demo.ps1 -SkipTests

# Quick demo without tests
.\presentation-demo.ps1 -QuickDemo -SkipTests
```

## What's Included

### 📋 Presentation Sections

1. **Project Overview & Architecture**
   - Technology stack
   - Project structure
   - Key features

2. **Test Suite Demonstration**
   - 55+ automated tests
   - ~40% code coverage
   - Test categories breakdown

3. **Authentication Systems**
   - OAuth2 (Google & GitHub)
   - JWT token authentication
   - Security configuration

4. **REST API Endpoints**
   - Game management endpoints
   - Team management endpoints
   - Status & health checks
   - Authentication endpoints

5. **Team Contributions**
   - Git history analysis
   - Contributor statistics
   - Andrew Brown's key contributions

6. **Project Files Showcase**
   - Configuration files
   - Documentation
   - Code statistics

7. **Build & Deployment**
   - Build process
   - Heroku deployment
   - Production readiness

8. **Live API Demo Instructions**
   - How to run locally
   - Key endpoints to test

## Team Contributions Highlight

### 🏆 Andrew Brown (Brown-doge) - 52 commits

**Major Contributions:**

#### OAuth2 Implementation ✨
- Complete Google & GitHub OAuth2 integration
- Security configuration with CORS support
- Multiple OAuth2 setup documentation guides
- Automated setup scripts

#### Test Suite Development 🧪
- Game & Team model unit tests (162 + 227 lines)
- GameController REST API tests (205 lines)
- GameService business logic tests (215 lines)
- Repository integration tests (385 lines)
- **Total: 55+ tests, ~40% code coverage**

#### JWT Authentication System 🔐
- Custom JWT token provider
- Mobile authentication endpoints
- Token validation and filtering
- HS512 algorithm implementation

#### Development & Deployment 🔧
- Local development environment setup
- H2 database configuration for testing
- Heroku deployment optimization
- Environment variable management

#### Documentation 📚
- `OAUTH2_SETUP.md` - Comprehensive OAuth2 guide
- `DATABASE_SETUP.md` - Database configuration
- `DEPLOY_TO_HEROKU.md` - Deployment instructions
- `SIMPLE_OAUTH_SETUP.md` - Quick setup guide
- `IP_ADDRESS_OAUTH_SETUP.md` - Network configuration
- `OAUTH_REDIRECT_SETUP.md` - Redirect URI setup
- `POSTMAN_OAUTH_SETUP.md` - API testing guide

#### Bug Fixes & Optimization 🐛
- Fixed OAuth2 authentication errors
- Resolved CSRF token issues
- Fixed project structure (removed nested folders)
- Postman OAuth2 testing configuration
- Session management improvements

### 📊 Contribution Breakdown by Category

```
Testing:           ~800 lines of test code
Security:          OAuth2 + JWT implementation
Documentation:     7 comprehensive guides
Configuration:     Environment & deployment setup
Bug Fixes:         10+ critical fixes
```

## Other Contributors

- **Micah Heneveld**: 45 commits - Core API development
- **Janniel Tan**: 13 commits - Feature contributions
- **Aaron Perez**: 15 commits - Project management

## API Endpoints Reference

### Games API
```
GET    /api/games/              - API documentation
GET    /api/games/test          - Test endpoint
GET    /api/games/all           - List all games
GET    /api/games/team/{id}     - Games by team
GET    /api/games/{id}          - Get specific game
POST   /api/games               - Create game
PUT    /api/games/{id}          - Update game
PATCH  /api/games/{id}          - Partial update
DELETE /api/games/{id}          - Delete game
```

### Teams API
```
GET    /api/teams/              - API documentation
GET    /api/teams/all           - List all teams
GET    /api/teams/{id}          - Get specific team
POST   /api/teams               - Create team
PUT    /api/teams/{id}          - Update team
PATCH  /api/teams/{id}          - Partial update
DELETE /api/teams/{id}          - Delete team
```

### Authentication
```
GET    /api/auth/user           - Current user (OAuth2)
GET    /login                   - OAuth2 login page
POST   /api/auth/mobile/login   - JWT login
GET    /oauth2/authorization/google  - Google OAuth2
GET    /oauth2/authorization/github  - GitHub OAuth2
```

### Status
```
GET    /api/status/             - API status
GET    /api/status/health       - Health check
```

## Testing

### Run All Tests
```bash
.\gradlew.bat test
```

### Run Specific Test Class
```bash
.\gradlew.bat test --tests GameServiceTest
```

### Test Coverage Report
```bash
.\gradlew.bat test jacocoTestReport
# View at: build/reports/jacoco/test/html/index.html
```

## Running the Application

### Local Development
```bash
# Using Gradle wrapper
.\gradlew.bat bootRun

# Or use quick-start
.\quick-start.bat
```

### With OAuth2
```bash
# Set up OAuth2 credentials first
.\setup-oauth.bat

# Then start with OAuth2 enabled
.\start-postman-with-oauth.bat
```

### Environment Profiles

- **Default**: H2 in-memory database, OAuth2 disabled
- **Test**: H2 database, minimal security
- **Production**: MySQL/JawsDB, full OAuth2 + JWT

## Technology Stack

### Backend
- **Framework**: Spring Boot 3.2.5
- **Language**: Java 17 + Groovy
- **Build**: Gradle 8.7
- **Security**: Spring Security + OAuth2 + JWT

### Database
- **Development**: H2 (in-memory)
- **Production**: MySQL (JawsDB on Heroku)

### Testing
- **Framework**: JUnit 5 + Spock
- **Coverage**: ~40% with 55+ tests

### Deployment
- **Platform**: Heroku
- **CI/CD**: Git-based deployment

## Project Metrics

- **Total Lines of Code**: 5,000+
- **Test Files**: 7
- **API Endpoints**: 20+
- **Documentation Files**: 10+
- **Total Commits**: 125+
- **Contributors**: 4
- **Test Coverage**: ~40%

## Key Features

✅ RESTful API with full CRUD operations
✅ Dual authentication (OAuth2 + JWT)
✅ Comprehensive test suite
✅ Professional documentation
✅ Production-ready deployment
✅ CORS enabled for cross-origin requests
✅ Secure environment variable management
✅ Multi-profile configuration

## Next Steps for Live Demo

1. **Start the application**
   ```bash
   .\gradlew.bat bootRun
   ```

2. **Open in browser**
   - Health Check: http://localhost:8080/api/status/health
   - API Docs: http://localhost:8080/api/games/
   - OAuth Login: http://localhost:8080/login

3. **Test with Postman**
   - Import collection
   - Configure OAuth2 (use setup guide)
   - Test all endpoints

4. **View test results**
   ```bash
   .\gradlew.bat test
   # Open: build/reports/tests/test/index.html
   ```

## Questions?

- GitHub: https://github.com/TuffAaron/project2_backend
- Issues: https://github.com/TuffAaron/project2_backend/issues

---

**Created for comprehensive project presentation**
*Showcasing all aspects from testing to deployment*
