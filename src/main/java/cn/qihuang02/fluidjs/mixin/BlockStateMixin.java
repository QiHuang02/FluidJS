package cn.qihuang02.fluidjs.mixin;

import cn.qihuang02.fluidjs.data.FluidPropertiesManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockBehaviour.BlockStateBase.class, remap = false)
public abstract class BlockStateMixin {
    @Unique
    private BlockState fluidjs$asState() {
        return (BlockState) (Object) this;
    }

    @Inject(method = "getLightEmission",
            at = @At("HEAD"),
            cancellable = true)
    private void fluidjs$getLightEmission(CallbackInfoReturnable<Integer> cir) {
        BlockState state = fluidjs$asState();
        Block block = state.getBlock();

        if (!(block instanceof LiquidBlock)) {
            return;
        }

        Fluid fluid = state.getFluidState().getType();

        if (fluid instanceof FlowingFluid) {
            Fluid sourceFluid = ((FlowingFluid) fluid).getSource();
            ResourceLocation fluidId = BuiltInRegistries.FLUID.getKey(sourceFluid);

            FluidPropertiesManager.getProperty(fluidId, "luminosity", Integer.class)
                    .ifPresent(cir::setReturnValue);
        }
    }
}
