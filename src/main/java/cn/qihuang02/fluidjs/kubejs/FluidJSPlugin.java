package cn.qihuang02.fluidjs.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import org.jetbrains.annotations.NotNull;

public class FluidJSPlugin implements KubeJSPlugin {
    public static final EventGroup GROUP = EventGroup.of("FluidJS");

    public static final EventHandler MODIFY = GROUP.server("modify", () -> ModifyFluidPropertiesEvent.class);

    @Override
    public void registerEvents(@NotNull EventGroupRegistry registry) {
        registry.register(GROUP);
    }
}
