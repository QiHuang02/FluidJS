# FluidJS

FluidJS is a lightweight Minecraft mod that exposes fluid property overrides to KubeJS **startup** scripts. Adjust luminosity, temperature, viscosity, density, motion behaviour, and more for both vanilla and modded fluids using familiar scripting tools.

## Features

- **KubeJS Startup Integration**: Configure fluids inside `kubejs/startup_scripts` without relying on data packs or resource reloads.
- **Comprehensive Property Control**: Override light level, density, temperature, viscosity, motion scale, fall distance modifier, and various interaction flags.
- **Cross-Mod Compatibility**: Works with fluids registered by other mods.

## Installation

1. Download the latest FluidJS release.
2. Place the `.jar` file in your `mods` folder.
3. Install [KubeJS](https://kubejs.com/) 6+ on both client and server if you plan to author scripts.
4. Launch Minecraft with NeoForge 21.1.186+.

## Usage

Fluid overrides now live exclusively in KubeJS **startup** scripts. Create a file such as `kubejs/startup_scripts/fluid_overrides.js` and register your changes via the `FluidJS.modify` event:

```js
FluidJS.modify(event => {
  event.modify('minecraft:water', fluid => {
    fluid.luminosity(8)
         .temperature(320)
         .motionScale(0.7)
         .canDrown(false);
  });

  event.modify('minecraft:lava', fluid => {
    fluid.viscosity(9000)
         .density(3100)
         .canExtinguish(false)
         .supportsBoating(false);
  });
});
```

Because the event runs during the startup phase, changes apply after the game (or dedicated server) is restarted. `/reload` no longer re-applies overrides.

## Property Reference

| Method | Description |
|--------|-------------|
| `luminosity(int value)` | Sets the emitted light level (0-15). |
| `density(int value)` | Adjusts how strongly the fluid pushes entities. |
| `temperature(int value)` | Defines the fluid temperature in Kelvin. |
| `viscosity(int value)` | Controls how quickly the fluid flows. |
| `rarity(String rarity)` | Overrides tooltip rarity (common/uncommon/rare/epic). |
| `motionScale(double value)` | Scales entity motion speed while inside the fluid. |
| `fallDistanceModifier(float value)` | Reduces or amplifies fall damage taken in the fluid. |
| `canPush(boolean value)` | Toggles whether the fluid can push entities. |
| `canSwim(boolean value)` | Controls if entities can swim. |
| `canDrown(boolean value)` | Determines if living entities can drown. |
| `canExtinguish(boolean value)` | Whether the fluid extinguishes fire. |
| `canConvertToSource(boolean value)` | Allows flowing fluid to recreate source blocks. |
| `canHydrate(boolean value)` | Enables farmland hydration. |
| `supportsBoating(boolean value)` | Allows boats to travel on the fluid. |

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
