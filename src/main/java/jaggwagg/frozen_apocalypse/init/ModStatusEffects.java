package jaggwagg.frozen_apocalypse.init;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.entity.effect.FrostResistanceStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Locale;
import java.util.StringJoiner;

public class ModStatusEffects {
    public static void init() {
        StringJoiner joiner = new StringJoiner(", ");

        Arrays.stream(RegisteredStatusEffects.values()).forEach(value -> {
            registerStatusEffect(value.getStatusEffect(), value.getName());
            joiner.add(value.getName());
        });

        FrozenApocalypse.LOGGER.info(FrozenApocalypse.MOD_ID + ": Initialized status effects: " + joiner);
    }

    public static void registerStatusEffect(StatusEffect statusEffect, String name) {
        Registry.register(Registries.STATUS_EFFECT, new Identifier(FrozenApocalypse.MOD_ID, name), statusEffect);
    }

    public enum RegisteredStatusEffects {
        FROST_RESISTANCE(new FrostResistanceStatusEffect());

        private final String name;
        private final StatusEffect statusEffect;

        <T extends StatusEffect> RegisteredStatusEffects(T statusEffect) {
            this.name = this.toString().toLowerCase(Locale.ROOT);
            this.statusEffect = statusEffect;
        }

        public String getName() {
            return this.name;
        }

        public StatusEffect getStatusEffect() {
            return this.statusEffect;
        }
    }
}
