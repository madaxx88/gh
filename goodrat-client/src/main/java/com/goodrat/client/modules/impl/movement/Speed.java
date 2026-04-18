package com.goodrat.client.modules.impl.movement;

import com.goodrat.client.modules.Module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class Speed extends Module {

    public Speed() {
        super("Speed", "Gives speed effect while enabled", Category.MOVEMENT);
    }

    @Override
    public void onTick(MinecraftClient client) {
        if (client.player == null) return;
        client.player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.SPEED, 20, 4, false, false, false));
    }

    @Override
    public void onDisable() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;
        mc.player.removeStatusEffect(StatusEffects.SPEED);
    }
}
