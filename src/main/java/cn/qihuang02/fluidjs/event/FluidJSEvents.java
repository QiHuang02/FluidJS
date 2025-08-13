package cn.qihuang02.fluidjs.event;

import cn.qihuang02.fluidjs.FluidJS;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = FluidJS.MODID)
public class FluidJSEvents {
    @SubscribeEvent
    public static void onAddReloadListeners(@NotNull AddReloadListenerEvent event) {
        event.addListener(new FluidPropertiesReloadListener());
    }
}
