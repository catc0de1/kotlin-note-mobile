# Note App

Android App built with kotlin and App Compact. Android material are optional. Pure using usb debugging and from scratch template from [https://github.com/catc0de1/kotlin-minimal-starter](https://github.com/catc0de1/kotlin-minimal-starter.git)

---

## Project Goals

### Features

- Create note app for spesific task
- Can create new item with validation
- Update and deletable item
- Excel like style
- Internal storage app
- Manual backup with csv or json

### To Do

#### Main feature
- [x] Excel like prototipe for note items
- [x] Create static column (index)
- [ ] Create search bar on bottom (static)
- [x] Build create button on left bottom (static)
- [ ] Functional CRUD item

#### Data management
- [ ] About, backup, and exit on drawable
- [ ] Using internal or room storage
- [ ] Manual backup using json or csv

#### Finishing
- [ ] Style finishing
- [ ] Brand and themes
- [ ] Independent deployment

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

## Environment Setup

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

or

```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

If want continuous development

```bash
./gradlew installDebug --continuous
```

### Launch App

```adb
adb shell monkey -p your.package.name -c android.intent.category.LAUNCHER 1
```

### Uninstall App

```adb
adb uninstall com.catcode.noteapp
```

---

## Debugging

View logs:

```bash
adb logcat
```

Filtered:

```bash
adb logcat | grep AndroidRuntime
```

Clean log:

```bash
adb logcat -c
```

---

## License

This Starter is [MIT Licensed](https://github.com/catc0de1/kotlin-note-mobile?tab=MIT-1-ov-file)
