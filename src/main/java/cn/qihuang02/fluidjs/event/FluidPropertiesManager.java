package cn.qihuang02.fluidjs.event;

import cn.qihuang02.fluidjs.FluidJS;
import cn.qihuang02.fluidjs.config.FluidJSConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@EventBusSubscriber(modid = FluidJS.MODID)
public class FluidPropertiesManager {
    private static final Map<ResourceLocation, Map<String, Object>> FLUID_OVERRIDES = new ConcurrentHashMap<>();

    /**
     * A generic and type-safe method for Mixins to get a property override.
     *
     * @param fluidId  The ResourceLocation of the fluid to check.
     * @param property The name of the property to get (e.g., "density").
     * @param type     The expected class of the property's value (e.g., Integer.class).
     * @return An Optional containing the typed value if it exists and matches the type, otherwise empty.
     */
    public static <T> Optional<T> getProperty(ResourceLocation fluidId, String property, Class<T> type) {
        return Optional.ofNullable(FLUID_OVERRIDES.get(fluidId))
                .map(props -> props.get(property.toLowerCase()))
                .filter(type::isInstance)
                .map(type::cast);
    }

    /**
     * Listens for the config file loading or reloading event.
     * This is the trigger to parse our configuration.
     */
    @SubscribeEvent
    public static void onConfigLoad(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == FluidJSConfig.SPEC) {
            parseConfig();
        }
    }

    @SubscribeEvent
    public static void onConfigReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == FluidJSConfig.SPEC) {
            parseConfig();
        }
    }

    public static void applyKubeJSModifications(Map<ResourceLocation, Map<String, Object>> modifications) {
        if (modifications.isEmpty()) {
            return;
        }
        FluidJS.LOGGER.info("Applying {} fluid modifications from KubeJS scripts.", modifications.size());
        modifications.forEach((fluidId, props) -> {
            FLUID_OVERRIDES.computeIfAbsent(fluidId, k -> new ConcurrentHashMap<>()).putAll(props);
        });
    }

    /**
     * Parses the raw list of strings from the config file into our structured map.
     */
    private static void parseConfig() {
        FLUID_OVERRIDES.clear();
        FluidJS.LOGGER.info("Parsing FluidJS config...");

        List<? extends String> rules = FluidJSConfig.FLUID_MODIFICATIONS.get();
        for (String rule : rules) {
            try {
                String[] parts = rule.split(",", 3);
                if (parts.length != 3) {
                    FluidJS.LOGGER.warn("Invalid rule format in FluidJS config (must have 3 parts): '{}'", rule);
                    continue;
                }

                ResourceLocation fluidId = ResourceLocation.tryParse(parts[0].trim());
                if (fluidId == null) {
                    FluidJS.LOGGER.warn("Invalid fluid resource location in FluidJS config: '{}'", parts[0]);
                    continue;
                }

                String propertyName = parts[1].trim().toLowerCase();
                String valueStr = parts[2].trim();
                Object propertyValue;

                // Handle special case for Rarity enum
                if ("rarity".equals(propertyName)) {
                    try {
                        propertyValue = Rarity.valueOf(valueStr.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        FluidJS.LOGGER.warn("Invalid rarity value in FluidJS config: '{}'. Valid values are COMMON, UNCOMMON, RARE, EPIC.", valueStr);
                        continue;
                    }
                } else {
                    propertyValue = parseValue(valueStr);
                }

                if (propertyValue == null) {
                    FluidJS.LOGGER.warn("Could not parse value for property '{}' in rule: '{}'", propertyName, rule);
                    continue;
                }

                FLUID_OVERRIDES.computeIfAbsent(fluidId, k -> new ConcurrentHashMap<>()).put(propertyName, propertyValue);

            } catch (Exception e) {
                FluidJS.LOGGER.error("Failed to parse rule in FluidJS config: '{}'", rule, e);
            }
        }
        FluidJS.LOGGER.info("Finished parsing FluidJS config. Loaded overrides for {} fluids.", FLUID_OVERRIDES.size());
    }

    /**
     * Tries to parse a string value into the most appropriate object type (Boolean, Integer, or Float).
     */
    private static Object parseValue(String valueStr) {
        String lowerValue = valueStr.toLowerCase();
        
        // Handle boolean values first
        switch (lowerValue) {
            case "true" -> {
                return true;
            }
            case "false" -> {
                return false;
            }
        }
        
        // Handle numeric values
        try {
            if (valueStr.contains(".")) {
                // Contains decimal point - parse as double for precision
                return Double.parseDouble(valueStr);
            } else {
                // No decimal point - parse as integer
                return Integer.parseInt(valueStr);
            }
        } catch (NumberFormatException e) {
            FluidJS.LOGGER.warn("Could not parse '{}' as a valid number or boolean.", valueStr);
            return null;
        }
    }
}
