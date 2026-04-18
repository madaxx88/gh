package com.goodrat.client.modules.impl.player;

import com.goodrat.client.modules.Module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class NoHunger extends Module {

    public NoHunger() {
        super("NoHunger", "Prevents hunger from depleting", Category.PLAYER);
    }

    @Override
    public void onTick(MinecraftClient client) {
        if (client.player == null) return;
        // Saturation effect keeps food bar full
        client.player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.SATURATION, 40, 99, false, false, false));
    }
}
