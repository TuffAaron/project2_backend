# 🏀 Jump Ball API

NBA Game Management System built with Spring Boot

## 📁 Project Structure

```
project2_backend/
├── src/                    # Source code
│   ├── main/              # Application code
│   └── test/              # Test suite (55+ tests)
├── docs/                   # Documentation
│   ├── DATABASE_SETUP.md
│   ├── DEPLOY_TO_HEROKU.md
│   ├── OAUTH2_SETUP.md
│   ├── OAUTH_REDIRECT_SETUP.md
│   ├── IP_ADDRESS_OAUTH_SETUP.md
│   ├── OAUTH_STATUS_CHECK.md
│   ├── POSTMAN_OAUTH_SETUP.md
│   └── SIMPLE_OAUTH_SETUP.md
├── scripts/                # Utility scripts
│   ├── setup-oauth.bat
│   ├── setup-oauth.sh
│   ├── quick-start.bat
│   ├── start-postman.bat
│   └── start-postman-with-oauth.bat
├── presentation/           # Presentation materials
│   ├── presentation.html
│   ├── presentation-demo.ps1
│   ├── demo-brown-doge.bat
│   ├── quick-presentation.bat
│   ├── start-presentation.bat
│   └── PRESENTATION_README.md
├── build.gradle            # Build configuration
├── Procfile               # Heroku deployment
└── system.properties      # Java version

```

## 🚀 Quick Start

```bash
# Run the application
.\gradlew.bat bootRun

# Or use quick-start script
.\scripts\quick-start.bat

# Run tests
.\gradlew.bat test

# Build for production
.\gradlew.bat build
```

## 🎯 Features

- ✅ RESTful API for NBA game management
- ✅ OAuth2 authentication (Google, GitHub)
- ✅ JWT token-based mobile authentication
- ✅ 55+ comprehensive tests (~40% coverage)
- ✅ CORS-enabled for cross-origin requests
- ✅ Production-ready Heroku deployment

## 🔐 Authentication

### OAuth2
- Google OAuth2
- GitHub OAuth2
- See [docs/OAUTH2_SETUP.md](docs/OAUTH2_SETUP.md) for setup instructions

### JWT
- Token-based authentication for mobile apps
- HS512 signature algorithm
- Configurable expiration

## 📊 API Endpoints

### Games
- `GET /api/games/all` - Get all games
- `GET /api/games/{id}` - Get specific game
- `GET /api/games/team/{teamId}` - Get games by team
- `POST /api/games` - Create game
- `PUT /api/games/{id}` - Update game
- `PATCH /api/games/{id}` - Partial update
- `DELETE /api/games/{id}` - Delete game

### Teams
- `GET /api/teams/all` - Get all teams
- `GET /api/teams/{id}` - Get specific team
- `POST /api/teams` - Create team
- `PUT /api/teams/{id}` - Update team

### Status
- `GET /api/status/health` - Health check

## 🧪 Testing

```bash
# Run all tests
.\gradlew.bat test

# Run specific test
.\gradlew.bat test --tests GameServiceTest

# View test reports
# Open: build/reports/tests/test/index.html
```

**Test Coverage:**
- Model tests (Game, Team)
- Repository integration tests
- Service layer tests
- Controller API tests
- Security tests
- **Total: 55+ tests, ~40% coverage**

## 🎓 Presentation

View the project presentation:

```bash
# Interactive demo
.\presentation\demo-brown-doge.bat

# Quick text presentation
.\presentation\quick-presentation.bat

# Visual HTML presentation
start presentation\presentation.html
```

See [presentation/PRESENTATION_README.md](presentation/PRESENTATION_README.md) for more options.

## 📚 Documentation

All documentation is in the [docs](docs/) folder:

- **[DATABASE_SETUP.md](docs/DATABASE_SETUP.md)** - Database configuration
- **[DEPLOY_TO_HEROKU.md](docs/DEPLOY_TO_HEROKU.md)** - Deployment guide
- **[OAUTH2_SETUP.md](docs/OAUTH2_SETUP.md)** - OAuth2 configuration
- **[SIMPLE_OAUTH_SETUP.md](docs/SIMPLE_OAUTH_SETUP.md)** - Quick OAuth2 setup
- **[POSTMAN_OAUTH_SETUP.md](docs/POSTMAN_OAUTH_SETUP.md)** - API testing setup

## 🛠️ Technology Stack

- **Framework:** Spring Boot 3.2.5
- **Language:** Java 17 + Groovy
- **Build Tool:** Gradle
- **Database:** H2 (dev), MySQL (prod)
- **Security:** Spring Security + OAuth2 + JWT
- **Testing:** JUnit 5 + Spock Framework

## 👥 Team

- **Andrew Brown** - 52 commits (OAuth2, JWT, Tests, Documentation)
- **Micah Heneveld** - 45 commits (Core API development)
- **Janniel Tan** - 13 commits (Features)
- **Aaron Perez** - 15 commits (Project management)

## 📈 Project Metrics

- **Total Commits:** 125+
- **Lines of Code:** 5,000+
- **Test Coverage:** ~40%
- **API Endpoints:** 20+
- **Documentation Files:** 8

## 🌐 Deployment

### Heroku

```bash
# Deploy to Heroku
git push heroku main

# View logs
heroku logs --tail
```

See [docs/DEPLOY_TO_HEROKU.md](docs/DEPLOY_TO_HEROKU.md) for detailed instructions.

### Environment Variables

Copy `.env.example` to `.env` and configure:

```
GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret
GITHUB_CLIENT_ID=your_github_client_id
GITHUB_CLIENT_SECRET=your_github_client_secret
JWT_SECRET=your_jwt_secret
JWT_EXPIRATION=86400000
```

## 📝 License

This project is part of CST438 coursework.

## 🔗 Links

- **Repository:** https://github.com/TuffAaron/project2_backend
- **Issues:** https://github.com/TuffAaron/project2_backend/issues

---

**Made with ❤️ by the Jump Ball team**
