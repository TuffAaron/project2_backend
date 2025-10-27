# OAuth Frontend Redirect Configuration

## Overview
Your OAuth flow has been updated to redirect users to your **Expo React Native app** after successful login, instead of the backend `/dashboard` endpoint.

## Changes Made

### 1. SecurityConfig.groovy
- Added a new `oauthSuccessHandler()` method that redirects to your Expo app
- Removed the hardcoded `/dashboard` redirect
- User information (name, email, avatar) is now passed as URL query parameters

### 2. application.properties
- Added `frontend.url` configuration property
- Default value: `exp://localhost:8081` (Expo React Native dev server)

## Configuration

### Local Development
No changes needed - the default `frontend.url=exp://localhost:8081` works with Expo.

### Production (Heroku/Railway/etc.)
Set the `FRONTEND_URL` environment variable to your app's deep link or web URL:

```bash
# For Expo deep linking
heroku config:set FRONTEND_URL=myapp://auth/callback

# For web-based Expo app
heroku config:set FRONTEND_URL=https://your-expo-app.com/auth

# Or via Heroku dashboard: Settings → Config Vars
# Key: FRONTEND_URL
# Value: myapp://auth/callback
```

## How It Works

1. User clicks "Login with GitHub/Google" in your Expo app
2. OAuth flow completes successfully
3. User is redirected to: `exp://localhost:8081?name=...&email=...&avatar=...&authenticated=true`

### Example Redirect URL
```
exp://localhost:8081?name=John%20Doe&email=john@example.com&avatar=https://...&authenticated=true
```

## Expo React Native Integration

### Setting Up Deep Linking in Expo

First, configure your app to handle deep links. In your `app.json` or `app.config.js`:

```json
{
  "expo": {
    "scheme": "myapp",
    "plugins": [
      [
        "expo-router",
        {
          "origin": "..."
        }
      ]
    ]
  }
}
```

### Handling the OAuth Callback

```javascript
// In your app (e.g., App.js or auth callback screen)
import { useEffect } from 'react';
import * as Linking from 'expo-linking';
import { useNavigation } from '@react-navigation/native';

function App() {
  useEffect(() => {
    // Listen for deep link events
    const subscription = Linking.addEventListener('url', handleDeepLink);
    
    // Check if app was opened with a URL
    Linking.getInitialURL().then((url) => {
      if (url) {
        handleDeepLink({ url });
      }
    });
    
    return () => subscription.remove();
  }, []);
  
  const handleDeepLink = ({ url }) => {
    // Parse the URL
    const { queryParams } = Linking.parse(url);
    
    if (queryParams?.authenticated === 'true') {
      const user = {
        name: decodeURIComponent(queryParams.name || ''),
        email: decodeURIComponent(queryParams.email || ''),
        avatar: decodeURIComponent(queryParams.avatar || '')
      };
      
      // Save user to state/AsyncStorage
      console.log('User logged in:', user);
      // navigation.navigate('Home', { user });
    }
  };
  
  return <YourApp />;
}
```

### Opening OAuth in Browser

```javascript
import * as WebBrowser from 'expo-web-browser';
import { Button } from 'react-native';

const handleLogin = async (provider) => {
  // Open OAuth in system browser
  const result = await WebBrowser.openAuthSessionAsync(
    `https://your-backend.herokuapp.com/oauth2/authorization/${provider}`,
    'exp://localhost:8081' // or 'myapp://auth/callback' in production
  );
  
  if (result.type === 'success') {
    // The redirect will be handled by the deep link listener
    console.log('OAuth success:', result.url);
  }
};

// In your component
<Button title="Login with GitHub" onPress={() => handleLogin('github')} />
<Button title="Login with Google" onPress={() => handleLogin('google')} />
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
