package cn.qihuang02.fluidjs;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(FluidJS.MODID)
public class FluidJS {
    public static final String MODID = "fluidjs";
    public static final Logger LOGGER = LogUtils.getLogger();

    public FluidJS(IEventBus modEventBus, ModContainer modContainer) {
    }
}
