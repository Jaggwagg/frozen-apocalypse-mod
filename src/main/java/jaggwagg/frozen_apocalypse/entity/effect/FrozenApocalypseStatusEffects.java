package jaggwagg.frozen_apocalypse.entity.effect;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class FrozenApocalypseStatusEffects {
    public static final StatusEffect COLD_RESISTANCE = new ColdResistanceStatusEffect();

    public static void init() {
        Registry.register(Registries.STATUS_EFFECT, new Identifier(FrozenApocalypse.MOD_ID, "cold_resistance"), COLD_RESISTANCE);
    }
}
