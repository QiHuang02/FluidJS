package cn.qihuang02.fluidjs.kubejs;

import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.world.item.Rarity;

import java.util.Map;

public class FluidModifications {
    private final Map<String, Object> properties;

    @HideFromJS
    public FluidModifications(Map<String, Object> properties) {
        this.properties = properties;
    }

    @Info("Sets the light level the fluid emits. Clamped between 0 and 15.")
    public FluidModifications luminosity(int value) {
        properties.put("luminosity", Math.max(0, Math.min(15, value)));
        return this;
    }

    @Info("Sets the density of the fluid. Affects how entities are pushed.")
    public FluidModifications density(int value) {
        properties.put("density", value);
        return this;
    }

    @Info("Sets the temperature of the fluid in Kelvin.")
    public FluidModifications temperature(int value) {
        properties.put("temperature", value);
        return this;
    }

    @Info("Sets the viscosity of the fluid. Affects flow speed and entity movement.")
    public FluidModifications viscosity(int value) {
        properties.put("viscosity", value);
        return this;
    }

    @Info("Sets the rarity of the fluid, affecting its name color in tooltips. Accepts 'common', 'uncommon', 'rare', 'epic'.")
    public FluidModifications rarity(String rarity) {
        properties.put("rarity", Rarity.valueOf(rarity.toUpperCase()));
        return this;
    }

    @Info("Sets how much the fluid affects entity movement speed. Lower values mean more drag.")
    public FluidModifications motionScale(double value) {
        properties.put("motionScale", value);
        return this;
    }

    @Info("Sets how much this fluid reduces fall damage. Higher values reduce damage more.")
    public FluidModifications fallDistanceModifier(float value) {
        properties.put("fallDistanceModifier", (double) value);
        return this;
    }

    @Info("Sets whether the fluid can push entities.")
    public FluidModifications canPush(boolean value) {
        properties.put("canPush", value);
        return this;
    }

    @Info("Sets whether entities can swim in this fluid.")
    public FluidModifications canSwim(boolean value) {
        properties.put("canSwim", value);
        return this;
    }

    @Info("Sets whether living entities can drown in this fluid.")
    public FluidModifications canDrown(boolean value) {
        properties.put("canDrown", value);
        return this;
    }

    @Info("Sets whether the fluid can extinguish fire on entities.")
    public FluidModifications canExtinguish(boolean value) {
        properties.put("canExtinguish", value);
        return this;
    }

    @Info("Sets whether flowing fluid can convert back into source blocks.")
    public FluidModifications canConvertToSource(boolean value) {
        properties.put("canConvertToSource", value);
        return this;
    }

    @Info("Sets whether this fluid can hydrate farmland.")
    public FluidModifications canHydrate(boolean value) {
        properties.put("canHydrate", value);
        return this;
    }

    @Info("Sets whether boats can navigate on this fluid.")
    public FluidModifications supportsBoating(boolean value) {
        properties.put("supportsBoating", value);
        return this;
    }
}
