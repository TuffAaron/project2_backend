@echo off
echo ================================================
echo Starting NBA Games API with Postman OAuth2
echo ================================================
echo.
echo This will start the application with OAuth2 enabled
echo for testing with Postman.
echo.
echo Make sure you have set your OAuth2 credentials:
echo - GITHUB_CLIENT_ID
echo - GITHUB_CLIENT_SECRET
echo - GOOGLE_CLIENT_ID
echo - GOOGLE_CLIENT_SECRET
echo.
echo Application will start on: http://localhost:8080
echo.
echo ================================================
echo.

REM Check if OAuth credentials are set
if "%GITHUB_CLIENT_ID%"=="" (
    echo WARNING: GITHUB_CLIENT_ID not set!
    echo Please set your GitHub OAuth credentials.
    echo.
)

if "%GOOGLE_CLIENT_ID%"=="" (
    echo WARNING: GOOGLE_CLIENT_ID not set!
    echo Please set your Google OAuth credentials.
    echo.
)

echo Starting application with postman profile...
echo.

gradlew.bat bootRun --args="--spring.profiles.active=postman"
