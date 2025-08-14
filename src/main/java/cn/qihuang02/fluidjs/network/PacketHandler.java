package cn.qihuang02.fluidjs.network;

import cn.qihuang02.fluidjs.FluidJS;
import cn.qihuang02.fluidjs.network.client.ClientPayloadHandler;
import cn.qihuang02.fluidjs.network.packet.UpdateFluidPropertiesPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = FluidJS.MODID)
public class PacketHandler {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1.0.0");

        registrar.playToClient(
                UpdateFluidPropertiesPacket.TYPE,
                UpdateFluidPropertiesPacket.STREAM_CODEC,
                ClientPayloadHandler::handleUpdateFluidProperties
        );
    }
}
