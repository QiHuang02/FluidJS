package cn.qihuang02.fluidjs.manager;

import cn.qihuang02.fluidjs.FluidJS;
import cn.qihuang02.fluidjs.kubejs.FluidModificationEvent;
import cn.qihuang02.fluidjs.kubejs.KubeJSEvents;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Stores the scripted overrides that should be applied to {@link net.neoforged.neoforge.fluids.FluidType} instances.
 */
public final class FluidModificationManager {
    private static final Map<ResourceLocation, MutableFluidTypeProperties> MODIFICATIONS = new HashMap<>();
    private static boolean initialized;

    private FluidModificationManager() {
    }

    /**
     * Ensures that the KubeJS modification event has been posted exactly once.
     */
    public static void ensureInitialized() {
        if (!initialized) {
            initialized = true;
            KubeJSEvents.MODIFICATION.post(new FluidModificationEvent());
        }
    }

    public static void addModification(ResourceLocation fluidId, MutableFluidTypeProperties properties) {
        MODIFICATIONS.merge(fluidId, properties, FluidModificationManager::mergeProperties);
        FluidJS.LOGGER.debug("Registered FluidJS override for {}", fluidId);
    }

    public static Optional<MutableFluidTypeProperties> getModification(ResourceLocation fluidId) {
        return Optional.ofNullable(MODIFICATIONS.get(fluidId));
    }

    private static MutableFluidTypeProperties mergeProperties(MutableFluidTypeProperties existing, MutableFluidTypeProperties incoming) {
        MutableFluidTypeProperties merged = new MutableFluidTypeProperties();
        Consumer<MutableFluidTypeProperties> copyExisting = props -> {
            if (existing.getDensity().isPresent()) {
                props.density(existing.getDensity().getAsInt());
            }
            if (existing.getViscosity().isPresent()) {
                props.viscosity(existing.getViscosity().getAsInt());
            }
            if (existing.getTemperature().isPresent()) {
                props.temperature(existing.getTemperature().getAsInt());
            }
            if (existing.getLightLevel().isPresent()) {
                props.lightLevel(existing.getLightLevel().getAsInt());
            }
        };
        Consumer<MutableFluidTypeProperties> copyIncoming = props -> {
            incoming.getDensity().ifPresent(props::density);
            incoming.getViscosity().ifPresent(props::viscosity);
            incoming.getTemperature().ifPresent(props::temperature);
            incoming.getLightLevel().ifPresent(props::lightLevel);
        };
        copyExisting.accept(merged);
        copyIncoming.accept(merged);
        return merged;
    }
}
