@echo off
echo 🚀 SUPER SIMPLE OAuth2 Setup
echo ============================
echo.
echo Step 1: Install ngrok if you haven't:
echo https://ngrok.com/download
echo.
echo Step 2: Start your app in one terminal:
echo gradlew bootRun
echo.
echo Step 3: Start ngrok in another terminal:
echo ngrok http 8080
echo.
echo Step 4: Copy the https URL from ngrok (like https://abc123.ngrok.io)
echo.
echo Step 5: Update these places with your ngrok URL:
echo - .env.example (change APP_BASE_URL)
echo - GitHub OAuth app redirect URI: https://your-ngrok-url.ngrok.io/login/oauth2/code/github
echo.
echo Step 6: Restart your app and test at: https://your-ngrok-url.ngrok.io/login
echo.
echo 📖 Full guide: SIMPLE_OAUTH_SETUP.md
pause