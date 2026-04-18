package com.goodrat.client.mixin;

import com.goodrat.client.GoodRatClient;
import com.goodrat.client.modules.impl.render.CrosshairMod;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void onRenderCrosshair(DrawContext context, net.minecraft.client.render.RenderTickCounter tickCounter, CallbackInfo ci) {
        CrosshairMod mod = (CrosshairMod) GoodRatClient.moduleManager.getModuleByName("CrosshairMod");
        if (mod != null && mod.isEnabled()) {
            ci.cancel(); // We draw our own crosshair in HudRenderer
            renderCustomCrosshair(context);
        }
    }

    private void renderCustomCrosshair(DrawContext ctx) {
        net.minecraft.client.MinecraftClient mc = net.minecraft.client.MinecraftClient.getInstance();
        int cx = mc.getWindow().getScaledWidth() / 2;
        int cy = mc.getWindow().getScaledHeight() / 2;
        int color = 0xFFFFFFFF;

        switch (CrosshairMod.style) {
            case DOT -> ctx.fill(cx - 1, cy - 1, cx + 1, cy + 1, color);
            case CROSS -> {
                ctx.fill(cx - 5, cy, cx + 6, cy + 1, color);
                ctx.fill(cx, cy - 5, cx + 1, cy + 6, color);
            }
            case CIRCLE -> {
                for (int deg = 0; deg < 360; deg += 10) {
                    double rad = Math.toRadians(deg);
                    int px = cx + (int)(Math.cos(rad) * 5);
                    int py = cy + (int)(Math.sin(rad) * 5);
                    ctx.fill(px, py, px + 1, py + 1, color);
                }
            }
            default -> {} // DEFAULT: vanilla handles it but we cancelled, so draw simple cross
        }
    }
}
