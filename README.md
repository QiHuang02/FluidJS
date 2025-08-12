
# FluidJS

FluidJS is a powerful Minecraft mod that allows you to modify fluid properties through data maps. Customize lightLevel, temperature, viscosity, density, flow behavior, and more for both vanilla and modded fluids using simple JSON configuration files.

## Features

- **Data Map Integration**: Uses Minecraft's native data map system for fluid property modification
- **Comprehensive Property Control**: Modify multiple fluid properties including:
  - Light Level (0-15)
  - Temperature
  - Viscosity 
  - Density
  - Flow behavior (tick delay, level decrease per block)
  - Interaction properties (can swim, can drown, can extinguish, etc.)
- **Hot Reload Support**: Configuration changes are applied when you reload resources (`/reload`)
- **Validation System**: Built-in validation prevents invalid configurations
- **Cross-Mod Compatibility**: Works with fluids from other mods

## Installation

1. Download the latest version of FluidJS
2. Place the `.jar` file in your `mods` folder
3. Start your Minecraft server/client with NeoForge 21.1.186+

## Usage

### Basic Configuration

Create or modify data map files in your datapack or mod's resources:

```
data/your_namespace/data_maps/fluid/fluid_properties.json
```

### Configuration Format

```json
{
  "replace": false,
  "values": {
    "minecraft:water": {
      "lightLevel": 0,
      "temperature": 300,
      "viscosity": 1000,
      "density": 1000,
      "gaseous": false,
      "canPushEntity": true,
      "canSwim": true,
      "canDrown": true,
      "canExtinguish": true,
      "canConvertToSource": true,
      "levelDecreasePerBlock": 1,
      "tickDelay": 5
    },
    "minecraft:lava": {
      "lightLevel": 15,
      "temperature": 1300,
      "viscosity": 6000,
      "density": 3000,
      "gaseous": false,
      "canPushEntity": true,
      "canSwim": false,
      "canDrown": true,
      "canExtinguish": false,
      "canConvertToSource": true,
      "levelDecreasePerBlock": 2,
      "tickDelay": 30
    }
  }
}
```

### Property Descriptions

| Property | Type | Range | Description |
|----------|------|-------|-------------|
| `lightLevel` | Integer | 0-15 | Light level emitted by the fluid |
| `temperature` | Integer | Any | Temperature of the fluid in Kelvin |
| `viscosity` | Integer | 0+ | How thick/sticky the fluid is |
| `density` | Integer | Any | Fluid density (affects entity movement) |
| `gaseous` | Boolean | - | Whether the fluid behaves as a gas |
| `canPushEntity` | Boolean | - | Whether the fluid can push entities |
| `canSwim` | Boolean | - | Whether entities can swim in this fluid |
| `canDrown` | Boolean | - | Whether entities can drown in this fluid |
| `canExtinguish` | Boolean | - | Whether the fluid can extinguish fire |
| `canConvertToSource` | Boolean | - | Whether flowing fluid can become source blocks |
| `levelDecreasePerBlock` | Integer | 1-8 | How much the fluid level decreases per block |
| `tickDelay` | Integer | 1+ | Delay between fluid flow updates |
| `motionScale` | String | - | Scale factor for entity motion in fluid |

### Example Configurations

#### Glowing Water
```json
{
  "replace": false,
  "values": {
    "minecraft:water": {
      "lightLevel": 8
    }
  }
}
```

#### Slower Lava
```json
{
  "replace": false,
  "values": {
    "minecraft:lava": {
      "tickDelay": 60,
      "viscosity": 10000
    }
  }
}
```

#### Mod Fluid Support
```json
{
  "replace": false,
  "values": {
    "thermal:crude_oil": {
      "lightLevel": 0,
      "viscosity": 8000,
      "canSwim": false,
      "canExtinguish": false
    }
  }
}
```

## Development

### Requirements
- Java 21
- NeoForge 21.1.186+
- Minecraft 1.21.1

### Building
```bash
./gradlew build
```

## License

This project is licensed under the MIT License.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
