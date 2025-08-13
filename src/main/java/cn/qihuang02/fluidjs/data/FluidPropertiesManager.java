package cn.qihuang02.fluidjs.data;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

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

    public static Map<ResourceLocation, Map<String, Object>> getOverrides() {
        return FLUID_OVERRIDES;
    }

    public static void setOverrides(Map<ResourceLocation, Map<String, Object>> newOverrides) {
        FLUID_OVERRIDES.clear();
        FLUID_OVERRIDES.putAll(newOverrides);
    }
}
