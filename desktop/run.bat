@echo off
echo Starting BloodMate Application...
cd /d "%~dp0"
java --module-path "target\lib" --add-modules javafx.controls,javafx.fxml,javafx.base,javafx.graphics --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED -cp "target\classes;target\lib\*" com.bloodmate.desktop.MainApp
pause

