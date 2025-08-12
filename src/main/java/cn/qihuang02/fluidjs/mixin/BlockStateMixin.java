package cn.qihuang02.fluidjs.mixin;

import cn.qihuang02.fluidjs.event.FluidPropertiesManager;
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

    /**
     * 拦截所有方块状态的 getLightEmission 方法调用。
     * 但我们只在方块是 LiquidBlock 的实例时才执行我们的逻辑。
     * 这允许我们动态地覆写“烘焙”在方块属性中的亮度值。
     */
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
        ResourceLocation fluidId;

        if (fluid instanceof FlowingFluid flowingFluid) {
            Fluid sourceFluid = flowingFluid.getSource(false).getType();
            fluidId = BuiltInRegistries.FLUID.getKey(sourceFluid);
        } else {
            fluidId = BuiltInRegistries.FLUID.getKey(fluid);
        }

        FluidPropertiesManager.getProperty(fluidId, "luminosity", Integer.class)
                .ifPresent(cir::setReturnValue);
    }
}
