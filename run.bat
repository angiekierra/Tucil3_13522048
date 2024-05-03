@echo off
cls

set SOURCE_DIR=src
set OUTPUT_DIR=bin

echo Compiling Java files in %SOURCE_DIR%...
javac -d %OUTPUT_DIR% %SOURCE_DIR%\*.java

if errorlevel 1 (
    echo Compilation failed.
    pause
    exit /b
)

echo Running...
cd %OUTPUT_DIR%
java src/Main

