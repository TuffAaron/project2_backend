# Deploy OAuth Redirect Fix to Heroku

## The Problem
The backend was redirecting to `/dashboard` instead of your Expo app because the `FRONTEND_URL` environment variable wasn't set on Heroku.

## The Solution

### Step 1: Deploy the Code Changes ✅
The code now includes:
- Null check for `frontendUrl`
- Better error logging
- Graceful fallback to `/dashboard` if not configured

### Step 2: Set Environment Variable on Heroku ⚠️ REQUIRED

You **MUST** set the `FRONTEND_URL` environment variable on Heroku for OAuth to redirect to your app.

#### Option A: Via Heroku CLI (Fastest)
```powershell
heroku config:set FRONTEND_URL=exp://localhost:8081 --app jump-ball-df460ee69b61
```

#### Option B: Via Heroku Dashboard
1. Go to: https://dashboard.heroku.com/apps/jump-ball-df460ee69b61/settings
2. Click **"Reveal Config Vars"**
3. Add a new config var:
   - **KEY**: `FRONTEND_URL`
   - **VALUE**: `exp://localhost:8081`
4. Click **"Add"**

### Step 3: Deploy the Updated Code
```powershell
# Make sure you're in the correct directory
cd project2_backend

# Stage all changes
git add -A

# Commit
git commit -m "Fix OAuth redirect - add null check and Expo support"

# Push to Heroku
git push heroku OauthRedirectsToLocalHost:main
```

### Step 4: Verify Deployment
Watch the logs to see if it works:
```powershell
heroku logs --tail --app jump-ball-df460ee69b61
```

Look for these log messages:
```
✅ OAuth login successful for user: [Name]
🔄 Frontend URL configured: exp://localhost:8081
✅ Redirect URL: exp://localhost:8081?name=...&email=...
```

---

## Testing the OAuth Flow

### 1. Test from Expo App
In your Expo app, click "Continue with Google" or "Continue with GitHub"

### 2. Complete OAuth in Browser
Log in with your Google/GitHub account

### 3. Expected Behavior
- ✅ Browser closes automatically
- ✅ Expo app receives the callback
- ✅ Console shows: `🔗 Deep link received: exp://localhost:8081?...`
- ✅ User is logged in and redirected to favoriteTeams

---

## Production Deployment

For production (when deploying to stores), you'll need to:

### 1. Update FRONTEND_URL for Production
```powershell
# Use your custom app scheme (defined in app.json)
heroku config:set FRONTEND_URL=myapp://auth/callback --app jump-ball-df460ee69b61
```

### 2. Update OAuth Provider Redirect URIs
Make sure your OAuth providers (Google, GitHub, etc.) allow:
- Development: `https://jump-ball-df460ee69b61.herokuapp.com/login/oauth2/code/{provider}`
- They don't need to know about `exp://` or `myapp://` - that's handled server-side

### 3. Test with Different URLs
Your app can handle multiple schemes:
- Development: `exp://localhost:8081`
- Production: `myapp://auth/callback`
- Web: `https://yourapp.com/auth/callback`

---

## Troubleshooting

### Problem: Still redirects to /dashboard
**Check:**
1. Is `FRONTEND_URL` set on Heroku?
   ```powershell
   heroku config --app jump-ball-df460ee69b61
   ```
2. Did you deploy the latest code?
   ```powershell
   git push heroku OauthRedirectsToLocalHost:main
   ```

### Problem: "No app to handle this URL"
**Solution:** Make sure your Expo app is running and listening for deep links.

### Problem: Deep link not captured in app
**Check:**
1. Is `Linking.addEventListener` set up in login.tsx?
2. Is the app scheme in app.json correct? (`"scheme": "myapp"`)
3. Restart the Expo server

### Problem: Heroku shows warnings in logs
**Check:**
Look for:
```
⚠️ FRONTEND_URL not configured, falling back to /dashboard
```
This means the environment variable isn't set.

---

## Current Configuration

### Backend (Heroku)
- URL: `https://jump-ball-df460ee69b61.herokuapp.com`
- Branch: `OauthRedirectsToLocalHost`
- Needs: `FRONTEND_URL` environment variable

### Frontend (Expo)
- Scheme: `myapp://`
- Development: `exp://localhost:8081`
- Listening for OAuth callbacks with query params: `?name=&email=&avatar=&authenticated=true`

---

## Quick Commands Reference

```powershell
# Check Heroku config
heroku config --app jump-ball-df460ee69b61

# Set frontend URL
heroku config:set FRONTEND_URL=exp://localhost:8081 --app jump-ball-df460ee69b61

# Deploy to Heroku
git push heroku OauthRedirectsToLocalHost:main

# Watch logs
heroku logs --tail --app jump-ball-df460ee69b61

# Restart Heroku dyno (if needed)
heroku restart --app jump-ball-df460ee69b61
```

---

## Summary

✅ **Fixed:** Added null check for `frontendUrl`  
✅ **Fixed:** Better error logging  
⚠️ **ACTION REQUIRED:** Set `FRONTEND_URL` on Heroku  
⚠️ **ACTION REQUIRED:** Deploy updated code  

**Status:** Ready to deploy! 🚀
