@echo off
REM OAuth2 Setup Script for Windows (IP Address Access)
echo 🚀 OAuth2 IP Address Setup Script
echo ==================================

REM Check if ngrok is installed
where ngrok >nul 2>nul
if %ERRORLEVEL% == 0 (
    echo ✅ ngrok is installed
) else (
    echo ❌ ngrok is not installed
    echo 📦 Please install ngrok from: https://ngrok.com/download
    echo    Or use: choco install ngrok ^(if you have chocolatey^)
)

echo.
echo 🔧 Setup Options:
echo 1. Local Development ^(localhost:8080^)
echo 2. ngrok Tunnel ^(external access with HTTPS^)
echo 3. Production Deployment ^(Heroku/Railway^)
echo.

set /p choice="Choose an option (1-3): "

if "%choice%"=="1" (
    echo 🏠 Setting up for LOCAL DEVELOPMENT...
    copy .env.local .env
    echo ✅ Environment configured for localhost
    echo 📋 Next steps:
    echo    1. Update .env with your OAuth2 client IDs/secrets
    echo    2. Configure OAuth2 apps with redirect URI: http://localhost:8080/login/oauth2/code/{provider}
    echo    3. Run: gradlew bootRun
) else if "%choice%"=="2" (
    echo 🌍 Setting up for NGROK TUNNEL...
    copy .env.ngrok .env
    echo ✅ Environment configured for ngrok
    echo 📋 Next steps:
    echo    1. Start your app: gradlew bootRun
    echo    2. In another terminal: ngrok http 8080
    echo    3. Copy the https URL from ngrok ^(e.g., https://abc123.ngrok.io^)
    echo    4. Update .env with APP_BASE_URL=https://abc123.ngrok.io
    echo    5. Update OAuth2 apps with redirect URI: https://abc123.ngrok.io/login/oauth2/code/{provider}
) else if "%choice%"=="3" (
    echo ☁️ Setting up for PRODUCTION...
    copy .env.production .env
    echo ✅ Environment configured for production
    echo 📋 Next steps:
    echo    1. Deploy to Heroku: heroku create your-app-name
    echo    2. Set environment variables: heroku config:set APP_BASE_URL=https://your-app.herokuapp.com
    echo    3. Update OAuth2 apps with redirect URI: https://your-app.herokuapp.com/login/oauth2/code/{provider}
    echo    4. Deploy: git push heroku master
) else (
    echo ❌ Invalid option
    exit /b 1
)

echo.
echo 🎯 OAuth2 Provider Setup Links:
echo GitHub: https://github.com/settings/developers
echo Google: https://console.cloud.google.com/apis/credentials
echo Microsoft: https://portal.azure.com/#blade/Microsoft_AAD_RegisteredApps
echo.
echo 📚 For detailed instructions, see: IP_ADDRESS_OAUTH_SETUP.md

pause