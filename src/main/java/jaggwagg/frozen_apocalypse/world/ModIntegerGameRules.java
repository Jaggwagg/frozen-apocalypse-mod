package jaggwagg.frozen_apocalypse.world;

import com.google.common.base.CaseFormat;
import jaggwagg.frozen_apocalypse.registry.RegisterGameRules;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public enum ModIntegerGameRules {
    FROZEN_APOCALYPSE_LEVEL(GameRuleFactory.createIntRule(0)),
    FROZEN_APOCALYPSE_DEATH_PROTECTION_DURATION(GameRuleFactory.createIntRule(2400)),
    FROZEN_APOCALYPSE_WORLD_UPDATE_SPEED(GameRuleFactory.createIntRule(3));

    private final GameRules.Key<GameRules.IntRule> key;

    ModIntegerGameRules(GameRules.Type<GameRules.IntRule> type) {
        this.key = GameRuleRegistry.register(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.toString()), RegisterGameRules.FROZEN_APOCALYPSE_GAMERULE_CATEGORY, type);
    }

    public GameRules.Key<GameRules.IntRule> getKey() {
        return this.key;
    }
}
