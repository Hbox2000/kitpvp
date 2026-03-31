package net.wobba;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.wobba.gui.ClassPreviewScreen;
import net.wobba.gui.ClassSelectionScreen;

public class KitpvpClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MenuScreens.register(ModMenuTypes.CLASS_SELECTION, ClassSelectionScreen::new);
		MenuScreens.register(ModMenuTypes.CLASS_PREVIEW, ClassPreviewScreen::new);
	}
}