@echo off
title TrustKeeper Console
cd /d "%~dp0"
echo ==================================================================
echo                   TRUSTKEEPER CONSOLE LAUNCHER
echo ==================================================================
echo.

if not exist "bin" mkdir bin

echo Compiling Java source files...
javac -d bin -sourcepath src src/com/trustkeeper/model/*.java src/com/trustkeeper/util/*.java src/com/trustkeeper/service/*.java src/com/trustkeeper/main/*.java

if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Compilation failed! Please ensure Java JDK is installed.
    pause
    exit /b %errorlevel%
)

echo Launching TrustKeeper Console...
echo.
java -cp bin com.trustkeeper.main.Main

pause
