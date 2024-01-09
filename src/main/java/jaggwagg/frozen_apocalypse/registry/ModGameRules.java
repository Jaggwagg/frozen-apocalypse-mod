package jaggwagg.frozen_apocalypse.registry;

import com.google.common.base.CaseFormat;
import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;

import java.util.Arrays;

public class ModGameRules {
    private static final CustomGameRuleCategory FROZEN_APOCALYPSE_GAMERULE_CATEGORY = new CustomGameRuleCategory(new Identifier(FrozenApocalypse.MOD_ID, FrozenApocalypse.MOD_ID), Text.translatable("gamerule.category." + FrozenApocalypse.MOD_ID).formatted(Formatting.AQUA, Formatting.BOLD));

    public static void init() {
        Arrays.stream(RegisteredBooleanGameRules.values()).forEach(Enum::describeConstable);
        Arrays.stream(RegisteredIntegerGameRules.values()).forEach(Enum::describeConstable);

        FrozenApocalypse.loggerInfo("Initialized gamerules");
    }

    public enum RegisteredBooleanGameRules {
        FROZEN_APOCALYPSE_DEATH_PROTECTION(GameRuleFactory.createBooleanRule(true)),
        FROZEN_APOCALYPSE_LEVEL_UPDATES_EACH_DAY(GameRuleFactory.createBooleanRule(true));

        private final GameRules.Key<GameRules.BooleanRule> key;

        RegisteredBooleanGameRules(GameRules.Type<GameRules.BooleanRule> type) {
            this.key = GameRuleRegistry.register(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.toString()), FROZEN_APOCALYPSE_GAMERULE_CATEGORY, type);
        }

        public GameRules.Key<GameRules.BooleanRule> getKey() {
            return this.key;
        }
    }

    public enum RegisteredIntegerGameRules {
        FROZEN_APOCALYPSE_LEVEL(GameRuleFactory.createIntRule(0)),
        FROZEN_APOCALYPSE_DEATH_PROTECTION_DURATION(GameRuleFactory.createIntRule(2400)),
        FROZEN_APOCALYPSE_WORLD_UPDATE_SPEED(GameRuleFactory.createIntRule(3));

        private final GameRules.Key<GameRules.IntRule> key;

        RegisteredIntegerGameRules(GameRules.Type<GameRules.IntRule> type) {
            this.key = GameRuleRegistry.register(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.toString()), FROZEN_APOCALYPSE_GAMERULE_CATEGORY, type);
        }

        public GameRules.Key<GameRules.IntRule> getKey() {
            return this.key;
        }
    }
}
