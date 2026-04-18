package com.goodrat.client.modules;

import com.goodrat.client.modules.Module.Module;
import com.goodrat.client.modules.impl.movement.Flight;
import com.goodrat.client.modules.impl.movement.Speed;
import com.goodrat.client.modules.impl.movement.NoFall;
import com.goodrat.client.modules.impl.movement.FreeLook;
import com.goodrat.client.modules.impl.render.Fullbright;
import com.goodrat.client.modules.impl.render.Zoom;
import com.goodrat.client.modules.impl.render.CrosshairMod;
import com.goodrat.client.modules.impl.player.NoHunger;
import com.goodrat.client.modules.impl.hud.Keystrokes;
import com.goodrat.client.modules.impl.hud.CPSCounter;
import com.goodrat.client.modules.impl.hud.FPSDisplay;
import com.goodrat.client.modules.impl.hud.ArmorStatus;
import com.goodrat.client.modules.impl.hud.CoordinateDisplay;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        // Movement
        register(new Flight());
        register(new Speed());
        register(new NoFall());
        register(new FreeLook());

        // Render
        register(new Fullbright());
        register(new Zoom());
        register(new CrosshairMod());

        // Player
        register(new NoHunger());

        // HUD
        register(new Keystrokes());
        register(new CPSCounter());
        register(new FPSDisplay());
        register(new ArmorStatus());
        register(new CoordinateDisplay());
    }

    private void register(Module module) {
        modules.add(module);
    }

    public void onTick(MinecraftClient client) {
        for (Module module : modules) {
            if (module.isEnabled()) {
                module.onTick(client);
            }
        }
    }

    public List<Module> getModules() { return modules; }

    public Module getModuleByName(String name) {
        return modules.stream()
                .filter(m -> m.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public List<Module> getModulesByCategory(Module.Category category) {
        return modules.stream()
                .filter(m -> m.getCategory() == category)
                .toList();
    }
}
