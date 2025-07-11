package ros.eagleoffire.roshud.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import ros.eagleoffire.roshud.ROSHUD;
import ros.eagleoffire.roshud.network.packet.ManaValueQueryC2SPacket;
import ros.eagleoffire.roshud.network.packet.ManaValueResponseS2CPacket;

public class ModMessages {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;
    private static int id(){
        return packetId++;
    }

    public static void register(){
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(ROSHUD.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ManaValueQueryC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ManaValueQueryC2SPacket::new)
                .encoder(ManaValueQueryC2SPacket::toBytes)
                .consumerMainThread(ManaValueQueryC2SPacket::handle)
                .add();

        net.messageBuilder(ManaValueResponseS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ManaValueResponseS2CPacket::new)
                .encoder(ManaValueResponseS2CPacket::toBytes)
                .consumerMainThread(ManaValueResponseS2CPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message){
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
