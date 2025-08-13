package cn.qihuang02.fluidjs.event;

import cn.qihuang02.fluidjs.FluidJS;
import cn.qihuang02.fluidjs.data.FluidPropertiesManager;
import cn.qihuang02.fluidjs.network.UpdateFluidPropertiesPacket;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = FluidJS.MODID)
public class PlayerEvents {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.@NotNull PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            UpdateFluidPropertiesPacket packet = new UpdateFluidPropertiesPacket(FluidPropertiesManager.getOverrides());
            PacketDistributor.sendToPlayer(player, packet);
        }
    }
}
