package com.goodrat.client.modules.Module;

import net.minecraft.client.MinecraftClient;

public abstract class Module {

    protected final MinecraftClient mc = MinecraftClient.getInstance();

    private final String name;
    private final String description;
    private final Category category;
    private boolean enabled = false;
    private int keybind = -1; // GLFW key code, -1 = none

    public enum Category {
        MOVEMENT, RENDER, PLAYER, HUD, MISC
    }

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public Module(String name, String description, Category category, int keybind) {
        this(name, description, category);
        this.keybind = keybind;
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick(MinecraftClient client) {}

    public void toggle() {
        enabled = !enabled;
        if (enabled) onEnable();
        else onDisable();
    }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) onEnable(); else onDisable();
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public Category getCategory() { return category; }
    public int getKeybind() { return keybind; }
    public void setKeybind(int key) { this.keybind = key; }
}
