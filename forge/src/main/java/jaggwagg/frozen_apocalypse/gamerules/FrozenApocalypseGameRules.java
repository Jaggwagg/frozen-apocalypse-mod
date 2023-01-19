package jaggwagg.frozen_apocalypse.gamerules;

import net.minecraft.world.level.GameRules;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Method;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrozenApocalypseGameRules {
    public static final GameRules.Key<GameRules.IntegerValue> FROZEN_APOCALYPSE_LEVEL = GameRules.register("frozenApocalypseLevel", GameRules.Category.UPDATES, create(0));
    public static final GameRules.Key<GameRules.BooleanValue> FROZEN_APOCALYPSE_LEVEL_OVERRIDE = GameRules.register("frozenApocalypseLevelOverride", GameRules.Category.UPDATES, create(false));
    public static final GameRules.Key<GameRules.BooleanValue> FROZEN_APOCALYPSE_DEATH_PROTECTION = GameRules.register("frozenApocalypseDeathProtection", GameRules.Category.UPDATES, create(true));

    @SuppressWarnings("unchecked")
    public static GameRules.Type<GameRules.BooleanValue> create(boolean defaultValue) {
        try {
            Method createGameruleMethod = ObfuscationReflectionHelper.findMethod(GameRules.BooleanValue.class, "m_46250_", boolean.class);
            createGameruleMethod.setAccessible(true);
            return (GameRules.Type<GameRules.BooleanValue>) createGameruleMethod.invoke(GameRules.BooleanValue.class, defaultValue);
        }
        catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static GameRules.Type<GameRules.IntegerValue> create(int defaultValue) {
        try {
            Method createGameruleMethod = ObfuscationReflectionHelper.findMethod(GameRules.IntegerValue.class, "m_46312_", int.class);
            createGameruleMethod.setAccessible(true);
            return (GameRules.Type<GameRules.IntegerValue>) createGameruleMethod.invoke(GameRules.IntegerValue.class, defaultValue);
        }
        catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}
