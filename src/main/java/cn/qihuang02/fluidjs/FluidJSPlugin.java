package cn.qihuang02.fluidjs;

import cn.qihuang02.fluidjs.kubejs.KubeJSEvents;
import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;

public final class FluidJSPlugin implements KubeJSPlugin {
    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(KubeJSEvents.GROUP);
    }
}
