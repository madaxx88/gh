package com.goodrat.client.modules.impl.hud;

import com.goodrat.client.modules.Module.Module;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.List;

public class CPSCounter extends Module {

    private static final List<Long> leftClicks = new ArrayList<>();
    private static final List<Long> rightClicks = new ArrayList<>();

    public CPSCounter() {
        super("CPSCounter", "Shows your clicks per second", Category.HUD);
    }

    public static void registerLeftClick() {
        leftClicks.add(System.currentTimeMillis());
    }

    public static void registerRightClick() {
        rightClicks.add(System.currentTimeMillis());
    }

    public static int getLeftCPS() {
        long now = System.currentTimeMillis();
        leftClicks.removeIf(t -> now - t > 1000);
        return leftClicks.size();
    }

    public static int getRightCPS() {
        long now = System.currentTimeMillis();
        rightClicks.removeIf(t -> now - t > 1000);
        return rightClicks.size();
    }
}
