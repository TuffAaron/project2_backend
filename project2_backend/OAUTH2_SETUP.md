# 🚀 OAuth2 Setup Instructions for Project2 Backend

## ✅ What's Been Implemented

Your Spring Boot application now has OAuth2 authentication set up with:

### 🔧 **Dependencies Added:**
- `spring-boot-starter-security`
- `spring-boot-starter-oauth2-client`
- `spring-boot-starter-thymeleaf`
- `thymeleaf-extras-springsecurity6`
- `spring-security-test`

### 🛡️ **Security Configuration:**
- OAuth2 login with multiple providers (GitHub, Google, Microsoft, Discord)
- Protected API endpoints
- Public endpoints for testing
- Session management and logout

### 🎨 **Web UI:**
- Home page (`/`) - Overview and quick links
- Login page (`/login`) - OAuth2 provider selection
- Dashboard (`/dashboard`) - User info after authentication
- Profile page (`/profile`) - Detailed user attributes

### 📡 **API Endpoints:**
- **Public:** `/api/public/status` - No auth required
- **Protected:** `/api/user` - User info (requires auth)
- **Protected:** `/api/games/**` - Games API (requires auth)
- **Protected:** `/api/teams/**` - Teams API (requires auth)

## 🔑 Quick Setup Steps

### 1. **Environment Configuration**
Copy and configure your environment file:
```bash
# Copy the template
cp .env.example .env.local

# Edit .env.local with your OAuth2 credentials
```

### 2. **Register OAuth2 Apps**

#### **GitHub** (Recommended for testing):
1. Go to: https://github.com/settings/developers
2. Click "New OAuth App"
3. Fill in:
   - **Application name:** `Project2 Backend OAuth2`
   - **Homepage URL:** `http://localhost:8080`
   - **Authorization callback URL:** `http://localhost:8080/login/oauth2/code/github`
4. Copy Client ID and Client Secret to `.env.local`:
   ```
   GITHUB_CLIENT_ID=your_client_id_here
   GITHUB_CLIENT_SECRET=your_client_secret_here
   ```

#### **Google**:
1. Go to: https://console.developers.google.com/
2. Create new project or select existing
3. Enable "Google+ API"
4. Create OAuth 2.0 credentials
5. Add redirect URI: `http://localhost:8080/login/oauth2/code/google`
6. Update `.env.local` with credentials

#### **Microsoft Azure**:
1. Go to: https://portal.azure.com/
2. Navigate to "Azure Active Directory" → "App registrations"
3. Click "New registration"
4. Add redirect URI: `http://localhost:8080/login/oauth2/code/microsoft`
5. Create client secret
6. Update `.env.local` with credentials

#### **Discord**:
1. Go to: https://discord.com/developers/applications
2. Create "New Application"
3. Go to OAuth2 settings
4. Add redirect URI: `http://localhost:8080/login/oauth2/code/discord`
5. Update `.env.local` with credentials

### 3. **Run the Application**
```bash
# Build and run
./gradlew bootRun

# Or if you're on Windows
gradlew.bat bootRun
```

### 4. **Test the Setup**
1. Visit: http://localhost:8080
2. Click "🔑 Login with OAuth2"
3. Choose a provider you've configured
4. Complete OAuth2 flow
5. You should be redirected to the dashboard

## 🌐 Available URLs

- **Home:** http://localhost:8080/
- **Login:** http://localhost:8080/login
- **Dashboard:** http://localhost:8080/dashboard (requires auth)
- **Profile:** http://localhost:8080/profile (requires auth)
- **H2 Console:** http://localhost:8080/h2-console
- **Public API:** http://localhost:8080/api/public/status
- **User API:** http://localhost:8080/api/user (requires auth)
- **Games API:** http://localhost:8080/api/games (requires auth)

## 🔍 Testing API Endpoints

### **Public Endpoint (No Auth):**
```bash
curl http://localhost:8080/api/public/status
```

### **Protected Endpoints (Requires Auth):**
After logging in via browser, you can test with browser or curl with session cookies:
```bash
# Get user info
curl -b cookies.txt http://localhost:8080/api/user

# Get games
curl -b cookies.txt http://localhost:8080/api/games
```

## 🐛 Troubleshooting

### **Common Issues:**

1. **"Client not found" error:**
   - Check your `.env.local` file has correct client credentials
   - Ensure environment variables are loaded properly

2. **Redirect URI mismatch:**
   - Verify redirect URIs in OAuth provider match exactly:
     `http://localhost:8080/login/oauth2/code/{provider}`

3. **401 Unauthorized on API calls:**
   - Make sure you're logged in first via `/login`
   - Check that cookies are being sent with requests

4. **H2 Console access denied:**
   - The H2 console is accessible without authentication
   - URL: http://localhost:8080/h2-console
   - JDBC URL: `jdbc:h2:file:./data/nba_games_db`

### **Debugging:**
Enable debug logging in `application.properties`:
```properties
logging.level.org.springframework.security=DEBUG
logging.level.org.springframework.security.oauth2=DEBUG
```

## 🚀 Next Steps

1. **Add more providers:** Configure additional OAuth2 providers as needed
2. **User persistence:** Store user information in database
3. **Role-based access:** Implement user roles and permissions
4. **API tokens:** Add JWT token support for API-only access
5. **Frontend integration:** Connect with a frontend framework

## 📚 Documentation References

- [Spring Security OAuth2 Client](https://docs.spring.io/spring-security/reference/servlet/oauth2/client/index.html)
- [Spring Boot OAuth2 Guide](https://spring.io/guides/tutorials/spring-boot-oauth2/)
- [OAuth2 Authorization Code Flow](https://datatracker.ietf.org/doc/html/rfc6749#section-4.1)

---

**🎉 Your OAuth2 setup is complete! Start by configuring at least one provider (GitHub recommended) and test the flow.**