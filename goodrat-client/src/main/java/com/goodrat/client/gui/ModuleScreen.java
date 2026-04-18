package com.goodrat.client.gui;

import com.goodrat.client.GoodRatClient;
import com.goodrat.client.modules.Module.Module;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ModuleScreen extends Screen {

    // Layout
    private static final int PANEL_WIDTH  = 120;
    private static final int PANEL_PAD    = 8;
    private static final int ENTRY_H      = 20;
    private static final int HEADER_H     = 24;
    private static final int GAP          = 10;

    // Colors
    private static final int COL_BG       = 0xE0101318;
    private static final int COL_PANEL    = 0xF0181C24;
    private static final int COL_HEADER   = 0xFF0D1117;
    private static final int COL_ACCENT   = 0xFF4FC3F7;
    private static final int COL_ON       = 0xFF2ECC71;
    private static final int COL_OFF      = 0xFF555E6B;
    private static final int COL_HOVER    = 0xFF1E2530;
    private static final int COL_TEXT     = 0xFFECEFF4;
    private static final int COL_SUBTEXT  = 0xFF8892A0;

    private final Module.Category[] categories = Module.Category.values();
    private int hoveredIndex = -1; // flat index across all entries
    private int scrollOffset = 0;

    public ModuleScreen() {
        super(Text.literal("GoodRat Client"));
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        // Dim background
        ctx.fill(0, 0, width, height, COL_BG);

        // Title bar
        String title = "✦  GoodRat Client";
        int titleW = textRenderer.getWidth(title);
        ctx.fill(0, 0, width, 20, COL_HEADER);
        ctx.drawTextWithShadow(textRenderer, "§b" + title, (width - titleW) / 2, 6, COL_TEXT);
        ctx.fill(0, 19, width, 20, COL_ACCENT);

        // Draw category panels
        int totalCols = categories.length;
        int totalW = totalCols * PANEL_WIDTH + (totalCols - 1) * GAP;
        int startX = (width - totalW) / 2;
        int startY = 30;

        hoveredIndex = -1;
        int flatIdx = 0;

        for (int col = 0; col < categories.length; col++) {
            Module.Category cat = categories[col];
            List<Module> mods = GoodRatClient.moduleManager.getModulesByCategory(cat);

            int px = startX + col * (PANEL_WIDTH + GAP);
            int panelH = HEADER_H + mods.size() * ENTRY_H + PANEL_PAD;
            int py = startY;

            // Panel background
            ctx.fill(px, py, px + PANEL_WIDTH, py + panelH, COL_PANEL);
            // Panel top accent line
            ctx.fill(px, py, px + PANEL_WIDTH, py + 2, COL_ACCENT);
            // Category header
            ctx.fill(px, py + 2, px + PANEL_WIDTH, py + HEADER_H, COL_HEADER);
            ctx.drawTextWithShadow(textRenderer, "§b" + cat.name(), px + PANEL_PAD, py + 8, COL_TEXT);

            // Module entries
            for (int row = 0; row < mods.size(); row++) {
                Module m = mods.get(row);
                int ey = py + HEADER_H + row * ENTRY_H;

                boolean hover = mouseX >= px && mouseX < px + PANEL_WIDTH
                        && mouseY >= ey && mouseY < ey + ENTRY_H;
                if (hover) hoveredIndex = flatIdx;

                // Entry background
                if (hover) ctx.fill(px, ey, px + PANEL_WIDTH, ey + ENTRY_H, COL_HOVER);

                // Toggle pill
                int pillX = px + PANEL_WIDTH - 28;
                int pillY = ey + 5;
                boolean on = m.isEnabled();
                ctx.fill(pillX, pillY, pillX + 20, pillY + 10, on ? COL_ON : COL_OFF);
                ctx.fill(on ? pillX + 11 : pillX + 1, pillY + 1,
                         on ? pillX + 19 : pillX + 9, pillY + 9, COL_TEXT);

                // Module name
                String name = m.getName();
                ctx.drawTextWithShadow(textRenderer, name, px + PANEL_PAD, ey + 6, on ? COL_TEXT : COL_SUBTEXT);

                flatIdx++;
            }

            // Panel border
            int borderCol = 0xFF1E2530;
            ctx.fill(px, py, px + 1, py + panelH, borderCol);
            ctx.fill(px + PANEL_WIDTH - 1, py, px + PANEL_WIDTH, py + panelH, borderCol);
            ctx.fill(px, py + panelH - 1, px + PANEL_WIDTH, py + panelH, borderCol);
        }

        // Footer hint
        String hint = "Click to toggle  •  ESC to close";
        ctx.drawTextWithShadow(textRenderer, "§7" + hint, (width - textRenderer.getWidth(hint)) / 2, height - 14, COL_SUBTEXT);

        super.render(ctx, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) return super.mouseClicked(mouseX, mouseY, button);

        int totalCols = categories.length;
        int totalW = totalCols * PANEL_WIDTH + (totalCols - 1) * GAP;
        int startX = (width - totalW) / 2;
        int startY = 30;

        for (int col = 0; col < categories.length; col++) {
            Module.Category cat = categories[col];
            List<Module> mods = GoodRatClient.moduleManager.getModulesByCategory(cat);
            int px = startX + col * (PANEL_WIDTH + GAP);

            for (int row = 0; row < mods.size(); row++) {
                int ey = startY + HEADER_H + row * ENTRY_H;
                if (mouseX >= px && mouseX < px + PANEL_WIDTH
                        && mouseY >= ey && mouseY < ey + ENTRY_H) {
                    mods.get(row).toggle();
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() { return false; } // Game keeps running
}
