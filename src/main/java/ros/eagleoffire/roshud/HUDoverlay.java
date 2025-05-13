package ros.eagleoffire.roshud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ROSHUD.MODID, value = Dist.CLIENT)
public class HUDoverlay {
    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGuiOverlayEvent.Pre event){
        if(event.getOverlay().id().getPath().equals("player_health")) {
            event.setCanceled(true);

            Minecraft mc = Minecraft.getInstance();
            GuiGraphics guiGraphics = event.getGuiGraphics();

            ResourceLocation HEALTH_BAR = new ResourceLocation(ROSHUD.MODID, "textures/gui/interfacevie/emptylife.png");
            ResourceLocation GREEN_HEALTH = new ResourceLocation(ROSHUD.MODID, "textures/gui/interfacevie/greenlife.png");

            int width = event.getGuiGraphics().guiWidth();
            int height = (int)(width / 24.07142857142857);

            guiGraphics.blit(GREEN_HEALTH, 0, 0, 0, 0, width, height, width, height);
            guiGraphics.blit(HEALTH_BAR, 0, 0, 0, 0, width, height, width, height);
        }
    }
}
