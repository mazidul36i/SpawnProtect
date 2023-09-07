package com.gliesestudio.spawnprotect;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.Bukkit;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

public class ApplicationConfig {

    private final Logger log = Bukkit.getLogger();

    public Config getConfig() {
        String configFilePath = StringLiterals.PLUGIN_PATH + File.separator + "config.yml";

        if (!isConfigFileExists()) {
            log.info("Generating customizable config.yml file...");
            try {
                InputStream inputStream = ApplicationConfig.class.getClassLoader().getResourceAsStream("config.yml");

                if (!new File(new File(configFilePath).getParent()).exists()) {
                    if (!new File(new File(configFilePath).getParent()).mkdir()) return null;
                }

                if (inputStream == null) throw new RuntimeException("Failed to read config.yml!");
                Files.copy(inputStream, Path.of(configFilePath), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return readConfig();
    }

    public static boolean saveConfig(Config config) {
        Yaml yaml = new Yaml();

        String configYml = yaml.dumpAs(config, Tag.MAP, DumperOptions.FlowStyle.BLOCK);
        Bukkit.getLogger().info("config: " + configYml);

        try (FileWriter fileWriter = new FileWriter(StringLiterals.PLUGIN_PATH + File.separator + "config.yml")) {
            fileWriter.write(configYml);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isConfigFileExists() {
        File configFile = new File(StringLiterals.PLUGIN_PATH + File.separator + "config.yml");
        return (configFile.exists());
    }

    private Config readConfig() {
        String configPath = StringLiterals.PLUGIN_PATH + File.separator + "config.yml";
        try {
            log.info("Reading config.yml...");
            Yaml yaml = new Yaml();
            FileInputStream inputStream = new FileInputStream(configPath);
            Object configJson = yaml.load(inputStream);

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
            return mapper.convertValue(configJson, Config.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
