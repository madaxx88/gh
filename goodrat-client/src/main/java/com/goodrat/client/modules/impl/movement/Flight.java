package com.goodrat.client.modules.impl.movement;

import com.goodrat.client.modules.Module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerAbilities;
import org.lwjgl.glfw.GLFW;

public class Flight extends Module {

    public Flight() {
        super("Flight", "Allows creative-style flight in survival", Category.MOVEMENT, GLFW.GLFW_KEY_F);
    }

    @Override
    public void onEnable() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;
        PlayerAbilities abilities = mc.player.getAbilities();
        abilities.allowFlying = true;
        abilities.flying = true;
        mc.player.sendAbilitiesUpdate();
    }

    @Override
    public void onDisable() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.player.isCreative() || mc.player.isSpectator()) return;
        PlayerAbilities abilities = mc.player.getAbilities();
        abilities.allowFlying = false;
        abilities.flying = false;
        mc.player.sendAbilitiesUpdate();
    }
}
