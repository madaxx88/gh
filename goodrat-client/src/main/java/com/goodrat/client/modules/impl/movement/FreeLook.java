package com.goodrat.client.modules.impl.movement;

import com.goodrat.client.modules.Module.Module;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public class FreeLook extends Module {

    public FreeLook() {
        super("FreeLook", "Hold V to look around without moving your character", Category.MOVEMENT, GLFW.GLFW_KEY_V);
    }

    // FreeLook logic is handled in MouseMixin
    // This module just tracks enabled state
}
