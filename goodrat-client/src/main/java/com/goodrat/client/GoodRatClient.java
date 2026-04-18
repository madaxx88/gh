package com.goodrat.client;

import com.goodrat.client.command.CommandManager;
import com.goodrat.client.modules.ModuleManager;
import com.goodrat.client.gui.HudRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoodRatClient implements ClientModInitializer {

    public static final String MOD_NAME = "GoodRat Client";
    public static final String MOD_ID = "goodrat";
    public static final String VERSION = "1.0.0";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static ModuleManager moduleManager;
    public static CommandManager commandManager;
    public static HudRenderer hudRenderer;

    @Override
    public void onInitializeClient() {
        LOGGER.info("[GoodRat] Initializing GoodRat Client v" + VERSION);

        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        hudRenderer = new HudRenderer();

        // Register tick event for modules
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            moduleManager.onTick(client);
        });

        // Register HUD rendering
        HudRenderCallback.EVENT.register((drawContext, tickDeltaManager) -> {
            hudRenderer.render(drawContext);
        });

        LOGGER.info("[GoodRat] Successfully loaded " + moduleManager.getModules().size() + " modules!");
    }
}
