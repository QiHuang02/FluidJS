package cn.qihuang02.fluidjs;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = FluidJS.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = FluidJS.MODID, value = Dist.CLIENT)
public class FluidJSClient {
    public FluidJSClient(ModContainer container) {
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        FluidJS.LOGGER.info("HELLO FROM CLIENT SETUP");
        FluidJS.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}
