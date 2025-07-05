package ros.eagleoffire.roshud.network.packet;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.variables.Variable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.core.jmx.Server;
import ros.eagleoffire.roshud.network.ModMessages;

import java.util.function.Supplier;

public class ManaValueQueryC2SPacket {
    public final String playerName;

    public ManaValueQueryC2SPacket(FriendlyByteBuf buf){
        this.playerName = buf.readUtf();
    }

    public ManaValueQueryC2SPacket(String playerName){
        this.playerName = playerName;
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeUtf(this.playerName);
    }

    public static void handle(ManaValueQueryC2SPacket msg, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            ServerPlayer serverPlayer = ctx.get().getSender();
            if(serverPlayer != null){
                Variable var = MagicSpells.getVariableManager().getVariable("meta_mana");
                if (var != null) {
                    Object val = var.getValue(msg.playerName);
                    double mana = (val instanceof Number) ? ((Number) val).doubleValue() : 0;

                    ManaValueResponseS2CPacket response = new ManaValueResponseS2CPacket(msg.playerName, mana);
                    ModMessages.sendToClients(response);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
