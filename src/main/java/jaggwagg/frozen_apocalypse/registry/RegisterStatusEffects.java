package jaggwagg.frozen_apocalypse.registry;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.entity.effect.ModStatusEffects;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;

public class RegisterStatusEffects {
    public static void init() {
        Arrays.stream(ModStatusEffects.values()).forEach(value -> registerStatusEffect(value.getId(), value.getStatusEffect()));
    }

    public static void registerStatusEffect(String id, StatusEffect statusEffect) {
        Registry.register(Registries.STATUS_EFFECT, new Identifier(FrozenApocalypse.MOD_ID, id), statusEffect);
    }
}
