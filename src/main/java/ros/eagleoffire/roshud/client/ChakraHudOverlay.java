package ros.eagleoffire.roshud.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import ros.eagleoffire.magicbridge.MagicBridge;
import ros.eagleoffire.magicbridge.api.SpellAPI;
import ros.eagleoffire.roshud.ROSHUD;

public class ChakraHudOverlay {
    private static final ResourceLocation EMPTY_CHAKRA = new ResourceLocation(ROSHUD.MODID,
            "textures/gui/interfacevie/chakrabar.png");
    private static final ResourceLocation FILLED_CHAKRA = new ResourceLocation(ROSHUD.MODID,
            "textures/gui/interfacevie/chakrabarfull.png");

    static final int chakra_outline_width = 200;
    static final int chakra_outline_height = 12;
    static final int chakra_width = 195;
    static final int chakra_height = 6;
    static float chakraRatio = 0;

    public static final IGuiOverlay CHAKRA_HUD = ((gui, guiGraphics, partialTick, width, height) -> {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player != null) {
            Player player = mc.player;
            chakraRatio = SpellAPI.getMana(player.getUUID()) / (float) SpellAPI.getMaxMana(player.getUUID());
        }

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, FILLED_CHAKRA);
        guiGraphics.blit(FILLED_CHAKRA, 3, 19, 0, 0, (int) (chakra_width * chakraRatio), chakra_height, chakra_width, chakra_height);

        RenderSystem.setShaderTexture(0, EMPTY_CHAKRA);
        guiGraphics.blit(EMPTY_CHAKRA, 3, 16, 0, 0, chakra_outline_width, chakra_outline_height, chakra_outline_width, chakra_outline_height);
    });
}
