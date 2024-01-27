package jaggwagg.frozen_apocalypse.world;

import com.google.common.base.CaseFormat;
import jaggwagg.frozen_apocalypse.registry.RegisterGameRules;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

import java.util.Locale;

public enum ModIntegerGameRules {
    FROZEN_APOCALYPSE_LEVEL(GameRuleFactory.createIntRule(0), "Apocalypse level"),
    FROZEN_APOCALYPSE_DEATH_PROTECTION_DURATION(GameRuleFactory.createIntRule(2400), "Death protection duration"),
    FROZEN_APOCALYPSE_WORLD_UPDATE_SPEED(GameRuleFactory.createIntRule(3), "Apocalypse world update speed");

    private final String id;
    private final String description;
    private final GameRules.Key<GameRules.IntRule> key;

    ModIntegerGameRules(GameRules.Type<GameRules.IntRule> type, String description) {
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

    public GameRules.Key<GameRules.IntRule> getKey() {
        return this.key;
    }
}
