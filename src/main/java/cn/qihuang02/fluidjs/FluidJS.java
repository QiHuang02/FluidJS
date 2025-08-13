package cn.qihuang02.fluidjs;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod(FluidJS.MODID)
public class FluidJS {
    public static final String MODID = "fluidjs";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Contract("_ -> new")
    public static @NotNull ResourceLocation getRL(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public FluidJS(@NotNull IEventBus modEventBus, ModContainer container) {
        LOGGER.info("FluidJS is loading!");

        modEventBus.addListener(this::commonSetup);

        LOGGER.info("FluidJS initialization completed!");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("FluidJS common setup completed!");
    }
}
