package jaggwagg.frozen_apocalypse;

import jaggwagg.frozen_apocalypse.config.FrozenApocalypseConfig;
import jaggwagg.frozen_apocalypse.entity.effect.FrozenApocalypseStatusEffects;
import jaggwagg.frozen_apocalypse.item.FrozenApocalypseItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FrozenApocalypse implements ModInitializer {
    public static final String MOD_ID = "frozen_apocalypse";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final FrozenApocalypseConfig CONFIG = FrozenApocalypseConfig.getConfig();

    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder(new Identifier(MOD_ID, "general"))
            .icon(() -> new ItemStack(FrozenApocalypseItems.Armor.THERMAL_CHESTPLATE.item))
            .build();

    public static final GameRules.Key<GameRules.IntRule> FROZEN_APOCALYPSE_LEVEL =
            GameRuleRegistry.register("frozenApocalypseLevel", GameRules.Category.UPDATES, GameRuleFactory.createIntRule(0));
    public static final GameRules.Key<GameRules.BooleanRule> FROZEN_APOCALYPSE_LEVEL_OVERRIDE =
            GameRuleRegistry.register("frozenApocalypseLevelOverride", GameRules.Category.UPDATES, GameRuleFactory.createBooleanRule(false));

    public static final GameRules.Key<GameRules.BooleanRule> FROZEN_APOCALYPSE_DEATH_PROTECTION =
            GameRuleRegistry.register("frozenApocalypseDeathProtection", GameRules.Category.UPDATES, GameRuleFactory.createBooleanRule(true));

    public static long timeOfDay;

    @Override
    public void onInitialize() {
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, isAlive) -> {
            if (newPlayer.getWorld().getGameRules().getBoolean(FROZEN_APOCALYPSE_DEATH_PROTECTION)) {
                if (!newPlayer.isCreative() && !newPlayer.isSpectator()) {
                    newPlayer.addStatusEffect(new StatusEffectInstance(FrozenApocalypseStatusEffects.COLD_RESISTANCE, 2400));
                }
            }
        });

        FrozenApocalypseItems.init();
        FrozenApocalypseStatusEffects.init();
        LOGGER.info("Successfully initialized!");
    }
}
