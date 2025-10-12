package cn.qihuang02.fluidjs.data;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class FluidPropertiesManager {
    private static final Map<ResourceLocation, Map<String, Object>> FLUID_OVERRIDES = new ConcurrentHashMap<>();
    private static final Map<ResourceLocation, ResourceLocation> ALIASES = new ConcurrentHashMap<>();

    /**
     * A generic and type-safe method for Mixins to get a property override.
     *
     * @param fluidId  The ResourceLocation of the fluid to check.
     * @param property The name of the property to get (e.g., "density").
     * @param type     The expected class of the property's value (e.g., Integer.class).
     * @return An Optional containing the typed value if it exists and matches the type, otherwise empty.
     */
    public static <T> Optional<T> getProperty(ResourceLocation fluidId, String property, Class<T> type) {
        ResourceLocation canonicalId = canonicalize(fluidId);

        return Optional.ofNullable(FLUID_OVERRIDES.get(canonicalId))
                .map(props -> props.get(property))
                .filter(type::isInstance)
                .map(type::cast);
    }

    public static Map<String, Object> getOrCreateProperties(ResourceLocation fluidId) {
        ResourceLocation canonicalId = canonicalize(fluidId);

        return FLUID_OVERRIDES.computeIfAbsent(canonicalId, key -> new ConcurrentHashMap<>());
    }

    public static Map<ResourceLocation, Map<String, Object>> getOverrides() {
        return FLUID_OVERRIDES;
    }

    public static void clearOverrides() {
        FLUID_OVERRIDES.clear();
        ALIASES.clear();
    }

    private static ResourceLocation canonicalize(ResourceLocation id) {
        ResourceLocation resolved = resolveCanonicalId(id);
        ResourceLocation previous = ALIASES.put(id, resolved);

        if (previous != null && !previous.equals(resolved)) {
            migrateOverrides(previous, resolved);
        }

        ALIASES.putIfAbsent(resolved, resolved);
        registerLinkedIdentifiers(id, resolved);
        return resolved;
    }

    private static ResourceLocation resolveCanonicalId(ResourceLocation id) {
        if (FLUID_OVERRIDES.containsKey(id)) {
            return id;
        }

        ResourceLocation fromType = resolveFromFluidType(id);
        if (fromType != null) {
            return fromType;
        }

        ResourceLocation fromFluid = resolveFromFluid(id);
        if (fromFluid != null) {
            return fromFluid;
        }

        return id;
    }

    private static ResourceLocation resolveFromFluidType(ResourceLocation id) {
        if (!NeoForgeRegistries.FLUID_TYPES.containsKey(id)) {
            return null;
        }

        ResourceLocation alias = ALIASES.get(id);
        if (alias != null && FLUID_OVERRIDES.containsKey(alias)) {
            return alias;
        }

        FluidType type = NeoForgeRegistries.FLUID_TYPES.get(id);
        if (type == null) {
            return id;
        }

        ResourceLocation match = findFluidAliasForType(type);
        return match != null ? match : id;
    }

    private static ResourceLocation resolveFromFluid(ResourceLocation id) {
        return BuiltInRegistries.FLUID.getOptional(id)
                .map(Fluid::getFluidType)
                .map(type -> {
                    ResourceLocation alias = findFluidAliasForType(type);
                    if (alias != null) {
                        return alias;
                    }

                    ResourceLocation typeId = NeoForgeRegistries.FLUID_TYPES.getKey(type);
                    return typeId != null ? typeId : id;
                })
                .orElse(null);
    }

    private static ResourceLocation findFluidAliasForType(FluidType type) {
        for (Fluid fluid : BuiltInRegistries.FLUID) {
            if (fluid.getFluidType() != type) {
                continue;
            }

            ResourceLocation fluidId = BuiltInRegistries.FLUID.getKey(fluid);
            if (fluidId == null) {
                continue;
            }

            ResourceLocation alias = ALIASES.get(fluidId);
            if (alias != null && FLUID_OVERRIDES.containsKey(alias)) {
                return alias;
            }

            if (FLUID_OVERRIDES.containsKey(fluidId)) {
                return fluidId;
            }
        }

        ResourceLocation typeId = NeoForgeRegistries.FLUID_TYPES.getKey(type);
        if (typeId != null && FLUID_OVERRIDES.containsKey(typeId)) {
            return typeId;
        }

        return null;
    }

    private static void registerLinkedIdentifiers(ResourceLocation source, ResourceLocation canonical) {
        if (NeoForgeRegistries.FLUID_TYPES.containsKey(source)) {
            FluidType type = NeoForgeRegistries.FLUID_TYPES.get(source);
            if (type == null) {
                return;
            }

            for (Fluid fluid : BuiltInRegistries.FLUID) {
                if (fluid.getFluidType() != type) {
                    continue;
                }

                ResourceLocation fluidId = BuiltInRegistries.FLUID.getKey(fluid);
                if (fluidId != null) {
                    ALIASES.putIfAbsent(fluidId, canonical);
                }
            }
            return;
        }

        BuiltInRegistries.FLUID.getOptional(source)
                .map(Fluid::getFluidType)
                .map(NeoForgeRegistries.FLUID_TYPES::getKey)
                .filter(Objects::nonNull)
                .ifPresent(typeId -> ALIASES.putIfAbsent(typeId, canonical));
    }

    private static void migrateOverrides(ResourceLocation from, ResourceLocation to) {
        if (from.equals(to)) {
            return;
        }

        Map<String, Object> existing = FLUID_OVERRIDES.remove(from);
        if (existing == null || existing.isEmpty()) {
            return;
        }

        FLUID_OVERRIDES.merge(to, existing, (target, incoming) -> {
            target.putAll(incoming);
            return target;
        });
    }
}
