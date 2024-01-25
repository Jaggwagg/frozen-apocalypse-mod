package jaggwagg.frozen_apocalypse.entity.effect;

import net.minecraft.entity.effect.StatusEffect;

import java.util.Locale;

public enum ModStatusEffects {
    FROST_RESISTANCE(new FrostResistanceStatusEffect());

    private final String id;
    private final StatusEffect statusEffect;

    <T extends StatusEffect> ModStatusEffects(T statusEffect) {
        this.id = this.toString().toLowerCase(Locale.ROOT);
        this.statusEffect = statusEffect;
    }

    public String getId() {
        return this.id;
    }

    public StatusEffect getStatusEffect() {
        return this.statusEffect;
    }
}
