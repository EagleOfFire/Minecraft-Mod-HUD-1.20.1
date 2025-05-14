package ros.eagleoffire.roshud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
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

            GuiGraphics guiGraphics = event.getGuiGraphics();

            ResourceLocation HEALTH_BAR = new ResourceLocation(ROSHUD.MODID, "textures/gui/interfacevie/emptylife.png");
            ResourceLocation GREEN_HEALTH = new ResourceLocation(ROSHUD.MODID, "textures/gui/interfacevie/greenlife.png");

            //int width = event.getGuiGraphics().guiWidth();
            int width = 200;
            int height = 10;

            guiGraphics.blit(GREEN_HEALTH, 2, 2, 0, 0, width, height, width, height);
            guiGraphics.blit(HEALTH_BAR, 2, 2, 0, 0, width, height, width, height);
        }else if(event.getOverlay().id().getPath().equals("food_level")) {
            event.setCanceled(true);
            int foodLevel = 0;

            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null){
                Player player = mc.player;

                foodLevel = player.getFoodData().getFoodLevel();
            }
            GuiGraphics guiGraphics = event.getGuiGraphics();

            ResourceLocation HUNGER_BAR = new ResourceLocation(ROSHUD.MODID, "textures/gui/interfacevie/foodbar.png");
            ResourceLocation HUNGER_UNIT = new ResourceLocation(ROSHUD.MODID, "textures/gui/interfacevie/food.png");
            ResourceLocation HUNGER_UNIT_HALF = new ResourceLocation(ROSHUD.MODID, "textures/gui/interfacevie/fooddemi.png");

            int width = 200;
            int height = 8;
            int unit_width = 19;
            int unit_heihgt = 5;

            guiGraphics.blit(HUNGER_BAR, 2, 14, 0, 0, width, height, width, height);
            for(int i=0; i<20; i++){
                if (foodLevel > i){
                    if (i % 2 == 0){
                        guiGraphics.blit(HUNGER_UNIT, 2 + i * 10, 14, 0, 0, unit_width, unit_heihgt, unit_width, unit_heihgt);
                    }else{
                        guiGraphics.blit(HUNGER_UNIT_HALF, 2 + i * 10, 14, 0, 0, unit_width/2, unit_heihgt, unit_width/2, unit_heihgt);
                    }
                }else {
                    break;
                }
            }
        }

    }
}
