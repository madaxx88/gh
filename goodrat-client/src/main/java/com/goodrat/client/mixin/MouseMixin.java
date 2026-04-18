package com.goodrat.client.mixin;

import com.goodrat.client.GoodRatClient;
import com.goodrat.client.modules.impl.render.Zoom;
import com.goodrat.client.modules.impl.hud.CPSCounter;
import net.minecraft.client.Mouse;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {

    @Inject(method = "onMouseButton", at = @At("HEAD"))
    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        if (action == GLFW.GLFW_PRESS) {
            if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) CPSCounter.registerLeftClick();
            if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) CPSCounter.registerRightClick();
        }
    }
}
