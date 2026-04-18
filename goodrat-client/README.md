# 🐀 GoodRat Client
A Lunar-style Fabric utility client for Minecraft 1.21.x

## Features

### 🏃 Movement
| Module   | Keybind | Description                        |
|----------|---------|------------------------------------|
| Flight   | F       | Creative-style flight in survival  |
| Speed    | —       | Speed V effect while enabled       |
| NoFall   | —       | Prevents fall damage               |
| FreeLook | V       | Look around freely (hold)          |

### 🎨 Render
| Module      | Keybind | Description                      |
|-------------|---------|----------------------------------|
| Fullbright  | —       | Night vision - no darkness       |
| Zoom        | C       | Zoom in (hold to zoom)           |
| CrosshairMod| —       | Custom crosshair styles          |

### 🧍 Player
| Module    | Keybind | Description              |
|-----------|---------|--------------------------|
| NoHunger  | —       | Prevents hunger drain    |

### 📊 HUD
| Module           | Description                        |
|------------------|------------------------------------|
| Keystrokes       | WASD + LMB/RMB keystroke display   |
| CPSCounter       | Clicks per second tracker          |
| FPSDisplay       | Current FPS on screen              |
| ArmorStatus      | Armor durability display           |
| CoordinateDisplay| XYZ coordinates on screen          |

## Commands
All commands use the `.` prefix in chat:

| Command              | Description             |
|----------------------|-------------------------|
| `.toggle <module>`   | Toggle a module on/off  |
| `.modules`           | List all modules        |
| `.help`              | Show command help       |

**Example:** `.toggle Flight` — toggles flight on/off

## Building & Installing

### Requirements
- Java 21+
- IntelliJ IDEA (recommended)
- Fabric Loader 0.15+

### Steps
1. Open the `goodrat-client` folder in IntelliJ IDEA
2. Let Gradle sync (it will download Minecraft and dependencies automatically)
3. Run: `./gradlew build` (Mac/Linux) or `gradlew.bat build` (Windows)
4. Find the built `.jar` in `build/libs/` (use the one WITHOUT `-dev` or `-sources`)
5. Drop the `.jar` into your `.minecraft/mods/` folder
6. Also install [Fabric API](https://modrinth.com/mod/fabric-api) in your mods folder
7. Launch Minecraft with the Fabric profile

## Compatibility
- Minecraft: 1.21.x (1.21, 1.21.1, 1.21.4)
- Mod Loader: Fabric
- Requires: Fabric API
