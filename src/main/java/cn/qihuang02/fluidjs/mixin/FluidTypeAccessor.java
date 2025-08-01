package cn.qihuang02.fluidjs.mixin;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.common.SoundAction;
import net.neoforged.neoforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = FluidType.class, remap = false)
public interface FluidTypeAccessor {
    @Mutable
    @Accessor("density")
    void setDensity(int density);

    @Mutable
    @Accessor("viscosity")
    void setViscosity(int viscosity);

    @Mutable
    @Accessor("temperature")
    void setTemperature(int temperature);

    @Mutable
    @Accessor("lightLevel")
    void setLightLevel(int lightLevel);

    @Mutable
    @Accessor("rarity")
    void setRarity(Rarity rarity);

    @Mutable
    @Accessor("sounds")
    void setSounds(Map<SoundAction, SoundEvent> sounds);

    @Mutable
    @Accessor("canExtinguish")
    void setCanExtinguish(boolean canExtinguish);

    @Mutable
    @Accessor("canConvertToSource")
    void setCanConvertToSource(boolean canConvertToSource);

    @Mutable
    @Accessor("supportsBoating")
    void setSupportsBoating(boolean supportsBoating);

    @Mutable
    @Accessor("fallDistanceModifier")
    void setFallDistanceModifier(float fallDistanceModifier);

    @Mutable
    @Accessor("canDrown")
    void setCanDrown(boolean canDrown);

    @Mutable
    @Accessor("canSwim")
    void setCanSwim(boolean canSwim);

    @Mutable
    @Accessor("canPushEntity")
    void setCanPushEntity(boolean canPushEntity);

    @Mutable
    @Accessor("motionScale")
    void setMotionScale(double motionScale);

    @Mutable
    @Accessor("canHydrate")
    void setCanHydrate(boolean canHydrate);
}
