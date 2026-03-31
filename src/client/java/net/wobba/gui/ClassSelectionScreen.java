package net.wobba.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.wobba.classSystem.gui.ClassSelectionMenu;
import org.jspecify.annotations.NonNull;

public class ClassSelectionScreen extends AbstractContainerScreen<ClassSelectionMenu> {

    private static final Identifier CHEST_TEXTURE =
            Identifier.withDefaultNamespace("textures/gui/container/generic_54.png");

    public ClassSelectionScreen(ClassSelectionMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageHeight = 60;
        this.inventoryLabelY = 1000;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CHEST_TEXTURE, x, y, 0, 0, this.imageWidth, 17, 256, 256);      // top border
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CHEST_TEXTURE, x, y + 17, 0, 17, this.imageWidth, 18, 256, 256); // row 1
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CHEST_TEXTURE, x, y + 35, 0, 17, this.imageWidth, 18, 256, 256); // row 2
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CHEST_TEXTURE, x, y + 53, 0, 125, this.imageWidth, 7, 256, 256); // bottom border
    }

    @Override
    public void render(@NonNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}