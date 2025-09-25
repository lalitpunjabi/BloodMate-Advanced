# BloodMate Desktop (JavaFX + Core Java + MySQL)

A simple desktop app preserving BloodMate features without Spring/Hibernate. Uses JavaFX for UI and JDBC (MySQL) for data.

## Prerequisites
- Java 17 (JDK) installed and on PATH
- Maven 3.8+
- MySQL running locally with a database named `bloodmate`
  - Create DB: `CREATE DATABASE bloodmate;`
  - Optional: set user/password in `src/main/resources/db.properties`

## Run (Development)
```bash
cd desktop
mvn -q -e -Dprism.order=sw javafx:run
```
If JavaFX fails due to missing modules on some JDK distributions, install OpenJFX or use Azul ZuluFX JDK.

## Build JAR
```bash
mvn -q -DskipTests package
```
The app JAR will be under `target/`. You can run with:
```bash
java -jar target/bloodmate-desktop-1.0.0.jar
```

## Create Windows .exe (Launch4j)
- Install Launch4j and wrap the built JAR.
- Include JavaFX modules by bundling a JRE via jlink (javafx-maven-plugin can create a custom image) or install JavaFX runtime on the target machine.

## Current Features
- List donors from MySQL
- Add donor from the UI

Next steps: add eligibility, inventory, rewards, and recipient management.
