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
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FrozenApocalypseConfig {
    public static final String CURRENT_VERSION = "1.1.3";
    public static final String CONFIG_PATH = System.getProperty("user.dir") + File.separator + "/config/";
    public final String VERSION;
    public final boolean FROZEN_APOCALYPSE_ENABLED;
    public final boolean SUN_SIZE_CHANGES_ENABLED;
    public final ArrayList<ApocalypseLevel> FROZEN_APOCALYPSE_LEVELS;
    public final ArrayList<HeatBlock> HEAT_BLOCKS;

    public FrozenApocalypseConfig() {
        this.VERSION = CURRENT_VERSION;
        this.FROZEN_APOCALYPSE_ENABLED = true;
        this.SUN_SIZE_CHANGES_ENABLED = true;
        this.FROZEN_APOCALYPSE_LEVELS = new ArrayList<>();
        this.HEAT_BLOCKS = new ArrayList<>();

        ArrayList<Block> heatBlocks = new ArrayList<>(List.of(
                Blocks.BEACON, Blocks.CAMPFIRE, Blocks.CONDUIT, Blocks.END_ROD, Blocks.FIRE, Blocks.OCHRE_FROGLIGHT, Blocks.PEARLESCENT_FROGLIGHT, Blocks.VERDANT_FROGLIGHT,
                Blocks.GLOWSTONE, Blocks.JACK_O_LANTERN, Blocks.LANTERN, Blocks.LAVA, Blocks.LAVA_CAULDRON, Blocks.MAGMA_BLOCK, Blocks.REDSTONE_TORCH, Blocks.SEA_LANTERN,
                Blocks.SHROOMLIGHT, Blocks.SOUL_CAMPFIRE, Blocks.SOUL_LANTERN, Blocks.SOUL_TORCH, Blocks.TORCH, Blocks.WALL_TORCH, Blocks.SOUL_WALL_TORCH, Blocks.REDSTONE_WALL_TORCH
        ));

        heatBlocks.forEach(value -> this.HEAT_BLOCKS.add(new HeatBlock(Registries.BLOCK.getId(value).toString(), value.getDefaultState().getLuminance(), Blocks.GLOWSTONE)));

        this.FROZEN_APOCALYPSE_LEVELS.addAll(List.of(
                new ApocalypseLevel.Builder(0, 0, 1.0f).build(),
                new ApocalypseLevel.Builder(1, 1, 0.9f).freezeEntities(150, 32, 1.0f).leafDecay().grassToPodzol().build(),
                new ApocalypseLevel.Builder(2, 2, 0.8f).disableMobsBurnDuringDaylight().freezeEntities(112, 32, 1.0f).leafDecay().grassToPodzol().build(),
                new ApocalypseLevel.Builder(3, 3, 0.7f).disableMobsBurnDuringDaylight().freezeEntities(84, 32, 1.0f).leafDecay().grassToPodzol().waterToIce().placeSnow().build(),
                new ApocalypseLevel.Builder(4, 4, 0.6f).disableMobsBurnDuringDaylight().freezeEntities(62, 32, 1.0f).leafDecay().grassToPodzol().waterToIce().placeSnow().build(),
                new ApocalypseLevel.Builder(5, 5, 0.5f).disableMobsBurnDuringDaylight().freezeEntities(45, 32, 1.0f).leafDecay().grassToPodzol().waterToIce().placeSnow().iceToPackedIce().lavaToObsidian().build(),
                new ApocalypseLevel.Builder(6, 6, 0.4f).disableMobsBurnDuringDaylight().freezeEntities(30, 32, 1.5f).leafDecay().grassToPodzol().waterToIce().placeSnow().iceToPackedIce().lavaToObsidian().placeSnowBlock().disableWeather().build(),
                new ApocalypseLevel.Builder(7, 7, 0.3f).disableMobsBurnDuringDaylight().freezeEntities(20, 16, 1.5f).leafDecay().grassToPodzol().waterToIce().placeSnow().iceToPackedIce().lavaToObsidian().placeSnowBlock().disableWeather().build(),
                new ApocalypseLevel.Builder(8, 8, 0.3f).disableMobsBurnDuringDaylight().freezeEntities(20, 16, 2.0f).leafDecay().grassToPodzol().waterToIce().placeSnow().iceToPackedIce().lavaToObsidian().placeSnowBlock().disableWeather().build()
        ));
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

            FrozenApocalypse.LOGGER.info("Successfully read config file");
        } catch (IOException e) {
            FrozenApocalypse.LOGGER.error("Could not read or create config file: " + e.getMessage());
            FrozenApocalypse.LOGGER.error("This might cause unintended side effects");
        }

        return config;
    }
}
