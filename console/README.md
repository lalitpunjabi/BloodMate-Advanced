# BloodMate Console (Core Java)

A simple console-based version of BloodMate without Spring, Hibernate, or Docker. It preserves the core features but runs entirely with core Java.

## Prerequisites
- Java 17+ on Windows (PATH should include `javac` and `java`).

## Build

Double-click or run from PowerShell/CMD in project root:

```bash
cd console
build.bat
```

## Run

```bash
cd console
run.bat
```

## Roadmap (in this folder)
- Models: Donor, Recipient, BloodInventory, Campaign, Rewards
- CSV-backed repositories (no database)
- Services: Eligibility, Donor management, Inventory, Rewards
- Interactive CLI for all operations
- Windows `.exe` packaging via Launch4j (we'll add a config and steps)

## Notes
- Data will live in `console/data/` as CSV files to keep it simple and portable.
- No frameworks used; pure Java classes, compiled with `javac`. 