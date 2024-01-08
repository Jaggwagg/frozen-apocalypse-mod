package jaggwagg.frozen_apocalypse.init;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.BooleanRule;
import net.minecraft.world.GameRules.IntRule;
import net.minecraft.world.GameRules.Key;

import java.util.Arrays;
import java.util.StringJoiner;

public class ModGameRules {
    private static final CustomGameRuleCategory CATEGORY = new CustomGameRuleCategory(new Identifier(FrozenApocalypse.MOD_ID, FrozenApocalypse.MOD_ID), Text.translatable("gamerule.category." + FrozenApocalypse.MOD_ID).formatted(Formatting.AQUA, Formatting.BOLD));

    public static void init() {
        StringJoiner joiner = new StringJoiner(", ");

        Arrays.stream(RegisteredBooleanGameRules.values()).forEach(value -> joiner.add(value.getKey().getName()));
        Arrays.stream(RegisteredIntegerGameRules.values()).forEach(value -> joiner.add(value.getKey().getName()));

        FrozenApocalypse.LOGGER.info(FrozenApocalypse.MOD_ID + ": Initialized game rules: " + joiner);
    }

    public enum RegisteredBooleanGameRules {
        DEATH_PROTECTION("frozenApocalypseDeathProtection", GameRuleFactory.createBooleanRule(true)),
        LEVEL_UPDATES_EACH_DAY("frozenApocalypseLevelUpdatesEachDay", GameRuleFactory.createBooleanRule(true));

        private final Key<BooleanRule> key;

        RegisteredBooleanGameRules(String name, GameRules.Type<BooleanRule> type) {
            this.key = GameRuleRegistry.register(name, CATEGORY, type);
        }

        public Key<BooleanRule> getKey() {
            return this.key;
        }
    }

    public enum RegisteredIntegerGameRules {
        APOCALYPSE_LEVEL("frozenApocalypseLevel", GameRuleFactory.createIntRule(0)),
        DEATH_PROTECTION_DURATION("frozenApocalypseDeathProtectionDuration", GameRuleFactory.createIntRule(2400)),
        WORLD_UPDATE_SPEED("frozenApocalypseWorldUpdateSpeed", GameRuleFactory.createIntRule(3));

        private final Key<IntRule> key;

        RegisteredIntegerGameRules(String name, GameRules.Type<IntRule> type) {
            this.key = GameRuleRegistry.register(name, CATEGORY, type);
        }

        public Key<IntRule> getKey() {
            return this.key;
        }
    }
}
