@echo off
REM ============================================================================
REM JUMP BALL API - PRESENTATION LAUNCHER
REM ============================================================================
REM Quick launcher for project presentation
REM ============================================================================

echo.
echo ============================================================================
echo   JUMP BALL API - PRESENTATION LAUNCHER
echo ============================================================================
echo.
echo Choose your presentation mode:
echo.
echo   1. Full Interactive Presentation (with tests)
echo   2. Quick Demo (auto-advance, with tests)
echo   3. Fast Demo (skip tests, quick view)
echo   4. Open Visual HTML Presentation in Browser
echo   5. View Presentation Documentation
echo.

set /p choice="Enter your choice (1-5): "

if "%choice%"=="1" (
    echo.
    echo Starting full interactive presentation...
    powershell -ExecutionPolicy Bypass -File .\presentation-demo.ps1
) else if "%choice%"=="2" (
    echo.
    echo Starting quick demo...
    powershell -ExecutionPolicy Bypass -File .\presentation-demo.ps1 -QuickDemo
) else if "%choice%"=="3" (
    echo.
    echo Starting fast demo without tests...
    powershell -ExecutionPolicy Bypass -File .\presentation-demo.ps1 -QuickDemo -SkipTests
) else if "%choice%"=="4" (
    echo.
    echo Opening HTML presentation in browser...
    start presentation.html
) else if "%choice%"=="5" (
    echo.
    echo Opening presentation documentation...
    start PRESENTATION_README.md
) else (
    echo.
    echo Invalid choice. Please run again and select 1-5.
    pause
    exit /b 1
)

echo.
echo ============================================================================
echo   Presentation Complete!
echo ============================================================================
echo.
pause
