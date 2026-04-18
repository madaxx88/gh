package com.goodrat.client.mixin;

import com.goodrat.client.GoodRatClient;
import com.goodrat.client.modules.impl.render.Zoom;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    private void onGetFov(net.minecraft.client.render.Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> cir) {
        Zoom zoomModule = (Zoom) GoodRatClient.moduleManager.getModuleByName("Zoom");
        if (zoomModule != null && zoomModule.isEnabled() && Zoom.isZooming) {
            cir.setReturnValue((double) Zoom.zoomFov);
        }
    }
}
