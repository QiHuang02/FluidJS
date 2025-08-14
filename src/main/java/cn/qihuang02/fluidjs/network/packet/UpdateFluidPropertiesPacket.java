package cn.qihuang02.fluidjs.network.packet;

import cn.qihuang02.fluidjs.FluidJS;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public record UpdateFluidPropertiesPacket(
        Map<ResourceLocation, Map<String, Object>> data
) implements CustomPacketPayload {
    public static final Type<UpdateFluidPropertiesPacket> TYPE = new Type<>(FluidJS.getRL("update_fluid_properties"));

    public UpdateFluidPropertiesPacket(Map<ResourceLocation, Map<String, Object>> data) {
        this.data = data;
    }

    public static final StreamCodec<FriendlyByteBuf, UpdateFluidPropertiesPacket> STREAM_CODEC = CustomPacketPayload.codec(
            UpdateFluidPropertiesPacket::write, UpdateFluidPropertiesPacket::new
    );

    public UpdateFluidPropertiesPacket(FriendlyByteBuf buf) {
        this(readOverrides(buf));
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeVarInt(data.size());
        data.forEach((fluidId, properties) -> {
            buf.writeResourceLocation(fluidId);
            buf.writeVarInt(properties.size());
            properties.forEach((propName, propValue) -> {
                buf.writeUtf(propName);
                if (propValue instanceof Integer) {
                    buf.writeByte(0);
                    buf.writeVarInt((Integer) propValue);
                } else if (propValue instanceof Double) {
                    buf.writeByte(1);
                    buf.writeDouble((Double) propValue);
                } else if (propValue instanceof Boolean) {
                    buf.writeByte(2);
                    buf.writeBoolean((Boolean) propValue);
                } else if (propValue instanceof Rarity) {
                    buf.writeByte(3);
                    buf.writeEnum((Rarity) propValue);
                }
            });
        });
    }

    private static Map<ResourceLocation, Map<String, Object>> readOverrides(FriendlyByteBuf buf) {
        Map<ResourceLocation, Map<String, Object>> overrides = new ConcurrentHashMap<>();
        int fluidMapSize = buf.readVarInt();
        for (int i = 0; i < fluidMapSize; i++) {
            ResourceLocation fluidId = buf.readResourceLocation();
            Map<String, Object> properties = new ConcurrentHashMap<>();
            int propMapSize = buf.readVarInt();
            for (int j = 0; j < propMapSize; j++) {
                String propName = buf.readUtf();
                byte type = buf.readByte();
                Object propValue = switch (type) {
                    case 0 -> buf.readVarInt();
                    case 1 -> buf.readDouble();
                    case 2 -> buf.readBoolean();
                    case 3 -> buf.readEnum(Rarity.class);
                    default -> null;
                };
                if (propValue != null) {
                    properties.put(propName, propValue);
                }
            }
            overrides.put(fluidId, properties);
        }
        return overrides;
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
