@echo off
setlocal ENABLEDELAYEDEXPANSION

set SRC_DIR=src
set BIN_DIR=bin

if not exist "%BIN_DIR%" mkdir "%BIN_DIR%"

rem Generate list of all .java files
if exist sources.txt del /q sources.txt
for /r "%SRC_DIR%" %%f in (*.java) do echo %%f >> sources.txt

if not exist sources.txt (
	echo No Java sources found under %SRC_DIR%.
	exit /b 1
)

javac -encoding UTF-8 -d "%BIN_DIR%" @sources.txt
if errorlevel 1 (
	echo Compilation failed.
	exit /b 1
)

echo Build successful. Class files in %BIN_DIR%.
exit /b 0 