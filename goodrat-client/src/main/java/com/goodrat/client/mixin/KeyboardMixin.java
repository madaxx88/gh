package com.goodrat.client.mixin;

import com.goodrat.client.GoodRatClient;
import com.goodrat.client.modules.Module.Module;
import com.goodrat.client.modules.impl.render.Zoom;
import com.goodrat.client.gui.ModuleScreen;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {

    @Inject(method = "onKey", at = @At("HEAD"))
    private void onKey(long window, int key, int scancode, int action, int mods, CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.currentScreen != null) return; // Don't fire when in a GUI

        // Open GoodRat GUI with Right Shift
        if (key == GLFW.GLFW_KEY_RIGHT_SHIFT && action == GLFW.GLFW_PRESS) {
            mc.execute(() -> mc.setScreen(new ModuleScreen()));
            return;
        }

        // Zoom hold logic (C key)
        Module zoomModule = GoodRatClient.moduleManager.getModuleByName("Zoom");
        if (zoomModule != null && zoomModule.isEnabled()) {
            if (key == GLFW.GLFW_KEY_C) {
                Zoom.isZooming = (action != GLFW.GLFW_RELEASE);
            }
        }

        // Module keybind toggles (on key press only)
        if (action == GLFW.GLFW_PRESS) {
            for (Module m : GoodRatClient.moduleManager.getModules()) {
                if (m.getKeybind() == key) {
                    // Zoom is hold-only, not a toggle
                    if (m.getName().equals("Zoom")) continue;
                    m.toggle();
                }
            }
        }
    }
}
