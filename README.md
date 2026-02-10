# Java Skocko Game

A simple **Skocko-style logic game** implemented in **Java** using **JavaFX** for the UI and **Maven** for the build system.

The goal of the game is to guess the correct combination of symbols within a limited number of attempts, receiving feedback after each guess—similar to classic Mastermind-style games.

---

## Tech Stack

* **Java 21 (LTS)**
* **JavaFX** – desktop UI
* **Maven** – build and dependency management
* **jpackage / jlink** – native packaging (DMG on macOS, MSI on Windows)

---

## Features

* Desktop GUI built with JavaFX
* Clean, minimal game logic
* Cross-platform builds (macOS & Windows)
* Native installers without requiring Java to be preinstalled

---

## Build & Run

```bash
mvn clean package
```
---

## Installation (Unsigned Builds)

The installers are **not code-signed**, so macOS and Windows may show security warnings.
This is expected. Follow the steps below to install the app safely.

---

### macOS (DMG)

macOS Gatekeeper may block the app because it is from an unidentified developer.

**Option A — Recommended (UI way):**

1. Download the `.dmg` file
2. Open the DMG
3. Drag the app into **Applications**
4. Try to open the app
5. When macOS blocks it:

    * Go to **System Settings → Privacy & Security**
    * Scroll down to **Security**
    * Click **“Open Anyway”**
6. Confirm once — the app will open normally from then on

**Option B — Terminal (advanced):**

```bash
xattr -rd com.apple.quarantine /Applications/Skocko.app
```

After this, the app will start normally.

---

### Windows (MSI)

Windows SmartScreen may show *“Windows protected your PC”*.

**Steps:**

1. Download the `.msi` file
2. Double-click the installer
3. When SmartScreen appears:

    * Click **More info**
    * Click **Run anyway**
4. Complete the installer wizard

After installation, the app runs normally and SmartScreen will not prompt again.

---

### Why this happens

* The app is **not digitally signed**
* No code-signing certificate is used (yet)
* The binaries are built directly from source using `jpackage`

This does **not** affect functionality — it only impacts first-time installation warnings.

---
