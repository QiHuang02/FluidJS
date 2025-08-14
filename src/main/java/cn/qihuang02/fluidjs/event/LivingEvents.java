package cn.qihuang02.fluidjs.event;

import cn.qihuang02.fluidjs.FluidJS;
import cn.qihuang02.fluidjs.data.FluidPropertiesManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@EventBusSubscriber(modid = FluidJS.MODID)
public class LivingEvents {
    @SubscribeEvent
    public static void onLivingBreathe(LivingBreatheEvent event) {
        if (event.canBreathe()) {
            return;
        }

        LivingEntity entity = event.getEntity();

        FluidType fluidType = entity.getEyeInFluidType();
        ResourceLocation fluidTypeId = NeoForgeRegistries.FLUID_TYPES.getKey(fluidType);

        if (fluidTypeId != null) {
            boolean canDrownOverride = FluidPropertiesManager.getProperty(fluidTypeId, "candrown", Boolean.class)
                    .orElse(true);

            if (!canDrownOverride) {
                event.setCanBreathe(true);
            }
        }
    }
}
