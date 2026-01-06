# Kotlin Minimal Starter

A **minimal, from-scratch Kotlin Android starter** designed for **headless development** using **CLI tools only**.  
No Android Studio. No Emulator.  
Development and deployment are done via **USB debugging** to a physical Android device.

This project is optimized for **Linux Mint** as the primary development environment, but it can be adapted to other operating systems with minor adjustments.

---

## Project Goals

- Android app development **without Android Studio**
- **No emulator**, deploy directly to a real device
- Lightweight, transparent, and scriptable workflow
- Suitable for **low-resource machines**
- Educational: understand Android build system from the ground up

---

## Concept Overview

This project uses:
- **Gradle (Kotlin DSL)**
- **Android SDK Command Line Tools**
- **ADB via USB Debugging**
- **VS Code (or any editor)**
- **Physical Android device**

> GUI is optional. The entire workflow works in a terminal.

---

## Requirements

### Hardware
- Android phone with **USB debugging enabled**
- USB cable

### Software (Linux Mint)
- OpenJDK 17 (or compatible)
- Android SDK Command Line Tools
- Gradle Wrapper (included)
- ADB

---

## Environment Setup (Linux Mint)

Read more environment setup for Linux Mint in my blog [here](https://zblogzone.netlify.app/blog/tutorial/kotlin-cli-only).

1. **Install Java (OpenJDK)**

    ```bash
    sudo apt update
    sudo apt install openjdk-17-jdk
    ```
    
2. **Install Android SDK (Command Line Only)**

    Download command line tools from Google, [https://developer.android.com/studio#command-tools](https://developer.android.com/studio#command-tools), then extract in `$HOME/Android/Sdk/cmdline-tools`

3. **Configure Environment Variables**

    Add to `~/.bashrc` or `~/.zshrc`:

    ```bash
    export ANDROID_HOME=$HOME/Android/Sdk
    export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin
    export PATH=$PATH:$ANDROID_HOME/platform-tools
    ```

    Reload:

    ```bash
    source ~/.bashrc
    ```

4. **Install Required SDK Packages**

    ```bash
    sdkmanager --licenses
    sdkmanager \
      "platform-tools" \
      "platforms;android-34" \
      "build-tools;34.0.0"
    ```

5. **Setting `local.properties`**
   
    Make `local.properties` file in root project.

    ```ini
    sdk.dir=/home/USERNAME/Android/Sdk
    ```

    Which is USERNAME base on OS username. Each OS have different configuration.

## Device Setup

On your Android phone:

1. Enable **Developer Options**
2. Enable **USB Debugging**
3. Connect device via USB

verify connection:

```bash
adb devices
```

Expected output:

```bash
List of devices attached
XXXXXXXX	device
```

---

## Build & Run (CLI Only)

### Build APK

```bash
./gradlew assembleDebug
```

APK output:

```text
app/build/outputs/apk/debug/app-debug.apk
```

### Install to Device

```bash
./gradlew installDebug
```

### Launch App

```adb
adb shell monkey -p your.package.name -c android.intent.category.LAUNCHER 1
```

---

## Debugging

View logs:

```bash
adb logcat
```

Filtered:

```bash
adb logcat | grep YourTag
```

---

## Notes

- This project intentionally avoids Android Studio
- No emulator support by default
- Best used with:
  - VS Code
  - Vim / Neovim
  - Any lightweight editor
- GUI tools are optional, not required

---

## Cross-Platform Notes

|    OS    |              Notes              |
|----------|---------------------------------|
|Linux Mint|Primary supported                |
|Ubuntu    |Same as Mint                     |
|Arch	     |Manual JDK & SDK paths           |
|macOS     |Replace paths & use Homebrew     |
|Windows   |Requires WSL or manual SDK config|

---

## License

This Starter is [MIT Licensed](https://github.com/CatC0de1/kotlin-minimal-starter?tab=MIT-1-ov-file)
