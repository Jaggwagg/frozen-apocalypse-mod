package jaggwagg.frozen_apocalypse.registry;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.entity.effect.FrostResistanceStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Locale;

public class ModStatusEffects {
    public static void init() {
        Arrays.stream(RegisteredStatusEffects.values()).forEach(value -> registerStatusEffect(value.getName(), value.getStatusEffect()));

        FrozenApocalypse.loggerInfo("Initialized status effects");
    }

    public static void registerStatusEffect(String id, StatusEffect statusEffect) {
        Registry.register(Registries.STATUS_EFFECT, new Identifier(FrozenApocalypse.MOD_ID, id), statusEffect);
    }

    public enum RegisteredStatusEffects {
        FROST_RESISTANCE(new FrostResistanceStatusEffect());

        private final String id;
        private final StatusEffect statusEffect;

        <T extends StatusEffect> RegisteredStatusEffects(T statusEffect) {
            this.id = this.toString().toLowerCase(Locale.ROOT);
            this.statusEffect = statusEffect;
        }

        public String getName() {
            return this.id;
        }

        public StatusEffect getStatusEffect() {
            return this.statusEffect;
        }
    }
}
