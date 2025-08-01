package cn.qihuang02.fluidjs.kubejs;

import cn.qihuang02.fluidjs.kubejs.event.FluidModificationEventJS;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.event.EventTargetType;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import org.jetbrains.annotations.NotNull;

public class FluidJSPlugin implements KubeJSPlugin {
    public static final EventGroup GROUP = EventGroup.of("FluidEvents");

    public static final EventHandler MODIFICATION = GROUP.startup("modifyFluid", () -> FluidModificationEventJS.class)
            .supportsTarget(EventTargetType.STRING);

    @Override
    public void registerEvents(@NotNull EventGroupRegistry registry) {
        registry.register(GROUP);
    }
}
