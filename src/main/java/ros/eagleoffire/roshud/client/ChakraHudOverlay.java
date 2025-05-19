package ros.eagleoffire.roshud.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import ros.eagleoffire.roshud.ROSHUD;

public class ChakraHudOverlay {
    private static final ResourceLocation EMPTY_CHAKRA = new ResourceLocation(ROSHUD.MODID,
            "textures/gui/interfacevie/chakrabar.png");
    private static final ResourceLocation FILLED_CHAKRA = new ResourceLocation(ROSHUD.MODID,
            "textures/gui/interfacevie/chakrabarfull.png");

    public static final IGuiOverlay CHAKRA_HUD = ((gui, guiGraphics, partialTick, width, height) -> {
        int chakra_outline_width = 200;
        int chakra_outline_height = 12;
        int chakra_width = 195;
        int chakra_height = 6;
        float chakraRatio = 1;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, EMPTY_CHAKRA);
        guiGraphics.blit(EMPTY_CHAKRA, 3, 16, 0, 0, chakra_outline_width, chakra_outline_height, chakra_outline_width, chakra_outline_height);
        RenderSystem.setShaderTexture(0, FILLED_CHAKRA);
        guiGraphics.blit(FILLED_CHAKRA, 3, 19, 0, 0, (int) (chakra_width * chakraRatio), chakra_height, chakra_width, chakra_height);
    });
}
