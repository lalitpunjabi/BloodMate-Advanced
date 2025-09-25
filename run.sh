#!/bin/bash

echo "Starting BloodMate Blood Donation Management System..."
echo ""
echo "Make sure you have:"
echo "- Java 17+ installed"
echo "- Maven installed"
echo "- MySQL running on localhost:3306"
echo ""
echo "Starting application..."

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "Error: Java 17 or higher is required. Current version: $JAVA_VERSION"
    exit 1
fi

echo "Java version: $(java -version 2>&1 | head -n 1)"
echo "Maven version: $(mvn -version | head -n 1)"
echo ""

# Start the application
mvn spring-boot:run
