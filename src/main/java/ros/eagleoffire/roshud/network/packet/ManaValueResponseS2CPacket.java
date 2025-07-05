package ros.eagleoffire.roshud.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaValueResponseS2CPacket {
    private final String playerName;
    private final double mana;

    public ManaValueResponseS2CPacket(FriendlyByteBuf buf) {
        this.playerName = buf.readUtf();
        this.mana = buf.readDouble();
    }

    public ManaValueResponseS2CPacket(String playerName, double mana) {
        this.playerName = playerName;
        this.mana = mana;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.playerName);
        buf.writeDouble(this.mana);
    }

    public static void handle(ManaValueResponseS2CPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            System.out.println("Received mana value for " + msg.playerName + ": " + msg.mana);
        });
        ctx.get().setPacketHandled(true);
    }
}
