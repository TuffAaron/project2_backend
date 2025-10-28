# Frontend Configuration for Heroku Backend

## ✅ Your Backend is LIVE on Heroku!

**Backend URL:** https://jump-ball-df460ee69b61.herokuapp.com/

---

## 🔧 Configure Frontend to Use Heroku Backend

### Option 1: Create .env file (Recommended)

In your frontend project (`CST438_Sports_betting_group14`), create a `.env` file:

```bash
# .env
EXPO_PUBLIC_API_URL=https://jump-ball-df460ee69b61.herokuapp.com
REACT_APP_API_URL=https://jump-ball-df460ee69b61.herokuapp.com
API_BASE_URL=https://jump-ball-df460ee69b61.herokuapp.com
```

### Option 2: Update config.js

If your frontend has a `config.js` or similar file:

```javascript
// config.js
export const API_BASE_URL = 'https://jump-ball-df460ee69b61.herokuapp.com';

// Or for environment-based config
export const API_BASE_URL = 
  process.env.NODE_ENV === 'production' 
    ? 'https://jump-ball-df460ee69b61.herokuapp.com'
    : 'http://localhost:8080';
```

### Option 3: Update API constants

If you have a constants file (like `src/constants/API.js`):

```javascript
// src/constants/API.js
const API_BASE_URL = 'https://jump-ball-df460ee69b61.herokuapp.com';

export const ENDPOINTS = {
  HEALTH: `${API_BASE_URL}/api/status/health`,
  GAMES: `${API_BASE_URL}/api/games/all`,
  TEAMS: `${API_BASE_URL}/api/teams/all`,
  GAME_BY_ID: (id) => `${API_BASE_URL}/api/games/${id}`,
  TEAM_BY_ID: (id) => `${API_BASE_URL}/api/teams/${id}`,
};

export default API_BASE_URL;
```

---

## 🧪 Test the Heroku Backend

### Test in Browser:
```
https://jump-ball-df460ee69b61.herokuapp.com/api/status/health
https://jump-ball-df460ee69b61.herokuapp.com/api/games/all
https://jump-ball-df460ee69b61.herokuapp.com/api/teams/all
```

### Test with PowerShell:
```powershell
Invoke-RestMethod https://jump-ball-df460ee69b61.herokuapp.com/api/status/health
Invoke-RestMethod https://jump-ball-df460ee69b61.herokuapp.com/api/games/all
```

### Test with curl:
```bash
curl https://jump-ball-df460ee69b61.herokuapp.com/api/status/health
curl https://jump-ball-df460ee69b61.herokuapp.com/api/games/all
```

---

## 📱 Frontend Deployment Options

### Option A: Test Locally with Heroku Backend
```bash
cd CST438_Sports_betting_group14
# Update .env with Heroku URL
npx expo start
# Your frontend runs locally but connects to Heroku backend
```

### Option B: Deploy Frontend to Vercel/Netlify
If your frontend is React/Next.js:
```bash
# Deploy to Vercel
vercel --prod

# Or deploy to Netlify
netlify deploy --prod
```

### Option C: Deploy Frontend to Expo
If using Expo:
```bash
npx expo publish
# Or build for app stores
eas build
```

---

## ⚙️ Heroku Backend Info

- **App Name:** jump-ball
- **URL:** https://jump-ball-df460ee69b61.herokuapp.com/
- **Database:** JawsDB MySQL (Leopard plan)
- **Region:** US
- **Stack:** Heroku-24
- **Collaborators:** 
  - aarperez@csumb.edu
  - anbrown@csumb.edu
  - tanjanniel@gmail.com

---

## 🔐 OAuth Configuration

If using OAuth2, update your Heroku config vars:

```bash
heroku config:set FRONTEND_URL=https://your-frontend-url.com --app jump-ball

# Or for Expo mobile app
heroku config:set FRONTEND_URL=exp://your-app-url --app jump-ball
```

---

## ✅ Quick Setup Script

Run this in your frontend directory:

```bash
# Create .env file with Heroku backend URL
echo "EXPO_PUBLIC_API_URL=https://jump-ball-df460ee69b61.herokuapp.com" > .env
echo "REACT_APP_API_URL=https://jump-ball-df460ee69b61.herokuapp.com" >> .env
echo "API_BASE_URL=https://jump-ball-df460ee69b61.herokuapp.com" >> .env

# Install dependencies
npm install

# Start the app
npx expo start
```

---

## 🎯 Testing Checklist

- [ ] Backend is accessible at Heroku URL
- [ ] Health endpoint returns success
- [ ] Games endpoint returns data
- [ ] Teams endpoint returns data
- [ ] Frontend .env configured with Heroku URL
- [ ] Frontend can fetch data from Heroku
- [ ] No CORS errors (backend has CORS enabled)
- [ ] OAuth redirects work (if configured)

---

## 🐛 Troubleshooting

### Issue: CORS errors
**Solution:** Your backend already has `@CrossOrigin(origins = "*")` enabled. Should work fine.

### Issue: Database connection errors
**Check:** JawsDB addon is attached and configured
```bash
heroku config --app jump-ball | grep JAWSDB
```

### Issue: App sleeping (free dyno)
**Solution:** Free Heroku dynos sleep after 30min of inactivity. First request wakes it up (may take 10-20 seconds).

### Issue: OAuth not redirecting
**Solution:** Set FRONTEND_URL environment variable:
```bash
heroku config:set FRONTEND_URL=your-frontend-url --app jump-ball
```

---

## 📊 Full E2E Flow

```
┌─────────────────────┐
│  Mobile/Web App     │  ← Your Frontend
│  (Local/Deployed)   │
└──────────┬──────────┘
           │
           │ HTTPS Requests
           ↓
┌─────────────────────┐
│  Heroku Backend     │  ← jump-ball.herokuapp.com
│  Spring Boot API    │
└──────────┬──────────┘
           │
           │ MySQL
           ↓
┌─────────────────────┐
│  JawsDB Database    │  ← Hosted MySQL
└─────────────────────┘
```

---

**Your backend is ready to use!** Just update your frontend config to point to:
`https://jump-ball-df460ee69b61.herokuapp.com`
