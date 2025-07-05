package ros.eagleoffire.roshud.event;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import ros.eagleoffire.roshud.ROSHUD;
import ros.eagleoffire.roshud.commands.ManaValueCommand;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = ROSHUD.MODID)
    public static class ForgeEvents{
        @SubscribeEvent
        public static void onCommandsRegister(RegisterCommandsEvent event){
            new ManaValueCommand(event.getDispatcher());
            ConfigCommand.register(event.getDispatcher());
        }
    }
}
