package cn.qihuang02.fluidjs.kubejs;

import cn.qihuang02.fluidjs.FluidJS;
import cn.qihuang02.fluidjs.manager.FluidModificationManager;
import cn.qihuang02.fluidjs.manager.MutableFluidTypeProperties;
import dev.latvian.mods.kubejs.event.KubeEvent;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Exposes the {@code FluidJS.modification} startup event to scripts.
 */
public class FluidModificationEvent implements KubeEvent {
    public void modify(String fluidId, Consumer<MutableFluidTypeProperties> consumer) {
        Objects.requireNonNull(consumer, "Consumer must not be null");
        ResourceLocation id = ResourceLocation.tryParse(fluidId);
        if (id == null) {
            FluidJS.LOGGER.warn("Ignoring FluidJS modification for invalid fluid id '{}'.", fluidId);
            return;
        }

        MutableFluidTypeProperties properties = new MutableFluidTypeProperties();
        consumer.accept(properties);
        FluidModificationManager.addModification(id, properties);
    }
}
