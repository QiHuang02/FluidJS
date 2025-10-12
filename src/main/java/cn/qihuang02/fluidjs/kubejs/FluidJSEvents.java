package cn.qihuang02.fluidjs.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface FluidJSEvents {
    EventGroup GROUP = EventGroup.of("FluidJS");
    EventHandler MODIFICATION = GROUP.startup("modification", () -> FluidModificationKubeEvent.class);
}
