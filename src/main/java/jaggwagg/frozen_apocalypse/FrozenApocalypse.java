package jaggwagg.frozen_apocalypse;

import jaggwagg.frozen_apocalypse.config.FrozenApocalypseConfig;
import jaggwagg.frozen_apocalypse.entity.effect.FrozenApocalypseStatusEffects;
import jaggwagg.frozen_apocalypse.item.FrozenApocalypseItems;
import jaggwagg.frozen_apocalypse.world.FrozenApocalypseGameRules;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class FrozenApocalypse implements ModInitializer {
    public static final String MOD_ID = "frozen_apocalypse";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final FrozenApocalypseConfig CONFIG = FrozenApocalypseConfig.getConfig();
    public static final boolean isFrozenApocalypseEnabled = CONFIG.getFrozenApocalypseEnabled();

    public static int frozenApocalypseLevel = 0;

    @Override
    public void onInitialize() {
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, isAlive) -> {
            if (newPlayer.getWorld().getGameRules().getBoolean(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_DEATH_PROTECTION)) {
                if (!newPlayer.isCreative() && !newPlayer.isSpectator()) {
                    int length = newPlayer.getWorld().getGameRules().getInt(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_DEATH_PROTECTION_DURATION);

                    if (length < 1) {
                        Objects.requireNonNull(newPlayer.getWorld().getServer()).getGameRules().get(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_DEATH_PROTECTION_DURATION).set(1, newPlayer.getWorld().getServer());
                        length = newPlayer.getWorld().getGameRules().getInt(FrozenApocalypseGameRules.FROZEN_APOCALYPSE_DEATH_PROTECTION_DURATION);
                    }

                    newPlayer.addStatusEffect(new StatusEffectInstance(FrozenApocalypseStatusEffects.FROST_RESISTANCE, length));
                }
            }
        });

        FrozenApocalypseGameRules.init();
        FrozenApocalypseItems.init();
        FrozenApocalypseStatusEffects.init();
        LOGGER.info("Successfully initialized common!");
    }
}
