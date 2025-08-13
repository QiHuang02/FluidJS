package cn.qihuang02.fluidjs.network;

import cn.qihuang02.fluidjs.data.FluidPropertiesManager;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public class ClientPayloadHandler {
    public static void handleUpdateFluidProperties(final UpdateFluidPropertiesPacket packet, final @NotNull IPayloadContext context) {
        context.enqueueWork(() -> {
            FluidPropertiesManager.setOverrides(packet.data());
            Minecraft.getInstance().levelRenderer.allChanged();
        });
    }
}
