package cn.qihuang02.fluidjs.kubejs;

import cn.qihuang02.fluidjs.data.FluidPropertiesManager;
import dev.latvian.mods.kubejs.event.KubeEvent;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ModifyFluidPropertiesEvent implements KubeEvent {
    public void modify(String fluidId, Consumer<FluidPropertySpecificBuilder> consumer) {
        ResourceLocation id = ResourceLocation.tryParse(fluidId);
        if (id == null) {
            return;
        }

        Map<String, Object> fluidProps = FluidPropertiesManager.getOverrides().computeIfAbsent(id, k -> new ConcurrentHashMap<>());
        FluidPropertySpecificBuilder builder = new FluidPropertySpecificBuilder(fluidProps);
        consumer.accept(builder);
//        ScriptRuntime
    }
}
