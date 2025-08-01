package cn.qihuang02.fluidjs;

import cn.qihuang02.fluidjs.kubejs.FluidJSPlugin;
import cn.qihuang02.fluidjs.kubejs.event.FluidModificationEventJS;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(FluidJS.MODID)
public class FluidJS {
    public static final String MODID = "fluidjs";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Contract("_ -> new")
    public static @NotNull ResourceLocation getRL(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public FluidJS(@NotNull IEventBus modEventBus) {
        LOGGER.info("FluidEvents is loading!");

        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("FluidJS is applying fluid modifications...");

        FluidJSPlugin.MODIFICATION.post(new FluidModificationEventJS());

        LOGGER.info("FluidJS fluid modifications applied successfully!");
    }
}
