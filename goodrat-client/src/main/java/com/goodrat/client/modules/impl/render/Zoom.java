package com.goodrat.client.modules.impl.render;

import com.goodrat.client.modules.Module.Module;
import org.lwjgl.glfw.GLFW;

public class Zoom extends Module {

    public static float zoomFov = 15.0f;
    public static boolean isZooming = false;

    public Zoom() {
        super("Zoom", "Hold C to zoom in like a spyglass", Category.RENDER, GLFW.GLFW_KEY_C);
    }

    // FOV manipulation is handled via GameRendererMixin
}
