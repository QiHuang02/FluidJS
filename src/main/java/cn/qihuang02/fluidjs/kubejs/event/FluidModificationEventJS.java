package cn.qihuang02.fluidjs.kubejs.event;

import cn.qihuang02.fluidjs.FluidJS;
import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidType;

import java.util.function.Consumer;

public class FluidModificationEventJS implements KubeEvent {
    @Info("""
            Modifies the properties of a fluid specified by its ID.
            
            Example:
            event.modify('minecraft:water', fluid => {
                fluid.density(1500);
            });
            """)
    public void modify(String fluidId, Consumer<FluidModificationBuilder> consumer) {
        ResourceLocation id = ResourceLocation.tryParse(fluidId);

        if (id == null) {
            FluidJS.LOGGER.warn("Invalid fluid ID format for FluidJS: '{}'", fluidId);
            return;
        }

        Fluid fluid = BuiltInRegistries.FLUID.get(id);

        if (fluid == Fluids.EMPTY) {
            FluidJS.LOGGER.warn("Fluid with ID [{}] not found for FluidJS, skipping modification.", fluidId);
            return;
        }

        FluidType fluidType = fluid.getFluidType();

        FluidModificationBuilder builder = new FluidModificationBuilder(fluidType);

        consumer.accept(builder);
    }
}
