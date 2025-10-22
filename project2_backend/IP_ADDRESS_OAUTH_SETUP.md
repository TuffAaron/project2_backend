# 🌐 OAuth2 IP Address Configuration Guide

## 🚨 Problem: OAuth2 + IP Address Challenge

OAuth2 providers (GitHub, Google, etc.) have strict redirect URI policies:
- ❌ Can't use `localhost` in production
- ❌ Can't use raw IP addresses for some providers 
- ✅ Need proper domain names or specific workarounds

## 🔧 Solutions for Different Scenarios

### 1. **Local Development with IP Access**

#### Option A: Use ngrok (Recommended)
```bash
# Install ngrok: https://ngrok.com/download
# Run your app
./gradlew bootRun

# In another terminal, expose your app
ngrok http 8080
# This gives you: https://abc123.ngrok.io
```

**Configure OAuth2 apps with ngrok URL:**
- GitHub redirect URI: `https://abc123.ngrok.io/login/oauth2/code/github`
- Google redirect URI: `https://abc123.ngrok.io/login/oauth2/code/google`

#### Option B: Local IP with hosts file modification
```bash
# Add to your hosts file (/etc/hosts on Mac/Linux, C:\Windows\System32\drivers\etc\hosts on Windows)
127.0.0.1 myproject.local
192.168.1.100 myproject.local  # Replace with your actual IP
```

Then use `http://myproject.local:8080` as your base URL.

### 2. **Production Deployment Options**

#### Option A: Heroku Deployment (Easiest)
```bash
# Deploy to Heroku (gives you: https://yourapp.herokuapp.com)
heroku create your-project-name
git push heroku master
```

#### Option B: Railway/Render (Alternative cloud platforms)
- Railway: Automatic HTTPS domains
- Render: Free tier with custom domains

#### Option C: Custom Domain + Cloud Provider
- AWS/Azure/GCP with Load Balancer
- CloudFlare for SSL/DNS
- Custom domain pointing to your server IP

### 3. **OAuth2 Provider Configuration by Platform**

#### **GitHub OAuth2 Setup**
1. Go to: https://github.com/settings/developers
2. Click "New OAuth App"
3. Configure:
   ```
   Application name: Project2 Backend
   Homepage URL: https://your-domain.com (or ngrok URL)
   Authorization callback URL: https://your-domain.com/login/oauth2/code/github
   ```

#### **Google OAuth2 Setup** 
1. Go to: https://console.cloud.google.com/apis/credentials
2. Create OAuth 2.0 Client ID
3. Configure:
   ```
   Authorized JavaScript origins: https://your-domain.com
   Authorized redirect URIs: https://your-domain.com/login/oauth2/code/google
   ```

#### **Microsoft OAuth2 Setup**
1. Go to: https://portal.azure.com/#blade/Microsoft_AAD_RegisteredApps
2. Register new application
3. Configure redirect URIs: `https://your-domain.com/login/oauth2/code/microsoft`

## 🔧 Environment Configuration for IP/Domain

Create different environment files for different setups:

### `.env.local` (for local development)
```env
APP_BASE_URL=http://localhost:8080
GITHUB_CLIENT_ID=your_github_client_id
GITHUB_CLIENT_SECRET=your_github_client_secret
GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret
```

### `.env.ngrok` (for ngrok testing)
```env
APP_BASE_URL=https://abc123.ngrok.io
GITHUB_CLIENT_ID=your_github_client_id
GITHUB_CLIENT_SECRET=your_github_client_secret
GOOGLE_CLIENT_ID=your_google_client_id  
GOOGLE_CLIENT_SECRET=your_google_client_secret
```

### `.env.production` (for deployed app)
```env
APP_BASE_URL=https://your-production-domain.com
GITHUB_CLIENT_ID=your_production_github_client_id
GITHUB_CLIENT_SECRET=your_production_github_client_secret
GOOGLE_CLIENT_ID=your_production_google_client_id
GOOGLE_CLIENT_SECRET=your_production_google_client_secret
```

## 🚀 Quick Start Commands

### For Local Development with ngrok:
```bash
# Terminal 1: Start your Spring Boot app
./gradlew bootRun

# Terminal 2: Start ngrok 
ngrok http 8080

# Copy the https URL from ngrok
# Update your OAuth2 app redirect URIs with this URL
# Set APP_BASE_URL environment variable to this URL
```

### For Heroku Deployment:
```bash
# Create Heroku app
heroku create your-app-name

# Set environment variables
heroku config:set APP_BASE_URL=https://your-app-name.herokuapp.com
heroku config:set GITHUB_CLIENT_ID=your_client_id
heroku config:set GITHUB_CLIENT_SECRET=your_client_secret

# Deploy
git push heroku master
```

## 🔍 Testing Your Setup

1. **Test OAuth2 flow:**
   ```
   https://your-domain.com/login
   → Click GitHub/Google login
   → Should redirect to provider
   → After auth, should return to your app
   ```

2. **Test API endpoints:**
   ```
   GET https://your-domain.com/api/public/status (should work without auth)
   GET https://your-domain.com/api/user (should require auth)
   ```

## ⚠️ Common Issues & Solutions

### Issue: "Invalid redirect URI"
**Solution:** Make sure your OAuth2 app redirect URI exactly matches your APP_BASE_URL + `/login/oauth2/code/{provider}`

### Issue: "localhost not accessible from other devices"
**Solution:** Use ngrok or deploy to cloud platform

### Issue: "HTTPS required for OAuth2"
**Solution:** Use ngrok (provides HTTPS) or deploy to platform with SSL

### Issue: "OAuth2 works locally but not in production"
**Solution:** Check that production environment variables match your production OAuth2 app configuration

## 📱 Mobile/Cross-Device Access

If you need to access from mobile devices on the same network:
1. Use ngrok (works from any device)
2. Or set up local network access:
   ```bash
   # Find your local IP
   ipconfig  # Windows
   ifconfig  # Mac/Linux
   
   # Use IP like: http://192.168.1.100:8080
   # But remember: OAuth2 redirect URIs must be registered!
   ```

## 🎯 Recommended Approach

**For Development:** Use ngrok - it's the easiest way to get HTTPS and external access
**For Production:** Deploy to Heroku/Railway/Render for automatic HTTPS and domain