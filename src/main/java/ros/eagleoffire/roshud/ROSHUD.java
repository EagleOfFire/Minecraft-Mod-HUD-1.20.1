package ros.eagleoffire.roshud;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerLifecycleEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import ros.eagleoffire.roshud.config.ModClientConfigs;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ROSHUD.MODID)
public class ROSHUD {
    public static final String MODID = "roshud";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ROSHUD() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ModClientConfigs.SPEC, "roshud-client.toml");

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
        });
    }
}