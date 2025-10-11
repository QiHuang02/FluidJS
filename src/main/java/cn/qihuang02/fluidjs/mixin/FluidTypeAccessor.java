package cn.qihuang02.fluidjs.mixin;

import net.neoforged.neoforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FluidType.class)
public interface FluidTypeAccessor {
    @Mutable
    @Accessor("density")
    void fluidjs$setDensity(int density);

    @Mutable
    @Accessor("viscosity")
    void fluidjs$setViscosity(int viscosity);

    @Mutable
    @Accessor("temperature")
    void fluidjs$setTemperature(int temperature);

    @Mutable
    @Accessor("lightLevel")
    void fluidjs$setLightLevel(int lightLevel);
}
