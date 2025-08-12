package cn.qihuang02.fluidjs.mixin;

import cn.qihuang02.fluidjs.event.FluidPropertiesManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
    // ===== HELPER METHODS =====
    
    /**
     * Helper method to get the ResourceLocation identifier for the current FluidType instance.
     * 
     * <p>This unique method retrieves the registry key (ResourceLocation) for the current
     * FluidType instance by looking it up in the NeoForge fluid types registry. Since this
     * mixin is applied to FluidType, 'this' refers to the specific FluidType instance being
     * accessed, allowing us to determine its identifier for property lookups.</p>
     * 
     * <p>The method uses a cast to (FluidType)(Object)this to work around mixin limitations
     * when accessing the instance within the mixin context.</p>
     * 
     * @return the ResourceLocation identifier for this FluidType, or null if not found in registry
     */
    @Unique
    private ResourceLocation fluidjs$getFluidId() {
        return NeoForgeRegistries.FLUID_TYPES.getKey((FluidType) (Object) this);
    }

    // ===== PHYSICAL PROPERTY METHODS =====
    // Methods that return physical properties of the fluid (density, temperature, etc.)

    /**
     * Intercepts the getDensity() method to provide custom density values.
     * 
     * <p>This method allows overriding the default density of the fluid, which affects
     * entity movement and physics calculations within the fluid.</p>
     * 
     * @param cir the callback info returnable for the original method
     */
    @Inject(method = "getDensity()I", at = @At("HEAD"), cancellable = true)
    private void fluidjs$getDensity(CallbackInfoReturnable<Integer> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "density", Integer.class)
                    .ifPresent(cir::setReturnValue);
        }
    }

    /**
     * Intercepts the getFallDistanceModifier method to provide custom fall distance modification.
     * 
     * <p>This method allows overriding how much this fluid reduces fall damage for entities.
     * Higher values reduce fall damage more, while lower values provide less protection.</p>
     * 
     * @param entity the entity experiencing fall damage modification
     * @param cir the callback info returnable for the original method
     */
    @Inject(method = "getFallDistanceModifier(Lnet/minecraft/world/entity/Entity;)F", at = @At("HEAD"), cancellable = true)
    private void fluidjs$getFallDistanceModifier(Entity entity, CallbackInfoReturnable<Float> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "fallDistanceModifier", Number.class)
                    .ifPresent(value -> cir.setReturnValue(value.floatValue()));
        }
    }

    /**
     * Intercepts the getLightLevel() method to provide custom luminosity values.
     * 
     * <p>This method allows overriding the default light level emitted by the fluid.
     * The luminosity property can be dynamically set through FluidPropertiesManager.</p>
     * 
     * @param cir the callback info returnable for the original method
     */
    @Inject(method = "getLightLevel()I", at = @At("HEAD"), cancellable = true)
    private void fluidjs$getLightLevel(CallbackInfoReturnable<Integer> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "luminosity", Integer.class)
                    .ifPresent(cir::setReturnValue);
        }
    }

    /**
     * Intercepts the getRarity method to provide custom rarity display.
     * 
     * <p>This method allows overriding the visual rarity of the fluid when displayed
     * in inventories or tooltips, affecting the color and formatting of the fluid name.</p>
     * 
     * @param stack the fluid stack being checked for rarity
     * @param cir the callback info returnable for the original method
     */
    @Inject(method = "getRarity(Lnet/neoforged/neoforge/fluids/FluidStack;)Lnet/minecraft/world/item/Rarity;", at = @At("HEAD"), cancellable = true)
    private void fluidjs$getRarity(FluidStack stack, CallbackInfoReturnable<Rarity> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "rarity", Rarity.class)
                    .ifPresent(cir::setReturnValue);
        }
    }

    /**
     * Intercepts the getTemperature() method to provide custom temperature values.
     * 
     * <p>This method allows overriding the default temperature of the fluid, which can
     * affect various interactions and behaviors such as evaporation or freezing effects.</p>
     * 
     * @param cir the callback info returnable for the original method
     */
    @Inject(method = "getTemperature()I", at = @At("HEAD"), cancellable = true)
    private void fluidjs$getTemperature(CallbackInfoReturnable<Integer> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "temperature", Integer.class)
                    .ifPresent(cir::setReturnValue);
        }
    }

    /**
     * Intercepts the getViscosity() method to provide custom viscosity values.
     * 
     * <p>This method allows overriding the default viscosity of the fluid, which determines
     * how thick or resistant to flow the fluid appears and behaves.</p>
     * 
     * @param cir the callback info returnable for the original method
     */
    @Inject(method = "getViscosity()I", at = @At("HEAD"), cancellable = true)
    private void fluidjs$getViscosity(CallbackInfoReturnable<Integer> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "viscosity", Integer.class)
                    .ifPresent(cir::setReturnValue);
        }
    }

    // ===== CAPABILITY METHODS =====
    // Methods that return boolean values about what the fluid can do

    /**
     * Intercepts the canConvertToSource method to provide custom source block conversion behavior.
     * 
     * <p>This method allows overriding whether flowing fluid can convert back into source blocks.
     * Useful for creating fluids with unique flow and pooling characteristics.</p>
     * 
     * @param stack the fluid stack being checked for source conversion
     * @param cir the callback info returnable for the original method
     */
    @Inject(method = "canConvertToSource(Lnet/neoforged/neoforge/fluids/FluidStack;)Z", at = @At("HEAD"), cancellable = true)
    private void fluidjs$canConvertToSource(FluidStack stack, CallbackInfoReturnable<Boolean> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "canConvertToSource", Boolean.class)
                    .ifPresent(cir::setReturnValue);
        }
    }

    /**
     * Intercepts the canDrownIn method to provide custom drowning behavior.
     * 
     * <p>This method allows overriding whether living entities can drown in this fluid type.
     * Custom drowning behavior can be useful for creating safe or dangerous fluid environments.</p>
     * 
     * @param entity the living entity that might drown
     * @param cir the callback info returnable for the original method
     */
    @Inject(method = "canDrownIn(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
    private void fluidjs$canDrownIn(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "canDrown", Boolean.class)
                    .ifPresent(cir::setReturnValue);
        }
    }

    /**
     * Intercepts the canExtinguish method to provide custom fire extinguishing behavior.
     * 
     * <p>This method allows overriding whether this fluid can extinguish fire on entities.
     * Can be used to create flammable fluids or super-effective fire suppressants.</p>
     * 
     * @param entity the entity that might have its fire extinguished
     * @param cir the callback info returnable for the original method
     */
    @Inject(method = "canExtinguish(Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    private void fluidjs$canExtinguish(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "canExtinguish", Boolean.class)
                    .ifPresent(cir::setReturnValue);
        }
    }

    /**
     * Intercepts the canHydrate method to provide custom farmland hydration behavior.
     * 
     * <p>This method allows overriding whether this fluid can hydrate farmland blocks
     * for crop growth. Useful for creating specialized agricultural or magical fluids.</p>
     * 
     * @param stack the fluid stack being checked for hydration capability
     * @param cir the callback info returnable for the original method
     */
    @Inject(method = "canHydrate(Lnet/neoforged/neoforge/fluids/FluidStack;)Z", at = @At("HEAD"), cancellable = true)
    private void fluidjs$canHydrate(FluidStack stack, CallbackInfoReturnable<Boolean> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "canHydrate", Boolean.class)
                    .ifPresent(cir::setReturnValue);
        }
    }

    /**
     * Intercepts the canPushEntity method to provide custom entity pushing behavior.
     * 
     * <p>This method allows overriding whether this fluid can push entities around.
     * Useful for creating fluids that either strongly affect entity movement or have no effect.</p>
     * 
     * @param entity the entity that might be pushed
     * @param cir the callback info returnable for the original method
     */
    @Inject(method = "canPushEntity(Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    private void fluidjs$canPushEntity(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "canPush", Boolean.class)
                    .ifPresent(cir::setReturnValue);
        }
    }

    /**
     * Intercepts the canSwim method to provide custom swimming behavior.
     * 
     * <p>This method allows overriding whether entities can swim in this fluid type.
     * When customized, it can enable or disable swimming mechanics for specific fluids.</p>
     * 
     * @param entity the entity attempting to swim
     * @param cir the callback info returnable for the original method
     */
    @Inject(method = "canSwim(Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    private void fluidjs$canSwim(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "canSwim", Boolean.class)
                    .ifPresent(cir::setReturnValue);
        }
    }

    // ===== INTERACTION METHODS =====
    // Methods that handle entity/environment interactions

    /**
     * Intercepts the motionScale method to provide custom entity movement scaling.
     * 
     * <p>This method allows overriding how much this fluid affects entity movement speed.
     * Values closer to 1.0 allow normal movement, while lower values slow entities down more.</p>
     * 
     * @param entity the entity whose movement is being scaled
     * @param cir the callback info returnable for the original method
     */
    @Inject(method = "motionScale(Lnet/minecraft/world/entity/Entity;)D", at = @At("HEAD"), cancellable = true)
    private void fluidjs$motionScale(Entity entity, CallbackInfoReturnable<Double> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "motionScale", Number.class)
                    .ifPresent(value -> cir.setReturnValue(value.doubleValue()));
        }
    }

    /**
     * Intercepts the supportsBoating method to provide custom boat interaction behavior.
     * 
     * <p>This method allows overriding whether boats can navigate on this fluid type.
     * Enables creation of fluids that either support or prevent boat movement.</p>
     * 
     * @param boat the boat entity attempting to navigate the fluid
     * @param cir the callback info returnable for the original method
     */
    @Inject(method = "supportsBoating(Lnet/minecraft/world/entity/vehicle/Boat;)Z", at = @At("HEAD"), cancellable = true)
    private void fluidjs$supportsBoating(Boat boat, CallbackInfoReturnable<Boolean> cir) {
        ResourceLocation fluidId = fluidjs$getFluidId();
        if (fluidId != null) {
            FluidPropertiesManager.getProperty(fluidId, "supportsBoating", Boolean.class)
                    .ifPresent(cir::setReturnValue);
        }
    }
}
