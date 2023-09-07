package com.gliesestudio.spawnprotect;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class SpawnProtectCommand implements CommandExecutor, TabCompleter {

    private final Config config;
    private final Logger log = Bukkit.getLogger();

    private SpawnProtect plugin;

    public SpawnProtectCommand(SpawnProtect plugin) {
        this.config = plugin.config;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("setspawn")) {
            // Check if the sender has permission to use the /setspawn command
            if (!sender.hasPermission("spawnprotect.setspawn")) {
                sender.sendMessage("You don't have permission to use this command.");
                return true;
            }

            if (args.length != 6) {
                sender.sendMessage("Invalid usage. Correct format: /setspawn x1 y1 z1 x2 y2 z2");
                return true;
            }

            try {
                log.info("all args: " + Arrays.toString(args));
                int x1 = Integer.parseInt(args[0]);
                int y1 = Integer.parseInt(args[1]);
                int z1 = Integer.parseInt(args[2]);
                int x2 = Integer.parseInt(args[3]);
                int y2 = Integer.parseInt(args[4]);
                int z2 = Integer.parseInt(args[5]);

                SpawnPosition spawnPosition = new SpawnPosition(x1, y1, z1, x2, y2, z2);
                config.setSpawnPosition(spawnPosition);
                if (ApplicationConfig.saveConfig(config)) {
                    plugin.config = config;
                    sender.sendMessage("Spawn protection area set successfully.");
                }
                else sender.sendMessage("Failed to save config!");

            } catch (NumberFormatException e) {
                sender.sendMessage("Invalid coordinate format. Please provide valid integers for x1, y1, z1, x2, y2, and z2.");
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, Command cmd, @NotNull String alias, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setspawn")) {
            if (args.length == 1) {
                return List.of("~");
            } else if (args.length == 2) {
                return List.of("~", "-64");
            } else if (args.length == 3) {
                return List.of("~");
            } else if (args.length == 4) {
                return List.of("~");
            } else if (args.length == 5) {
                return List.of("~", "319");
            } else if (args.length == 6) {
                return List.of("~");
            }
        }

        return null;
    }

}