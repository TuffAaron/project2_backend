# OAuth Frontend Redirect Configuration

## Overview
Your OAuth flow has been updated to redirect users to your frontend app after successful login, instead of the backend `/dashboard` endpoint.

## Changes Made

### 1. SecurityConfig.groovy
- Added a new `oauthSuccessHandler()` method that redirects to your frontend app
- Removed the hardcoded `/dashboard` redirect
- User information (name, email, avatar) is now passed as URL query parameters

### 2. application.properties
- Added `FRONTEND_URL` configuration property
- Default value: `http://localhost:3000` (typical React/Next.js dev server)

## Configuration

### Local Development
No changes needed - the default `FRONTEND_URL=http://localhost:3000` should work.

### Production (Heroku/Railway/etc.)
Set the `FRONTEND_URL` environment variable to your deployed frontend app URL:

```bash
# Heroku example
heroku config:set FRONTEND_URL=https://your-app.vercel.app

# Or via Heroku dashboard: Settings → Config Vars
# Key: FRONTEND_URL
# Value: https://your-app.vercel.app
```

## How It Works

1. User clicks "Login with GitHub/Google" 
2. OAuth flow completes successfully
3. User is redirected to: `${FRONTEND_URL}?name=...&email=...&avatar=...&authenticated=true`

### Example Redirect URL
```
http://localhost:3000?name=John%20Doe&email=john@example.com&avatar=https://...&authenticated=true
```

## Frontend Integration

### React/Next.js Example
```javascript
// In your app component or auth callback page
import { useEffect } from 'react';
import { useRouter } from 'next/router'; // or useSearchParams in React

function App() {
  const router = useRouter();
  
  useEffect(() => {
    const { name, email, avatar, authenticated } = router.query;
    
    if (authenticated === 'true') {
      // Store user info in state/context/localStorage
      const user = {
        name: decodeURIComponent(name),
        email: decodeURIComponent(email),
        avatar: decodeURIComponent(avatar)
      };
      
      // Save to your state management
      // setUser(user);
      // localStorage.setItem('user', JSON.stringify(user));
      
      console.log('User logged in:', user);
    }
  }, [router.query]);
  
  return <div>Your App</div>;
}
```

## Additional API Endpoints

Your backend still provides these useful endpoints:

### Check Authentication Status
```javascript
// Frontend can call this to verify session
fetch('http://localhost:8080/api/user', {
  credentials: 'include' // Important: includes session cookie
})
.then(res => res.json())
.then(user => console.log('Authenticated user:', user));
```

### Public Status Check
```javascript
// No authentication required
fetch('http://localhost:8080/api/public/status')
.then(res => res.json())
.then(data => console.log('API status:', data));
```

## CORS Configuration
The CORS settings already allow:
- `http://localhost:*` (any port)
- `https://*.ngrok.io`
- `https://*.herokuapp.com`
- `https://*.railway.app`
- `https://*.render.com`

If your frontend is on a different domain, you may need to add it to the `corsConfigurationSource()` in `SecurityConfig.groovy`.

## Testing

1. Start your backend: `./gradlew bootRun`
2. Start your frontend (e.g., React app on port 3000)
3. Navigate to: `http://localhost:8080/login`
4. Click GitHub or Google login
5. After OAuth success, you should be redirected to your frontend with user info in the URL

## Troubleshooting

### Redirect not working?
- Check the `FRONTEND_URL` environment variable is set correctly
- Verify CORS allows your frontend origin
- Check browser console for any errors

### Session not persisted?
- Make sure your frontend includes `credentials: 'include'` in API calls
- Verify cookies are allowed between domains (or use same domain)

## Need Dashboard Backend?
The `/dashboard` endpoint still exists if you need it for backend admin purposes. Regular users will be redirected to the frontend automatically.
