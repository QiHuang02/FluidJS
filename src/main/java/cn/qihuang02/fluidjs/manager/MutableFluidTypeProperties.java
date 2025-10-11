package cn.qihuang02.fluidjs.manager;

import cn.qihuang02.fluidjs.mixin.FluidTypeAccessor;
import dev.latvian.mods.kubejs.typings.Info;

import java.util.OptionalInt;

/**
 * A mutable representation of {@link net.neoforged.neoforge.fluids.FluidType.Properties} exposed to scripts.
 */
@Info("Builder-style wrapper used to override properties of existing fluid types.")
public class MutableFluidTypeProperties {
    private Integer density;
    private Integer viscosity;
    private Integer temperature;
    private Integer lightLevel;

    public OptionalInt getDensity() {
        return density != null ? OptionalInt.of(density) : OptionalInt.empty();
    }

    public OptionalInt getViscosity() {
        return viscosity != null ? OptionalInt.of(viscosity) : OptionalInt.empty();
    }

    public OptionalInt getTemperature() {
        return temperature != null ? OptionalInt.of(temperature) : OptionalInt.empty();
    }

    public OptionalInt getLightLevel() {
        return lightLevel != null ? OptionalInt.of(lightLevel) : OptionalInt.empty();
    }

    @Info("Sets the density of the fluid type.")
    public MutableFluidTypeProperties density(int value) {
        this.density = value;
        return this;
    }

    @Info("Sets the viscosity of the fluid type.")
    public MutableFluidTypeProperties viscosity(int value) {
        this.viscosity = value;
        return this;
    }

    @Info("Sets the temperature of the fluid type.")
    public MutableFluidTypeProperties temperature(int value) {
        this.temperature = value;
        return this;
    }

    @Info("Sets the emitted light level of the fluid type.")
    public MutableFluidTypeProperties lightLevel(int value) {
        this.lightLevel = value;
        return this;
    }

    public void applyTo(FluidTypeAccessor accessor) {
        getDensity().ifPresent(accessor::fluidjs$setDensity);
        getViscosity().ifPresent(accessor::fluidjs$setViscosity);
        getTemperature().ifPresent(accessor::fluidjs$setTemperature);
        getLightLevel().ifPresent(accessor::fluidjs$setLightLevel);
    }
}
