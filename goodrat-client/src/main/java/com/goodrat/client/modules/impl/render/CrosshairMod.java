package com.goodrat.client.modules.impl.render;

import com.goodrat.client.modules.Module.Module;

public class CrosshairMod extends Module {

    public enum CrosshairStyle { DEFAULT, DOT, CROSS, CIRCLE }
    public static CrosshairStyle style = CrosshairStyle.CROSS;

    public CrosshairMod() {
        super("CrosshairMod", "Customize your crosshair style", Category.RENDER);
    }

    // Rendering handled in InGameHudMixin
}
