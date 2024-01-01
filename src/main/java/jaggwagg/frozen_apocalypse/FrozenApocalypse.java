package jaggwagg.frozen_apocalypse;

import jaggwagg.frozen_apocalypse.config.ApocalypseLevel;
import jaggwagg.frozen_apocalypse.config.FrozenApocalypseConfig;
import jaggwagg.frozen_apocalypse.config.FrozenApocalypseConfigManager;
import jaggwagg.frozen_apocalypse.registry.FrozenApocalypseStatusEffects;
import jaggwagg.frozen_apocalypse.registry.FrozenApocalypseEvents;
import jaggwagg.frozen_apocalypse.registry.FrozenApocalypseItems;
import jaggwagg.frozen_apocalypse.registry.FrozenApocalypseGameRules;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class FrozenApocalypse implements ModInitializer {
    public static final String MOD_ID = "frozen_apocalypse";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final String CONFIG_FILE_PATH = System.getProperty("user.dir") + File.separator + "/config/" + MOD_ID + ".json";
    public static final FrozenApocalypseConfig CONFIG = FrozenApocalypseConfigManager.loadConfig(CONFIG_FILE_PATH);
    public static ApocalypseLevel apocalypseLevel = CONFIG.getApocalypseLevels().get(0);

    @Override
    public void onInitialize() {
        FrozenApocalypseGameRules.init();
        FrozenApocalypseStatusEffects.init();
        FrozenApocalypseItems.init();
        FrozenApocalypseEvents.init();

        LOGGER.info(MOD_ID + ": Initialized common successfully");
    }
}
