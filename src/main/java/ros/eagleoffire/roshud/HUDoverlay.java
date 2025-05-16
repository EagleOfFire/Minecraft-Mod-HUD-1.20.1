package ros.eagleoffire.roshud;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ROSHUD.MODID, value = Dist.CLIENT)
public class HUDoverlay {

    static ResourceLocation HEALTH_BAR = new ResourceLocation(ROSHUD.MODID, "textures/gui/interfacevie/emptylife.png");
    static ResourceLocation GREEN_HEALTH = new ResourceLocation(ROSHUD.MODID, "textures/gui/interfacevie/greenlife.png");
    static ResourceLocation HUNGER_BAR = new ResourceLocation(ROSHUD.MODID, "textures/gui/interfacevie/foodbar.png");
    static ResourceLocation HUNGER_UNIT = new ResourceLocation(ROSHUD.MODID, "textures/gui/interfacevie/food.png");
    static ResourceLocation HUNGER_UNIT_HALF = new ResourceLocation(ROSHUD.MODID, "textures/gui/interfacevie/fooddemi.png");
    static ResourceLocation HUNGER_STATE = new ResourceLocation(ROSHUD.MODID, "textures/gui/interfacevie/eaten.png");

    static int health_outline_width = 200;
    static int health_outline_height = 12;
    static int health_width = 195;
    static int health_height = 6;
    static int hunger_bar_width = 200;
    static int hunger_bar_height = 12;
    static int foodLevel = 0;
    static float healthRatio = 0;
    static int unit_width = 16;
    static int unit_heihgt = 3;
    static int hunger_state_width = 20;
    static int hunger_state_height = hunger_state_width / 2;

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGuiOverlayEvent.Pre event) {
        if ((event.getOverlay().id().getPath().equals("player_health") || (event.getOverlay().id().getPath().equals("food_level")))) {
            //event.setCanceled(true);

            GuiGraphics guiGraphics = event.getGuiGraphics();
            Minecraft mc = Minecraft.getInstance();

            if (mc.player != null) {
                Player player = mc.player;

                foodLevel = player.getFoodData().getFoodLevel();
                healthRatio = player.getHealth() / player.getMaxHealth();
            }

            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.blendFuncSeparate(
                    GlStateManager.SourceFactor.SRC_ALPHA,
                    GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                    GlStateManager.SourceFactor.ONE,
                    GlStateManager.DestFactor.ZERO
            );
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            guiGraphics.blit(GREEN_HEALTH, 3, 5, 0, 0, (int)(health_width*healthRatio), health_height, health_width, health_height);
            guiGraphics.blit(HEALTH_BAR, 2, 2, 0, 0, health_outline_width, health_outline_height, health_outline_width, health_outline_height);

            guiGraphics.blit(HUNGER_BAR, 3, 16, 0, 0, hunger_bar_width, hunger_bar_height, hunger_bar_width, hunger_bar_height);
            guiGraphics.blit(HUNGER_STATE, 7, 16, 0, 0, hunger_state_width, hunger_state_height, hunger_state_width, hunger_state_height);

            for (int i = 0; i <= 20; i++) {
                if ((foodLevel >= i) && (i != 0)) {
                    if (i % 2 == 0) {
                        guiGraphics.blit(HUNGER_UNIT, 22 + (i * 8), 18, 0, 0, unit_width, unit_heihgt, unit_width, unit_heihgt);
                    } else {
                        if (i == foodLevel) {
                            guiGraphics.blit(HUNGER_UNIT_HALF, 30 + (i * 8), 18, 0, 0, unit_width / 2, unit_heihgt, unit_width / 2, unit_heihgt);
                        }
                    }
                }
            }

            RenderSystem.depthMask(true);
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            RenderSystem.disableBlend();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
