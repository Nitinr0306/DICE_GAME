# Dice Tournament — Pure Java Console Project

## Overview
A polished, pure Java console application that simulates a dice tournament. Manage players, play rounds or full matches, view sorted leaderboards, and generate simple statistics. Data is persisted to disk using Java serialization, no external libraries.

## Problem Statement
Provide a simple yet well-structured game to demonstrate Core Java concepts: OOP, collections, streams, exceptions, file I/O, simple design patterns, and a clean console UI suitable for university viva.

## Features
- Player management (add, list, search, delete)
- Configurable dice (sides) and number of dice per round
- Play single rounds or full matches (multiple rounds)
- Tie handling and per-player statistics (wins, losses, total score)
- Leaderboard sorting by wins or name
- Reports with totals and averages using Streams
- Persistent storage of players between runs

## Technologies Used
- Java Standard Library
- Collections (List, Map, Set)
- Streams and Lambdas
- Generics (Repository interface)
- Exception Handling (custom exceptions)
- File Handling (serialization via `ObjectOutputStream`/`ObjectInputStream`)
- Date/Time (timestamps in reports)

## Java Concepts Demonstrated

| Java Concept       | Implementation |
|--------------------|----------------|
| Encapsulation      | Private fields with getters/setters in `model` |
| Inheritance        | Not forced; composition over inheritance used deliberately |
| Polymorphism       | Interface-based `Repository<T,ID>` and services |
| Abstraction        | `Repository` interface and `PlayerService` API |
| Collections        | `Map` for in-memory index, `List` for storage |
| HashMap            | Fast ID lookup in `PlayerService` |
| Comparator         | Sort by wins/name in `PlayerService` |
| Generics           | `Repository<T,ID>` |
| Streams            | Reports and sorting pipelines |
| Lambda             | Stream operations and comparators |
| Exception Handling | Custom exceptions in `exception` package |
| File Handling      | `FilePlayerRepository` using serialization |
| Enums              | `SortOption` for leaderboard sorting |
| Builder Pattern    | `GameConfig.Builder` to configure a match |

## Project Architecture
```
src/
├── app/
│   └── Main.java
├── config/
│   └── GameConfig.java
├── enums/
│   └── SortOption.java
├── exception/
│   ├── DuplicateRecordException.java
│   ├── EntityNotFoundException.java
│   └── InvalidInputException.java
├── model/
│   ├── Dice.java
│   ├── Player.java
│   └── RoundResult.java
├── repository/
│   ├── Repository.java
│   └── FilePlayerRepository.java
├── reports/
│   └── ReportService.java
├── service/
│   ├── GameEngine.java
│   └── PlayerService.java
└── util/
    ├── ConsoleUtils.java
    └── InputValidator.java
```
Legacy original files (`Executor.java`, `GameEngine.java`, `Dice.java`, `Player.java`) are preserved unmodified for reference; the new application compiles from `src` only.

## How to Run
1. Open terminal in project root (`dice project`).
2. Compile sources to `out` directory:
```
javac -d out -sourcepath src src/app/Main.java
```
3. Run the app:
```
java -cp out app.Main
```

Player data is stored in `players.dat` in the project root.

## Example Workflow
- Add players Alice and Bob
- Configure a match: 2 dice, 6 sides each, 5 rounds
- Play match; view leaderboard sorted by wins desc
- Generate report to view totals and averages

## Design Patterns Used
- Builder: `GameConfig.Builder` for configuring matches
- Repository: `Repository<T,ID>` with `FilePlayerRepository`
- Strategy (lightweight): Dice configuration and number of dice influence roll strategy inside `GameEngine`
- Singleton (controlled via static path): Repository path managed centrally within `FilePlayerRepository`

## SOLID Principles
- SRP: Each class has a focused responsibility
- OCP: Sorting and configuration options are extensible
- LSP/ISP: Small focused interfaces (Repository)
- DIP: Services depend on abstractions (`Repository`) not concrete storage

## Future Improvements
- Export reports to CSV
- Add concurrent tournament simulation with `ExecutorService`
- Unit tests with JUnit (kept out for pure core Java focus in this iteration)

