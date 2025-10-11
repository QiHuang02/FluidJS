package cn.qihuang02.fluidjs.kubejs;

import cn.qihuang02.fluidjs.FluidJS;
import cn.qihuang02.fluidjs.data.FluidPropertiesManager;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.neoforged.fml.ModList;

public final class FluidJSStartupScriptRunner {
    private FluidJSStartupScriptRunner() {
    }

    public static void apply() {
        FluidPropertiesManager.clearOverrides();

        if (!ModList.get().isLoaded("kubejs")) {
            FluidJS.LOGGER.debug("KubeJS not detected; skipping FluidJS startup script processing.");
            return;
        }

        FluidJS.LOGGER.info("Applying FluidJS startup scripts...");
        FluidJSPlugin.MODIFY.post(ScriptType.STARTUP, new ModifyFluidPropertiesEvent());
        FluidJS.LOGGER.info("Finished applying FluidJS startup scripts. Loaded {} fluid overrides.", FluidPropertiesManager.getOverrides().size());
    }
}
