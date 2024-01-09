package jaggwagg.frozen_apocalypse;

import jaggwagg.frozen_apocalypse.config.ApocalypseLevel;
import jaggwagg.frozen_apocalypse.config.ModConfig;
import jaggwagg.frozen_apocalypse.config.ModConfigManager;
import jaggwagg.frozen_apocalypse.registry.*;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class FrozenApocalypse implements ModInitializer {
    public static final String MOD_ID = "frozen_apocalypse";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final String CONFIG_FILE_PATH = System.getProperty("user.dir") + File.separator + "/config/" + MOD_ID + ".json";
    public static final ModConfig CONFIG = ModConfigManager.loadConfig(CONFIG_FILE_PATH);
    public static ApocalypseLevel apocalypseLevel = CONFIG.getApocalypseLevels().get(0);

    public static void loggerInfo(String message) {
        LOGGER.info(MOD_ID + ": " + message);
    }

    @Override
    public void onInitialize() {
        ModBlocks.init();
        ModEvents.init();
        ModGameRules.init();
        ModItems.init();
        ModItemGroups.init();
        ModStatusEffects.init();

        LOGGER.info(MOD_ID + ": Initialized common successfully");
    }
}
