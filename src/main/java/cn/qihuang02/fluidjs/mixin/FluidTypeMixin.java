package cn.qihuang02.fluidjs.mixin;

import cn.qihuang02.fluidjs.data.FluidPropertiesManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FluidType.class, remap = false)
public abstract class FluidTypeMixin {
    @Unique
    private ResourceLocation fluidjs$getFluidId() {
        return NeoForgeRegistries.FLUID_TYPES.getKey((FluidType) (Object) this);
    }

    @Inject(method = "getDensity()I", at = @At("HEAD"), cancellable = true)
    private void fluidjs$getDensity(CallbackInfoReturnable<Integer> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "density", Integer.class)
                    .ifPresent(cir::setReturnValue);
        }
    }

    @Inject(method = "getFallDistanceModifier(Lnet/minecraft/world/entity/Entity;)F", at = @At("HEAD"), cancellable = true)
    private void fluidjs$getFallDistanceModifier(Entity entity, CallbackInfoReturnable<Float> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "fallDistanceModifier", Number.class)
                    .ifPresent(value -> cir.setReturnValue(value.floatValue()));
        }
    }

    @Inject(method = "getLightLevel()I", at = @At("HEAD"), cancellable = true)
    private void fluidjs$getLightLevel(CallbackInfoReturnable<Integer> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "luminosity", Integer.class)
                    .ifPresent(cir::setReturnValue);
        }
    }

    @Inject(method = "getRarity(Lnet/neoforged/neoforge/fluids/FluidStack;)Lnet/minecraft/world/item/Rarity;", at = @At("HEAD"), cancellable = true)
    private void fluidjs$getRarity(FluidStack stack, CallbackInfoReturnable<Rarity> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "rarity", Rarity.class)
                    .ifPresent(cir::setReturnValue);
        }
    }

    @Inject(method = "getTemperature()I", at = @At("HEAD"), cancellable = true)
    private void fluidjs$getTemperature(CallbackInfoReturnable<Integer> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "temperature", Integer.class)
                    .ifPresent(cir::setReturnValue);
        }
    }

    @Inject(method = "getViscosity()I", at = @At("HEAD"), cancellable = true)
    private void fluidjs$getViscosity(CallbackInfoReturnable<Integer> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "viscosity", Integer.class)
                    .ifPresent(cir::setReturnValue);
        }
    }

    @Inject(method = "canConvertToSource(Lnet/neoforged/neoforge/fluids/FluidStack;)Z", at = @At("HEAD"), cancellable = true)
    private void fluidjs$canConvertToSource(FluidStack stack, CallbackInfoReturnable<Boolean> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "canConvertToSource", Boolean.class)
                    .ifPresent(cir::setReturnValue);
        }
    }

    @Inject(method = "canExtinguish(Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    private void fluidjs$canExtinguish(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "canExtinguish", Boolean.class)
                    .ifPresent(cir::setReturnValue);
        }
    }

    @Inject(method = "canHydrate(Lnet/neoforged/neoforge/fluids/FluidStack;)Z", at = @At("HEAD"), cancellable = true)
    private void fluidjs$canHydrate(FluidStack stack, CallbackInfoReturnable<Boolean> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "canHydrate", Boolean.class)
                    .ifPresent(cir::setReturnValue);
        }
    }

    @Inject(method = "canPushEntity(Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    private void fluidjs$canPushEntity(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "canPush", Boolean.class)
                    .ifPresent(cir::setReturnValue);
        }
    }

    @Inject(method = "canSwim(Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    private void fluidjs$canSwim(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "canSwim", Boolean.class)
                    .ifPresent(cir::setReturnValue);
        }
    }

    @Inject(method = "motionScale(Lnet/minecraft/world/entity/Entity;)D", at = @At("HEAD"), cancellable = true)
    private void fluidjs$motionScale(Entity entity, CallbackInfoReturnable<Double> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "motionScale", Number.class)
                    .ifPresent(value -> cir.setReturnValue(value.doubleValue()));
        }
    }

    @Inject(method = "supportsBoating(Lnet/minecraft/world/entity/vehicle/Boat;)Z", at = @At("HEAD"), cancellable = true)
    private void fluidjs$supportsBoating(Boat boat, CallbackInfoReturnable<Boolean> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "supportsBoating", Boolean.class)
                    .ifPresent(cir::setReturnValue);
        }
    }
}
