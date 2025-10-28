@echo off
echo ================================================
echo Starting NBA Games API with OAuth2 (from .env)
echo ================================================
echo.

REM Load environment variables from .env file
if exist .env (
    echo Loading OAuth2 credentials from .env file...
    echo.
    for /F "usebackq tokens=1,2 delims==" %%A in (".env") do (
        set "%%A=%%B"
    )
    
    echo GitHub Client ID: %GITHUB_CLIENT_ID%
    echo Google Client ID: %GOOGLE_CLIENT_ID%
    echo.
) else (
    echo ERROR: .env file not found!
    echo Please create a .env file with your OAuth2 credentials.
    echo.
    pause
    exit /b 1
)

echo Starting application with postman profile...
echo Application will start on: http://localhost:8080
echo.
echo OAuth2 login pages:
echo - GitHub: http://localhost:8080/oauth2/authorization/github
echo - Google: http://localhost:8080/oauth2/authorization/google
echo.
echo ================================================
echo.

gradlew.bat bootRun --args="--spring.profiles.active=postman"
