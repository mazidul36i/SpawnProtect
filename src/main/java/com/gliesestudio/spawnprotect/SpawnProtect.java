package com.gliesestudio.spawnprotect;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class SpawnProtect extends JavaPlugin {

    private Logger log = Bukkit.getLogger();
    public Config config;

    @Override
    public void onEnable() {
        displayStartingMessage();
        config = new ApplicationConfig().getConfig();

        new SpawnProtectionListener(this);
        initCommands();
    }

    @Override
    public void onDisable() {
        log.info("Shut down Spawn Protect");
    }

    public void displayStartingMessage() {
        log.info("\u001B[32m╔═══════════════════════════════╗\u001B[0m");
        log.info("\u001B[32m║         Spawn Protect         ║\u001B[0m");
        log.info("\u001B[32m╚═══════════════════════════════╝\u001B[0m");
    }

    private void initCommands() {
        PluginCommand setSpawnCommand = getCommand("setspawn");
        if (setSpawnCommand != null) {
            setSpawnCommand.setExecutor(new SpawnProtectCommand(this));
            setSpawnCommand.setTabCompleter(new SpawnProtectCommand(this));
        }
    }
}
