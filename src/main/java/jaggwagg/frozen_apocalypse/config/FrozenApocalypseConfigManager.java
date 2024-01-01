package jaggwagg.frozen_apocalypse.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import jaggwagg.frozen_apocalypse.FrozenApocalypse;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FrozenApocalypseConfigManager {
    public static void saveConfig(FrozenApocalypseConfig config, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder()
                    .disableHtmlEscaping()
                    .setPrettyPrinting()
                    .serializeNulls()
                    .create();
            gson.toJson(config, writer);
            FrozenApocalypse.LOGGER.info(FrozenApocalypse.MOD_ID + ": Configuration saved successfully");
        } catch (IOException e) {
            FrozenApocalypse.LOGGER.warn(FrozenApocalypse.MOD_ID + ": Unable to write file: " + e.getMessage());
        }
    }

    public static FrozenApocalypseConfig loadConfig(String filePath) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(FrozenApocalypseConfig.class, new FrozenApocalypseConfigDeserializer())
                .create();
        FrozenApocalypseConfig loadedConfig = new FrozenApocalypseConfig();
        boolean shouldCreateNewConfig = false;

        try (FileReader reader = new FileReader(filePath)) {
            try {
                loadedConfig = gson.fromJson(reader, FrozenApocalypseConfig.class);
            } catch (JsonParseException e) {
                FrozenApocalypse.LOGGER.warn(FrozenApocalypse.MOD_ID + ": Invalid config file: \n" + e.getMessage());
                shouldCreateNewConfig = true;
            }
        } catch (IOException e) {
            FrozenApocalypse.LOGGER.warn(FrozenApocalypse.MOD_ID + ": Could not read config file: \n" + e.getMessage());
            shouldCreateNewConfig = true;
        }

        if (shouldCreateNewConfig) {
            FrozenApocalypseConfigManager.backupConfig(filePath);
            FrozenApocalypseConfigManager.saveConfig(loadedConfig, filePath);
            FrozenApocalypse.LOGGER.warn(FrozenApocalypse.MOD_ID + ": Config file not found or corrupted, creating a new default config file and backed up current one");
        } else {
            FrozenApocalypse.LOGGER.info(FrozenApocalypse.MOD_ID + ": Config loaded successfully");
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
            FrozenApocalypse.LOGGER.warn(FrozenApocalypse.MOD_ID + ": Original config file does not exist, making a new one without backup");
        }
    }
}
