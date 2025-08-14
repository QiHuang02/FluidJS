package cn.qihuang02.fluidjs.data;

import cn.qihuang02.fluidjs.FluidJS;
import cn.qihuang02.fluidjs.kubejs.FluidJSPlugin;
import cn.qihuang02.fluidjs.kubejs.ModifyFluidPropertiesEvent;
import cn.qihuang02.fluidjs.network.packet.UpdateFluidPropertiesPacket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FluidPropertiesReloadListener extends SimpleJsonResourceReloadListener {
    private static final String DIRECTORY = "properties";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public FluidPropertiesReloadListener() {
        super(GSON, DIRECTORY);
    }

    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> object, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profiler) {
        Map<ResourceLocation, Map<String, Object>> newOverrides = new ConcurrentHashMap<>();

        for (Map.Entry<ResourceLocation, JsonElement> entry : object.entrySet()) {
            ResourceLocation fileLocation = entry.getKey();
            if (!entry.getValue().isJsonObject()) continue;
            JsonObject fileJson = entry.getValue().getAsJsonObject();

            if (fileJson.has("values") && fileJson.get("values").isJsonObject()) {
                JsonObject values = fileJson.getAsJsonObject("values");

                for (Map.Entry<String, JsonElement> fluidEntry : values.entrySet()) {
                    ResourceLocation fluidId = ResourceLocation.tryParse(fluidEntry.getKey());
                    if (fluidId == null || !fluidEntry.getValue().isJsonObject()) {
                        FluidJS.LOGGER.warn("Invalid fluid ID or format in file '{}': {}", fileLocation, fluidEntry.getKey());
                        continue;
                    }

                    JsonObject properties = fluidEntry.getValue().getAsJsonObject();
                    Map<String, Object> fluidProps = newOverrides.computeIfAbsent(fluidId, k -> new ConcurrentHashMap<>());

                    for (Map.Entry<String, JsonElement> propEntry : properties.entrySet()) {
                        String propName = propEntry.getKey().toLowerCase();
                        JsonElement propValue = propEntry.getValue();
                        Object parsedValue = parseJsonValue(propName, propValue);
                        if (parsedValue != null) {
                            fluidProps.put(propName, parsedValue);
                        } else {
                            FluidJS.LOGGER.warn("Could not parse property '{}' for fluid '{}' in file '{}'", propName, fluidId, fileLocation);
                        }
                    }
                }
            }
        }
        FluidPropertiesManager.setOverrides(newOverrides);
        FluidJS.LOGGER.info("Loaded {} fluid property overrides from data packs.", newOverrides.size());

        if (ModList.get().isLoaded("kubejs")) {
            FluidJS.LOGGER.info("KubeJS detected, applying script modifications...");
            FluidJSPlugin.MODIFY.post(ScriptType.SERVER, new ModifyFluidPropertiesEvent());
            FluidJS.LOGGER.info("Finished applying KubeJS script modifications.");
        }

        if (ServerLifecycleHooks.getCurrentServer() != null) {
            UpdateFluidPropertiesPacket packet = new UpdateFluidPropertiesPacket(FluidPropertiesManager.getOverrides());
            PacketDistributor.sendToAllPlayers(packet);
            FluidJS.LOGGER.info("Synced fluid properties to all connected clients.");
        }
    }

    private @Nullable Object parseJsonValue(String propName, JsonElement propValue) {
        try {
            return switch (propName) {
                case "luminosity", "density", "viscosity", "temperature" -> propValue.getAsInt();
                case "falldistancemodifier", "motionscale" -> propValue.getAsDouble();
                case "canpush", "canswim", "candrown", "canextinguish", "canconverttosource", "supportsboating", "canhydrate" -> propValue.getAsBoolean();
                case "rarity" -> Rarity.valueOf(propValue.getAsString().toUpperCase());
                default -> null;
            };
        } catch (Exception e) {
            return null;
        }
    }
}
