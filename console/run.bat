@echo off
set BIN_DIR=bin

if not exist "%BIN_DIR%" (
	echo Bin directory not found. Run build.bat first.
	exit /b 1
)

java -cp "%BIN_DIR%" bloodmate.Main 