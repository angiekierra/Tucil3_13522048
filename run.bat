@echo off
cls

REM Set the path to your Java source code directory
set SOURCE_DIR=src

REM Set the path to your compiled Java class directory
set OUTPUT_DIR=bin

REM Compile the Java source code
echo Compiling Java files in %SOURCE_DIR%...
javac -d %OUTPUT_DIR% %SOURCE_DIR%\*.java

REM Check if compilation was successful
if errorlevel 1 (
    echo Compilation failed.
    pause
    exit /b
)

REM Run the compiled Java program
echo Running...
cd %OUTPUT_DIR%
cd %SOURCE_DIR%
java src/Main

pause
