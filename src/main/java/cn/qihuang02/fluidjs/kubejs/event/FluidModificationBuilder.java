package cn.qihuang02.fluidjs.kubejs.event;

import cn.qihuang02.fluidjs.mixin.FluidTypeAccessor;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.common.SoundAction;
import net.neoforged.neoforge.fluids.FluidType;

import java.util.Map;

@Info("""
        Allows for the modification of a fluid's properties.
        Note: To make a fluid gaseous, set its density to a negative value.
        """)
public class FluidModificationBuilder {
    private final FluidTypeAccessor fluidTypeAccessor;

    public FluidModificationBuilder(FluidType fluidType) {
        // We cast the FluidType to our mixin interface to get access to the setters.
        this.fluidTypeAccessor = (FluidTypeAccessor) fluidType;
    }

    @Info("Sets the density of the fluid. Affects bouyancy. Negative values make it a gas.")
    public FluidModificationBuilder density(int density) {
        this.fluidTypeAccessor.setDensity(density);
        return this;
    }

    @Info("Sets the viscosity of the fluid. Higher values make it flow slower.")
    public FluidModificationBuilder viscosity(int viscosity) {
        this.fluidTypeAccessor.setViscosity(viscosity);
        return this;
    }

    @Info("Sets the temperature of the fluid in Kelvin. Affects interactions with blocks and entities.")
    public FluidModificationBuilder temperature(int temperature) {
        this.fluidTypeAccessor.setTemperature(temperature);
        return this;
    }

    @Info("Sets the light level emitted by the fluid (0-15).")
    public FluidModificationBuilder lightLevel(int lightLevel) {
        this.fluidTypeAccessor.setLightLevel(lightLevel);
        return this;
    }

    @Info("Sets the rarity of the fluid, affecting its name color in tooltips.")
    public FluidModificationBuilder rarity(Rarity rarity) {
        this.fluidTypeAccessor.setRarity(rarity);
        return this;
    }

    @Info("Replaces all sounds for this fluid. Takes a map of SoundAction to SoundEvent.")
    public FluidModificationBuilder sounds(Map<SoundAction, SoundEvent> newSounds) {
        this.fluidTypeAccessor.setSounds(newSounds);
        return this;
    }

    @Info("Sets whether the fluid can extinguish fire.")
    public FluidModificationBuilder canExtinguish(boolean canExtinguish) {
        this.fluidTypeAccessor.setCanExtinguish(canExtinguish);
        return this;
    }

    @Info("Sets whether the fluid can form new sources.")
    public FluidModificationBuilder canConvertToSource(boolean canConvertToSource) {
        this.fluidTypeAccessor.setCanConvertToSource(canConvertToSource);
        return this;
    }

    @Info("Sets whether boats can be used on this fluid.")
    public FluidModificationBuilder supportsBoating(boolean supportsBoating) {
        this.fluidTypeAccessor.setSupportsBoating(supportsBoating);
        return this;
    }

    @Info("Sets the modifier for fall damage when landing in this fluid.")
    public FluidModificationBuilder fallDistanceModifier(float fallDistanceModifier) {
        this.fluidTypeAccessor.setFallDistanceModifier(fallDistanceModifier);
        return this;
    }

    @Info("Sets whether entities can drown in this fluid.")
    public FluidModificationBuilder canDrown(boolean canDrown) {
        this.fluidTypeAccessor.setCanDrown(canDrown);
        return this;
    }

    @Info("Sets whether entities can swim in this fluid.")
    public FluidModificationBuilder canSwim(boolean canSwim) {
        this.fluidTypeAccessor.setCanSwim(canSwim);
        return this;
    }

    @Info("Sets whether the fluid can push entities.")
    public FluidModificationBuilder canPushEntity(boolean canPushEntity) {
        this.fluidTypeAccessor.setCanPushEntity(canPushEntity);
        return this;
    }

    @Info("Sets the motion scale applied to entities in the fluid.")
    public FluidModificationBuilder motionScale(double motionScale) {
        this.fluidTypeAccessor.setMotionScale(motionScale);
        return this;
    }

    @Info("Sets whether the fluid can hydrate things like farmland.")
    public FluidModificationBuilder canHydrate(boolean canHydrate) {
        this.fluidTypeAccessor.setCanHydrate(canHydrate);
        return this;
    }
}
