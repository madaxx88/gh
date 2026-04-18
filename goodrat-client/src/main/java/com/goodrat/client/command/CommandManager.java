package com.goodrat.client.command;

import com.goodrat.client.GoodRatClient;
import com.goodrat.client.modules.Module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class CommandManager {

    public static final String PREFIX = ".";

    /**
     * Called when the player sends a chat message.
     * Returns true if the message was a command (and should be cancelled).
     */
    public boolean handleCommand(String message) {
        if (!message.startsWith(PREFIX)) return false;

        String[] args = message.substring(PREFIX.length()).trim().split(" ");
        if (args.length == 0 || args[0].isEmpty()) return true;

        String cmd = args[0].toLowerCase();

        switch (cmd) {
            case "toggle", "t" -> {
                if (args.length < 2) {
                    sendFeedback("§cUsage: .toggle <module>");
                    return true;
                }
                Module m = GoodRatClient.moduleManager.getModuleByName(args[1]);
                if (m == null) {
                    sendFeedback("§cModule not found: §f" + args[1]);
                } else {
                    m.toggle();
                    sendFeedback("§b" + m.getName() + " §7-> " + (m.isEnabled() ? "§aON" : "§cOFF"));
                }
            }
            case "modules", "mods" -> {
                sendFeedback("§b--- GoodRat Modules ---");
                for (Module.Category cat : Module.Category.values()) {
                    var mods = GoodRatClient.moduleManager.getModulesByCategory(cat);
                    if (mods.isEmpty()) continue;
                    StringBuilder sb = new StringBuilder("§7[" + cat.name() + "] §f");
                    for (Module m : mods) {
                        sb.append(m.isEnabled() ? "§a" : "§c").append(m.getName()).append("§f ");
                    }
                    sendFeedback(sb.toString());
                }
            }
            case "help" -> {
                sendFeedback("§b--- GoodRat Commands ---");
                sendFeedback("§f.toggle <module> §7- Toggle a module");
                sendFeedback("§f.modules §7- List all modules");
                sendFeedback("§f.help §7- Show this help");
            }
            default -> sendFeedback("§cUnknown command. Type §f.help §cfor help.");
        }

        return true;
    }

    private void sendFeedback(String msg) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player != null) {
            mc.player.sendMessage(Text.literal(msg), false);
        }
    }
}
