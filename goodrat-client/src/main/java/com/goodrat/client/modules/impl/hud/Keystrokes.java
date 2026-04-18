package com.goodrat.client.modules.impl.hud;

import com.goodrat.client.modules.Module.Module;

public class Keystrokes extends Module {
    public Keystrokes() {
        super("Keystrokes", "Shows WASD + click keystrokes on screen", Category.HUD);
    }
    // Rendering handled in HudRenderer
}
