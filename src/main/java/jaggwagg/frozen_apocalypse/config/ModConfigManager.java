package jaggwagg.frozen_apocalypse.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import jaggwagg.frozen_apocalypse.FrozenApocalypse;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModConfigManager {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void saveConfig(ModConfig config, String filePath) {
        File file = new File(filePath);

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            try (FileWriter writer = new FileWriter(file)) {
                Gson gson = new GsonBuilder()
                        .disableHtmlEscaping()
                        .setPrettyPrinting()
                        .serializeNulls()
                        .create();
                gson.toJson(config, writer);
                FrozenApocalypse.loggerInfo("Configuration saved successfully");
            }
        } catch (IOException e) {
            FrozenApocalypse.loggerInfo("Unable to write file: " + e.getMessage());
        }
    }

    public static ModConfig loadConfig(String filePath) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ModConfig.class, new ModConfigDeserializer())
                .create();
        ModConfig loadedConfig = new ModConfig();
        boolean shouldCreateNewConfig = false;

        try (FileReader reader = new FileReader(filePath)) {
            try {
                loadedConfig = gson.fromJson(reader, ModConfig.class);
            } catch (JsonParseException e) {
                FrozenApocalypse.loggerInfo("Invalid config file: \n" + e.getMessage());
                shouldCreateNewConfig = true;
            }
        } catch (IOException e) {
            FrozenApocalypse.loggerInfo("Could not read config file: \n" + e.getMessage());
            shouldCreateNewConfig = true;
        }

        if (shouldCreateNewConfig) {
            ModConfigManager.backupConfig(filePath);
            ModConfigManager.saveConfig(loadedConfig, filePath);
            FrozenApocalypse.loggerInfo("Config file not found or corrupted, creating a new default config file and backed up current one");
        } else {
            FrozenApocalypse.loggerInfo("Config loaded successfully");
        }

        return loadedConfig;
    }

    public static void backupConfig(String filePath) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());
        String backupFilePath = filePath + "." + timestamp + ".backup";

        try {
            Files.copy(Path.of(filePath), Path.of(backupFilePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            FrozenApocalypse.loggerInfo("Original config file does not exist, making a new one without backup");
        }
    }
}
