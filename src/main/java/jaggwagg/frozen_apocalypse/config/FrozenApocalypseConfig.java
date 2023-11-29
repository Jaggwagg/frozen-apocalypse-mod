package jaggwagg.frozen_apocalypse.config;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FrozenApocalypseConfig {
    private static final String CURRENT_VERSION = "1.1.1";
    private static final String CONFIG_PATH = System.getProperty("user.dir") + File.separator + "/config/";
    private static final HashSet<Block> currentHeatBlocks = new HashSet<>();
    private final String VERSION;
    private final boolean FROZEN_APOCALYPSE_ENABLED;
    private final ArrayList<String> HEAT_BLOCKS;

    public FrozenApocalypseConfig() {
        this.VERSION = CURRENT_VERSION;
        this.FROZEN_APOCALYPSE_ENABLED = true;
        this.HEAT_BLOCKS = new ArrayList<>();

        ArrayList<Block> heatBlocks = new ArrayList<>(List.of(
                Blocks.TORCH, Blocks.WALL_TORCH, Blocks.SOUL_TORCH, Blocks.SOUL_WALL_TORCH, Blocks.FIRE, Blocks.SOUL_FIRE,
                Blocks.CAMPFIRE, Blocks.SOUL_CAMPFIRE, Blocks.LANTERN, Blocks.SOUL_LANTERN,
                Blocks.LAVA, Blocks.LAVA_CAULDRON, Blocks.MAGMA_BLOCK, Blocks.JACK_O_LANTERN, Blocks.SEA_LANTERN
        ));

        heatBlocks.forEach(value -> this.HEAT_BLOCKS.add(Registries.BLOCK.getId(value).toString()));
    }

    private static FrozenApocalypseConfig createNewDefaultConfig(File configFile, Gson gson) throws IOException {
        FileWriter writer = new FileWriter(configFile);
        FrozenApocalypseConfig config = new FrozenApocalypseConfig();

        writer.write(gson.toJson(config));
        writer.close();

        return config;
    }

    public static FrozenApocalypseConfig getConfig() {
        FrozenApocalypseConfig config = new FrozenApocalypseConfig();
        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .serializeNulls()
                .create();

        if (new File(CONFIG_PATH).mkdir()) {
            FrozenApocalypse.LOGGER.info("Created config directory because it could not be found");
        }

        File configFile = new File(CONFIG_PATH + FrozenApocalypse.MOD_ID + ".json");

        try {
            if (configFile.createNewFile()) {
                config = createNewDefaultConfig(configFile, gson);
                FrozenApocalypse.LOGGER.warn("Created a new config file because it could not be found");
            } else {
                boolean makeBackup = false;
                String json = Files.readString(Path.of(CONFIG_PATH + FrozenApocalypse.MOD_ID + ".json"));

                try {
                    config = gson.fromJson(json, FrozenApocalypseConfig.class);
                } catch (JsonSyntaxException e) {
                    makeBackup = true;
                }

                if (config == null || !config.VERSION.equals(CURRENT_VERSION)) {
                    makeBackup = true;
                }

                if (makeBackup) {
                    File configFileBackup = new File(CONFIG_PATH + FrozenApocalypse.MOD_ID + "_backup.json");
                    Files.copy(configFile.toPath(), configFileBackup.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    FrozenApocalypse.LOGGER.warn("Invalid config file, overwriting with default config and made a backup of current file");

                    config = createNewDefaultConfig(configFile, gson);
                }
            }

            config.HEAT_BLOCKS.forEach(value -> {
                Identifier blockId = new Identifier(value);

                if (Registries.BLOCK.containsId(blockId)) {
                    currentHeatBlocks.add(Registries.BLOCK.get(new Identifier(value)));
                } else {
                    FrozenApocalypse.LOGGER.warn(value + " does not exist");
                }
            });

            FrozenApocalypse.LOGGER.info("Successfully read config file");
        } catch (IOException e) {
            FrozenApocalypse.LOGGER.error("Could not read or create config file: " + e.getMessage());
            FrozenApocalypse.LOGGER.error("This might cause unintended side effects");
        }

        return config;
    }

    public boolean getFrozenApocalypseEnabled() {
        return this.FROZEN_APOCALYPSE_ENABLED;
    }

    public HashSet<Block> getHeatBlocks() {
        return currentHeatBlocks;
    }
}
