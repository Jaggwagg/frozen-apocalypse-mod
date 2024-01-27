package jaggwagg.frozen_apocalypse.world;

import com.google.common.base.CaseFormat;
import jaggwagg.frozen_apocalypse.registry.RegisterGameRules;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

import java.util.Locale;

public enum ModBooleanGameRules {
    FROZEN_APOCALYPSE_DEATH_PROTECTION(GameRuleFactory.createBooleanRule(true), "Death protection"),
    FROZEN_APOCALYPSE_LEVEL_UPDATES_EACH_DAY(GameRuleFactory.createBooleanRule(true), "Apocalypse level updates each day");

    private final String id;
    private final String description;
    private final GameRules.Key<GameRules.BooleanRule> key;

    ModBooleanGameRules(GameRules.Type<GameRules.BooleanRule> type, String description) {
        this.id = this.toString().toLowerCase(Locale.ROOT);
        this.description = description;
        this.key = GameRuleRegistry.register(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.toString()), RegisterGameRules.FROZEN_APOCALYPSE_GAMERULE_CATEGORY, type);
    }

    public String getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public GameRules.Key<GameRules.BooleanRule> getKey() {
        return this.key;
    }
}
