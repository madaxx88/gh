package com.goodrat.client.modules.impl.render;

import com.goodrat.client.modules.Module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class Fullbright extends Module {

    public Fullbright() {
        super("Fullbright", "Makes everything fully lit - no darkness", Category.RENDER);
    }

    @Override
    public void onTick(MinecraftClient client) {
        if (client.player == null) return;
        client.player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.NIGHT_VISION, 300, 0, false, false, false));
    }

    @Override
    public void onDisable() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;
        mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
    }
}
