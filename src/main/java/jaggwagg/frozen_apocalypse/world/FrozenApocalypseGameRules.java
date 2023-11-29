package jaggwagg.frozen_apocalypse.world;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;

public class FrozenApocalypseGameRules {
    public static final Identifier CATEGORY_IDENTIFIER = new Identifier(FrozenApocalypse.MOD_ID, FrozenApocalypse.MOD_ID);
    public static final CustomGameRuleCategory CATEGORY = new CustomGameRuleCategory(CATEGORY_IDENTIFIER, Text.translatable("gamerule.category." + FrozenApocalypse.MOD_ID).formatted(Formatting.YELLOW, Formatting.BOLD));
    public static final GameRules.Key<GameRules.IntRule> FROZEN_APOCALYPSE_LEVEL =
            GameRuleRegistry.register("frozenApocalypseLevel", CATEGORY, GameRuleFactory.createIntRule(0));
    public static final GameRules.Key<GameRules.BooleanRule> FROZEN_APOCALYPSE_LEVEL_OVERRIDE =
            GameRuleRegistry.register("frozenApocalypseLevelOverride", CATEGORY, GameRuleFactory.createBooleanRule(false));

    public static final GameRules.Key<GameRules.IntRule> FROZEN_APOCALYPSE_UPDATE_SPEED =
            GameRuleRegistry.register("frozenApocalypseUpdateSpeed", CATEGORY, GameRuleFactory.createIntRule(64));
    public static final GameRules.Key<GameRules.BooleanRule> FROZEN_APOCALYPSE_DEATH_PROTECTION =
            GameRuleRegistry.register("frozenApocalypseDeathProtection", CATEGORY, GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<GameRules.IntRule> FROZEN_APOCALYPSE_DEATH_PROTECTION_DURATION =
            GameRuleRegistry.register("frozenApocalypseDeathProtectionDuration", CATEGORY, GameRuleFactory.createIntRule(2400));

    public static void init() {
        FrozenApocalypse.LOGGER.info("Initialized game rules");
    }
}
