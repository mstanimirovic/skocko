# Java Skocko Game

A simple **Skocko-style logic game** implemented in **Java** using **JavaFX** for the UI and **Maven** for the build system.

The goal of the game is to guess the correct combination of symbols within a limited number of attempts, receiving feedback after each guess—similar to classic Mastermind-style games.

## Tech Stack

* **Java 21 (LTS)**
* **JavaFX** – desktop UI
* **Maven** – build and dependency management
* **jpackage / jlink** – native packaging (DMG on macOS, MSI on Windows)

## Features

* Desktop GUI built with JavaFX
* Clean, minimal game logic
* Cross-platform builds (macOS & Windows)
* Native installers without requiring Java to be preinstalled

## Build & Run

### Build fat JAR

```bash
mvn clean package
```

### Run locally

```bash
java -jar target/*-all.jar
```

### Create native installer

```bash
mvn clean package
```

This will generate:

* **DMG** on macOS
* **MSI** on Windows

(Using `jlink` + `jpackage` under the hood.)

## Project Structure

```
src/
 ├─ main/
 │   ├─ java/        # Game logic and UI
 │   └─ resources/   # JavaFX resources
 └─ test/
```