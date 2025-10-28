# 🔄 End-to-End Demo Guide - Backend + Frontend

## Overview

This guide shows you how to run a complete end-to-end demo connecting the **Jump Ball Backend API** with your **React Native/Expo Frontend**.

---

## 📋 Prerequisites

### Backend (This Project)
- ✅ Java 17 installed
- ✅ Gradle wrapper ready
- ✅ Port 8080 available

### Frontend (Separate Expo App)
- ✅ Node.js installed
- ✅ Expo CLI installed (`npm install -g expo-cli`)
- ✅ Frontend repo cloned
- ✅ Port 8081 available (Expo default)

---

## 🚀 Quick Start - End-to-End Demo

### Step 1: Start the Backend (This Project)

Open a terminal in this directory:

```bash
# Option A: Use the demo script
.\presentation\demo-backend.bat

# Option B: Start directly
.\gradlew.bat bootRun
```

**Wait for this message:**
```
Started Project2BackendApplication in X seconds
```

Backend is now running at: **http://localhost:8080**

---

### Step 2: Verify Backend is Running

Open another terminal and test:

```bash
# Test health endpoint
curl http://localhost:8080/api/status/health

# Or use PowerShell
Invoke-RestMethod http://localhost:8080/api/status/health

# Or open in browser
start http://localhost:8080/api/status/health
```

**Expected Response:**
```json
{
  "status": "UP",
  "message": "Application is running",
  "timestamp": "2025-10-28T..."
}
```

---

### Step 3: Find Your Frontend Repository

Your frontend is likely in a separate repo. Common names:
- `project2_frontend`
- `jump-ball-frontend`
- `nba-app-frontend`
- Or similar

**If you don't have it yet, check:**
```bash
# Check GitHub for your frontend repo
# Look for repositories by TuffAaron or team members
```

---

### Step 4: Configure Frontend to Connect to Backend

In your frontend project, update the API configuration:

**Common locations:**
- `config.js`
- `api/config.js`
- `constants/API.js`
- `.env` file

**Update the backend URL:**
```javascript
// For local development
export const API_BASE_URL = 'http://localhost:8080';

// Or in .env
REACT_APP_API_URL=http://localhost:8080
# or
EXPO_PUBLIC_API_URL=http://localhost:8080
```

---

### Step 5: Start the Frontend

Navigate to your frontend directory:

```bash
# Navigate to frontend folder
cd ../project2_frontend  # or your frontend folder name

# Install dependencies (if first time)
npm install

# Start Expo
npx expo start
```

**Or if using React Native CLI:**
```bash
npm start
# Then press 'a' for Android or 'i' for iOS
```

---

### Step 6: Test the Connection

#### Test 1: Health Check from Frontend

Your frontend should make a request to:
```
GET http://localhost:8080/api/status/health
```

#### Test 2: Get Games List

```
GET http://localhost:8080/api/games/all
```

#### Test 3: Get Teams List

```
GET http://localhost:8080/api/teams/all
```

---

## 🧪 Manual E2E Testing Workflow

### Scenario 1: View Games List

1. ✅ **Backend Running**: http://localhost:8080
2. ✅ **Frontend Running**: http://localhost:8081 (Expo)
3. 📱 **On Frontend**: Navigate to "Games" screen
4. 🔄 **Frontend calls**: `GET /api/games/all`
5. ✅ **Backend responds**: JSON array of games
6. 📱 **Frontend displays**: List of games

### Scenario 2: View Team Details

1. 📱 **On Frontend**: Click on a team
2. 🔄 **Frontend calls**: `GET /api/teams/{id}`
3. ✅ **Backend responds**: Team details
4. 📱 **Frontend displays**: Team info and games

### Scenario 3: OAuth2 Login

1. 📱 **On Frontend**: Click "Login with Google"
2. 🔄 **Frontend redirects**: To backend OAuth endpoint
3. 🔐 **Backend**: Initiates Google OAuth2 flow
4. ✅ **User**: Logs in with Google
5. 🔄 **Backend redirects**: Back to frontend with user data
6. 📱 **Frontend**: Stores user session

---

## 🐛 Troubleshooting

### Problem: Frontend can't connect to backend

**Solution 1 - Check CORS:**
Backend has CORS enabled for all origins:
```java
@CrossOrigin(origins = "*")
```

**Solution 2 - Check URLs:**
```bash
# Backend should be at:
http://localhost:8080

# Frontend should be at:
http://localhost:8081  # Expo
```

**Solution 3 - Check Backend Logs:**
Look for incoming requests in backend terminal:
```
GET "/api/games/all" 200 OK
```

### Problem: OAuth2 redirect not working

**Update environment variable:**
```bash
# In .env file (backend)
FRONTEND_URL=exp://localhost:8081
# or
FRONTEND_URL=http://localhost:8081
```

### Problem: Can't access on mobile device

**Use your computer's IP address:**

1. Find your IP:
```bash
# Windows
ipconfig | findstr IPv4

# Mac/Linux
ifconfig | grep inet
```

2. Update backend URL in frontend:
```javascript
// Replace localhost with your IP
export const API_BASE_URL = 'http://192.168.1.100:8080';
```

3. Ensure same WiFi network for both devices

---

## 📊 API Testing with Postman (Alternative to Frontend)

If you don't have the frontend ready, test with Postman:

### Import this Collection:

```json
{
  "info": {
    "name": "Jump Ball API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Health Check",
      "request": {
        "method": "GET",
        "url": "http://localhost:8080/api/status/health"
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

---

## 🎬 Complete Demo Script

### Terminal 1 - Backend
```bash
cd project2_backend
.\presentation\demo-backend.bat
# Wait for "Started Project2BackendApplication"
```

### Terminal 2 - Test API
```bash
# Run the auto-generated test script
powershell -ExecutionPolicy Bypass -File test-api.ps1
```

### Terminal 3 - Frontend (if available)
```bash
cd ../project2_frontend
npm install
npx expo start
# Scan QR code or press 'w' for web
```

### Browser
```
http://localhost:8080/api/status/health
http://localhost:8080/api/games/all
http://localhost:8080/api/teams/all
```

---

## 📝 Expected Data Flow

```
┌─────────────────┐
│  Mobile App     │
│  (Expo/React)   │
│  localhost:8081 │
└────────┬────────┘
         │
         │ HTTP Request
         │ GET /api/games/all
         ↓
┌─────────────────┐
│  Spring Boot    │
│  Backend API    │
│  localhost:8080 │
└────────┬────────┘
         │
         │ Query
         ↓
┌─────────────────┐
│  H2 Database    │
│  (In-Memory)    │
└─────────────────┘
```

---

## ✅ Success Checklist

- [ ] Backend starts successfully on port 8080
- [ ] Health endpoint returns `{"status": "UP"}`
- [ ] `/api/games/all` returns array of games
- [ ] `/api/teams/all` returns array of teams
- [ ] Frontend can connect to backend
- [ ] Data displays correctly in frontend
- [ ] OAuth2 login flow works (if configured)

---

## 🆘 Need Help?

### Check Backend Logs
Look in the backend terminal for errors

### Check Frontend Console
Look in Expo DevTools or browser console

### Test Endpoints Manually
Use curl, Postman, or browser to verify backend works independently

### Verify Configuration
- Backend: Port 8080, CORS enabled
- Frontend: Correct API_BASE_URL
- Network: Same WiFi if testing on phone

---

## 📚 Next Steps

1. **Run Backend Demo**: `.\presentation\demo-backend.bat`
2. **Find Frontend Repo**: Check GitHub for your frontend
3. **Connect & Test**: Follow steps above
4. **Full E2E Demo**: Backend + Frontend working together!

---

**Questions?** Check the documentation in `docs/` folder or README.md
