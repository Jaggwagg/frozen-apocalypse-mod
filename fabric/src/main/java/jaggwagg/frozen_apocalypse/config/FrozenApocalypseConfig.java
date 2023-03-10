package jaggwagg.frozen_apocalypse.config;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import jaggwagg.frozen_apocalypse.FrozenApocalypse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FrozenApocalypseConfig {
    private static final String CONFIG_PATH = System.getProperty("user.dir") + File.separator + "/config/" + FrozenApocalypse.MOD_ID + ".json";
    private final boolean frozenApocalypseEnabled;
    private final int maxApocalypseLevel;

    private static FrozenApocalypseConfig createNewDefaultConfig(File configFile, Gson gson) throws IOException {
        FileWriter writer = new FileWriter(configFile);
        FrozenApocalypseConfig config = new FrozenApocalypseConfig();

        writer.write(gson.toJson(config));
        writer.close();

        return config;
    }

    public FrozenApocalypseConfig() {
        this.frozenApocalypseEnabled = true;
        this.maxApocalypseLevel = 999;
    }

    public boolean getFrozenApocalypseEnabled() {
        return this.frozenApocalypseEnabled;
    }

    public int getMaxApocalypseLevel() {
        return this.maxApocalypseLevel;
    }

    public static FrozenApocalypseConfig getConfig() {
        FrozenApocalypseConfig config = new FrozenApocalypseConfig();
        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .serializeNulls()
                .create();

        new File(System.getProperty("user.dir") + File.separator + "/config").mkdir();

        File configFile = new File(CONFIG_PATH);

        try {
            if (configFile.createNewFile()) {
                config = createNewDefaultConfig(configFile, gson);
                FrozenApocalypse.LOGGER.warn("Created a new config file because it could not be found");
            } else {
                String json = Files.readString(Path.of(CONFIG_PATH));

                try {
                    config = gson.fromJson(json, FrozenApocalypseConfig.class);
                } catch(JsonSyntaxException e) {
                    config = createNewDefaultConfig(configFile, gson);
                    FrozenApocalypse.LOGGER.warn("Invalid JSON in config file, overwriting with default config");
                }

                if (config == null) {
                    config = createNewDefaultConfig(configFile, gson);
                    FrozenApocalypse.LOGGER.warn("Could not parse current config file, created a new one");
                }

                FrozenApocalypse.LOGGER.info("Successfully read config file");
            }
        } catch (IOException e) {
            FrozenApocalypse.LOGGER.error("Could not read or create config file: " + e.getMessage());
        }

        return config;
    }
}
