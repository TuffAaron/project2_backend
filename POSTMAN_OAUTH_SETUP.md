# Postman OAuth2 Authentication Setup Guide

This guide explains how to test OAuth2 authentication using Postman with your Spring Boot application.

## Prerequisites

1. **Postman** installed (download from https://www.postman.com/downloads/)
2. **GitHub OAuth App** or **Google OAuth Client** credentials
3. Application running locally on `http://localhost:8080`

## Setup Instructions

### Step 1: Start Application with Postman Profile

Run the application with the `postman` profile enabled:

```bash
# Windows (PowerShell)
.\gradlew.bat bootRun --args='--spring.profiles.active=postman'

# Or set environment variable
$env:SPRING_PROFILES_ACTIVE="postman"
.\gradlew.bat bootRun
```

### Step 2: Configure OAuth2 Credentials

Create a `.env` file or set environment variables with your OAuth2 credentials:

```properties
# GitHub OAuth2
GITHUB_CLIENT_ID=your-github-client-id
GITHUB_CLIENT_SECRET=your-github-client-secret

# Google OAuth2
GOOGLE_CLIENT_ID=your-google-client-id
GOOGLE_CLIENT_SECRET=your-google-client-secret
```

**To create GitHub OAuth App:**
1. Go to https://github.com/settings/developers
2. Click "New OAuth App"
3. Set Authorization callback URL to: `http://localhost:8080/login/oauth2/code/github`
4. Copy Client ID and Client Secret

**To create Google OAuth Client:**
1. Go to https://console.cloud.google.com/apis/credentials
2. Create OAuth 2.0 Client ID
3. Set Authorized redirect URI to: `http://localhost:8080/login/oauth2/code/google`
4. Copy Client ID and Client Secret

### Step 3: Configure Postman

#### Option A: Using Postman's OAuth 2.0 Authorization

1. **Open Postman** and create a new request
2. Go to the **Authorization** tab
3. Select **OAuth 2.0** from the Type dropdown
4. Click **Get New Access Token**
5. Configure:
   
   **For GitHub:**
   - Token Name: `GitHub OAuth`
   - Grant Type: `Authorization Code`
   - Callback URL: `http://localhost:8080/login/oauth2/code/github`
   - Auth URL: `https://github.com/login/oauth/authorize`
   - Access Token URL: `https://github.com/login/oauth/access_token`
   - Client ID: `<your-github-client-id>`
   - Client Secret: `<your-github-client-secret>`
   - Scope: `read:user user:email`

   **For Google:**
   - Token Name: `Google OAuth`
   - Grant Type: `Authorization Code`
   - Callback URL: `http://localhost:8080/login/oauth2/code/google`
   - Auth URL: `https://accounts.google.com/o/oauth2/v2/auth`
   - Access Token URL: `https://oauth2.googleapis.com/token`
   - Client ID: `<your-google-client-id>`
   - Client Secret: `<your-google-client-secret>`
   - Scope: `profile email`

6. Click **Request Token**
7. Complete OAuth flow in browser
8. Use the token in your requests

#### Option B: Browser-Based Authentication (Simpler)

1. Open browser and go to `http://localhost:8080/login`
2. Click "Login with GitHub" or "Login with Google"
3. Complete OAuth flow
4. After successful login, open browser DevTools (F12)
5. Go to Application/Storage > Cookies > `http://localhost:8080`
6. Copy the `JSESSIONID` cookie value
7. In Postman, add a cookie header:
   ```
   Cookie: JSESSIONID=<your-session-id>
   ```

### Step 4: Test Authenticated Endpoints

Create requests in Postman to test your API:

#### Test Authentication Status
```
GET http://localhost:8080/api/auth/status
```

#### Get Current User Info
```
GET http://localhost:8080/api/user
```

#### Get Games (Authenticated)
```
GET http://localhost:8080/api/games/all
```

#### Get Teams (Authenticated)
```
GET http://localhost:8080/api/teams/all
```

## Available Endpoints

### Public Endpoints (No Auth Required)
- `GET /` - Home page
- `GET /login` - Login page
- `GET /api/public/status` - Public status check

### Protected Endpoints (Auth Required)
- `GET /api/auth/status` - Check authentication status
- `GET /api/auth/success` - OAuth success callback info
- `GET /api/user` - Get current user details
- `GET /api/games/all` - Get all games
- `GET /api/games/team/{id}` - Get games by team
- `GET /api/teams/all` - Get all teams
- `GET /dashboard` - User dashboard (HTML)
- `GET /profile` - User profile (HTML)

## Troubleshooting

### Issue: "Not authenticated" responses
- **Solution**: Ensure you're logged in via browser first and using the correct JSESSIONID cookie
- Or configure OAuth 2.0 properly in Postman's Authorization tab

### Issue: CSRF token errors
- **Solution**: CSRF is disabled in postman profile. Ensure you're running with `--spring.profiles.active=postman`

### Issue: OAuth callback not working
- **Solution**: 
  - Verify redirect URIs match exactly in OAuth provider settings
  - Check that application is running on `http://localhost:8080`
  - Ensure OAuth credentials are set correctly in environment variables

### Issue: CORS errors
- **Solution**: The postman profile allows requests from `*.postman.com` and `localhost:8080`

## Security Notes

⚠️ **Important**: The `postman` profile is for **local testing only**!

- CSRF protection is disabled
- Debug logging is enabled
- Uses H2 in-memory database
- Never use this profile in production

For production testing, use the `prod` profile with proper security configurations.

## Postman Collection

You can import this collection to quickly test all endpoints:

```json
{
  "info": {
    "name": "NBA Games API - OAuth2",
    "description": "Test OAuth2 authentication and API endpoints"
  },
  "auth": {
    "type": "oauth2"
  },
  "item": [
    {
      "name": "Auth Status",
      "request": {
        "method": "GET",
        "url": "http://localhost:8080/api/auth/status"
      }
    },
    {
      "name": "Get User",
      "request": {
        "method": "GET",
        "url": "http://localhost:8080/api/user"
      }
    },
    {
      "name": "Get All Games",
      "request": {
        "method": "GET",
        "url": "http://localhost:8080/api/games/all"
      }
    },
    {
      "name": "Get All Teams",
      "request": {
        "method": "GET",
        "url": "http://localhost:8080/api/teams/all"
      }
    }
  ]
}
```

Save this as `NBA_Games_OAuth2.postman_collection.json` and import into Postman.
