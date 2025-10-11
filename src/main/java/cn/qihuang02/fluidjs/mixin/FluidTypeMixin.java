package cn.qihuang02.fluidjs.mixin;

import cn.qihuang02.fluidjs.kubejs.FluidModificationManager;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FluidType.class)
public abstract class FluidTypeMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void fluidjs$applyScriptedOverrides(FluidType.Properties properties, CallbackInfo ci) {
        FluidModificationManager.ensureInitialized();

        FluidType self = (FluidType) (Object) this;
        ResourceLocation fluidTypeId = NeoForgeRegistries.FLUID_TYPES.getKey(self);
        if (fluidTypeId == null) {
            return;
        }

        FluidModificationManager.getModification(fluidTypeId).ifPresent(props -> props.applyTo((FluidTypeAccessor) self));
    }
}
