package com.goodrat.client.modules.impl.movement;

import com.goodrat.client.modules.Module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class NoFall extends Module {

    public NoFall() {
        super("NoFall", "Prevents fall damage", Category.MOVEMENT);
    }

    @Override
    public void onTick(MinecraftClient client) {
        if (client.player == null) return;
        // Apply feather falling effect continuously so no fall damage is taken
        client.player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.SLOW_FALLING, 40, 0, false, false, false));
    }

    @Override
    public void onDisable() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;
        mc.player.removeStatusEffect(StatusEffects.SLOW_FALLING);
    }
}
