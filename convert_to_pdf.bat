@echo off
echo Converting BloodMate Documentation to PDF...
echo.

REM Check if pandoc is installed
where pandoc >nul 2>nul
if %errorlevel% neq 0 (
    echo Pandoc is not installed. Please install pandoc first:
    echo https://pandoc.org/installing.html
    echo.
    echo After installation, run this script again.
    pause
    exit /b 1
)

REM Convert Markdown to PDF using pandoc
echo Converting documentation...
pandoc BloodMate_Documentation.md -o BloodMate_Documentation.pdf --pdf-engine=xelatex --toc --toc-depth=3 --highlight-style=tango --variable=geometry:margin=1in --variable=fontsize:11pt --variable=colorlinks:true --variable=linkcolor:red --variable=urlcolor:red

if %errorlevel% equ 0 (
    echo.
    echo ✅ Success! PDF documentation has been created:
    echo    📄 BloodMate_Documentation.pdf
    echo.
    echo Opening PDF...
    start BloodMate_Documentation.pdf
) else (
    echo.
    echo ❌ Error: Failed to convert documentation to PDF
    echo Please check that you have xelatex installed:
    echo https://www.latex-project.org/get/
)

echo.
pause