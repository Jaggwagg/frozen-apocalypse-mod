package jaggwagg.frozen_apocalypse.config;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FrozenApocalypseConfig {
    private static final String CONFIG_PATH = System.getProperty("user.dir") + File.separator + "/config/" + FrozenApocalypse.MOD_ID + ".json";
    private final boolean frozenApocalypseEnabled;
    private final ArrayList<String> heatBlocks;

    private static FrozenApocalypseConfig createNewDefaultConfig(File configFile, Gson gson) throws IOException {
        FileWriter writer = new FileWriter(configFile);
        FrozenApocalypseConfig config = new FrozenApocalypseConfig();

        writer.write(gson.toJson(config));
        writer.close();

        return config;
    }

    public FrozenApocalypseConfig() {
        this.frozenApocalypseEnabled = true;
        this.heatBlocks = new ArrayList<>();

        ArrayList<Block> heatBlocks = new ArrayList<>(List.of(
                Blocks.TORCH, Blocks.WALL_TORCH, Blocks.SOUL_TORCH, Blocks.SOUL_WALL_TORCH, Blocks.FIRE, Blocks.SOUL_FIRE,
                Blocks.CAMPFIRE, Blocks.SOUL_CAMPFIRE, Blocks.LANTERN, Blocks.SOUL_LANTERN,
                Blocks.LAVA, Blocks.LAVA_CAULDRON, Blocks.MAGMA_BLOCK, Blocks.JACK_O_LANTERN, Blocks.SEA_LANTERN
        ));

        heatBlocks.forEach(value -> this.heatBlocks.add(Registries.BLOCK.getId(value).toString()));
    }

    public boolean getFrozenApocalypseEnabled() {
        return this.frozenApocalypseEnabled;
    }

    public ArrayList<String> getHeatBlocks() {
        return this.heatBlocks;
    }

    public static FrozenApocalypseConfig getConfig() {
        FrozenApocalypseConfig config = new FrozenApocalypseConfig();
        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .serializeNulls()
                .create();

        boolean createdDirectory = new File(System.getProperty("user.dir") + File.separator + "/config").mkdir();

        if (createdDirectory) {
            FrozenApocalypse.LOGGER.info("Created config directory because it could not be found");
        }

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
