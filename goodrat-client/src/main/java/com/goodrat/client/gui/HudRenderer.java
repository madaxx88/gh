package com.goodrat.client.gui;

import com.goodrat.client.GoodRatClient;
import com.goodrat.client.modules.Module.Module;
import com.goodrat.client.modules.impl.hud.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class HudRenderer {

    private static final int WHITE     = 0xFFFFFFFF;
    private static final int YELLOW    = 0xFFFFD700;
    private static final int CYAN      = 0xFF00E5FF;
    private static final int DARK_BG   = 0x88000000;
    private static final int ACCENT    = 0xFF4FC3F7; // GoodRat light blue

    public void render(DrawContext ctx) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.world == null) return;
        if (mc.options.hudHidden) return;

        TextRenderer tr = mc.textRenderer;
        int sw = mc.getWindow().getScaledWidth();
        int sh = mc.getWindow().getScaledHeight();

        // ── Watermark ──────────────────────────────────────────────
        String watermark = "§bGoodRat §fClient §7v1.0.0";
        ctx.fill(2, 2, 4 + tr.getWidth(watermark) - 2, 12, DARK_BG);
        ctx.drawTextWithShadow(tr, watermark, 4, 3, WHITE);

        // ── Active modules list (top-right) ────────────────────────
        List<Module> active = GoodRatClient.moduleManager.getModules()
                .stream().filter(Module::isEnabled).toList();
        int y = 3;
        for (Module m : active) {
            String name = m.getName();
            int x = sw - tr.getWidth(name) - 4;
            ctx.fill(x - 2, y - 1, sw - 2, y + 9, DARK_BG);
            ctx.drawTextWithShadow(tr, "§b" + name, x, y, WHITE);
            y += 11;
        }

        // ── FPS Display ────────────────────────────────────────────
        if (isEnabled(FPSDisplay.class)) {
            String fps = "§7FPS: §f" + mc.getCurrentFps();
            ctx.fill(2, 14, 4 + tr.getWidth(fps), 24, DARK_BG);
            ctx.drawTextWithShadow(tr, fps, 4, 15, WHITE);
        }

        // ── Coordinates ────────────────────────────────────────────
        if (isEnabled(CoordinateDisplay.class)) {
            BlockPos pos = mc.player.getBlockPos();
            String coords = String.format("§7X:§f%d §7Y:§f%d §7Z:§f%d", pos.getX(), pos.getY(), pos.getZ());
            ctx.fill(2, 26, 4 + tr.getWidth(coords), 36, DARK_BG);
            ctx.drawTextWithShadow(tr, coords, 4, 27, WHITE);
        }

        // ── CPS Counter ────────────────────────────────────────────
        if (isEnabled(CPSCounter.class)) {
            String cps = "§7LMB: §f" + CPSCounter.getLeftCPS() + " §7RMB: §f" + CPSCounter.getRightCPS();
            ctx.fill(2, 38, 4 + tr.getWidth(cps), 48, DARK_BG);
            ctx.drawTextWithShadow(tr, cps, 4, 39, WHITE);
        }

        // ── Keystrokes (bottom-center-ish) ─────────────────────────
        if (isEnabled(Keystrokes.class)) {
            renderKeystrokes(ctx, tr, mc, sw, sh);
        }

        // ── Armor Status ───────────────────────────────────────────
        if (isEnabled(ArmorStatus.class)) {
            renderArmor(ctx, tr, mc, sw, sh);
        }
    }

    private void renderKeystrokes(DrawContext ctx, TextRenderer tr, MinecraftClient mc, int sw, int sh) {
        int kx = sw / 2 + 100;
        int ky = sh - 70;
        int ks = 16; // key size
        int gap = 2;

        boolean w = mc.options.forwardKey.isPressed();
        boolean a = mc.options.leftKey.isPressed();
        boolean s = mc.options.backKey.isPressed();
        boolean d = mc.options.rightKey.isPressed();
        boolean lmb = mc.options.attackKey.isPressed();
        boolean rmb = mc.options.useKey.isPressed();

        drawKey(ctx, tr, "W", kx + ks + gap, ky, ks, w);
        drawKey(ctx, tr, "A", kx, ky + ks + gap, ks, a);
        drawKey(ctx, tr, "S", kx + ks + gap, ky + ks + gap, ks, s);
        drawKey(ctx, tr, "D", kx + (ks + gap) * 2, ky + ks + gap, ks, d);
        drawKey(ctx, tr, "LMB", kx, ky + (ks + gap) * 2, ks * 2, lmb);
        drawKey(ctx, tr, "RMB", kx + ks * 2 + gap * 2, ky + (ks + gap) * 2, ks * 2, rmb);
    }

    private void drawKey(DrawContext ctx, TextRenderer tr, String label, int x, int y, int size, boolean pressed) {
        int bg = pressed ? 0xAA4FC3F7 : 0xAA000000;
        int border = pressed ? ACCENT : 0xFF444444;
        ctx.fill(x, y, x + size, y + size, bg);
        ctx.fill(x, y, x + size, y + 1, border);
        ctx.fill(x, y + size - 1, x + size, y + size, border);
        ctx.fill(x, y, x + 1, y + size, border);
        ctx.fill(x + size - 1, y, x + size, y + size, border);
        int lw = tr.getWidth(label);
        ctx.drawTextWithShadow(tr, label, x + (size - lw) / 2, y + (size - 8) / 2, WHITE);
    }

    private void renderArmor(DrawContext ctx, TextRenderer tr, MinecraftClient mc, int sw, int sh) {
        int ax = sw - 90;
        int ay = sh - 60;
        Iterable<ItemStack> armor = mc.player.getArmorItems();
        int i = 0;
        for (ItemStack stack : armor) {
            if (stack.isEmpty()) { i++; continue; }
            int damage = stack.getMaxDamage() - stack.getDamage();
            int maxDamage = stack.getMaxDamage();
            int pct = maxDamage == 0 ? 100 : (damage * 100 / maxDamage);
            int color = pct > 50 ? 0xFF55FF55 : pct > 25 ? 0xFFFFAA00 : 0xFFFF5555;
            String text = stack.getName().getString().substring(0, Math.min(4, stack.getName().getString().length())) + " " + pct + "%";
            ctx.drawTextWithShadow(tr, text, ax, ay + i * 10, color);
            i++;
        }
    }

    private boolean isEnabled(Class<? extends Module> cls) {
        return GoodRatClient.moduleManager.getModules().stream()
                .filter(m -> m.getClass() == cls)
                .anyMatch(Module::isEnabled);
    }
}
