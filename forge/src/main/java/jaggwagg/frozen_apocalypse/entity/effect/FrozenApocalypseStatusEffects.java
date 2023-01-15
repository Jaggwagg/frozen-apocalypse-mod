package jaggwagg.frozen_apocalypse.entity.effect;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FrozenApocalypseStatusEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, FrozenApocalypse.MOD_ID);

    public static final RegistryObject<MobEffect> COLD_RESISTANCE = MOB_EFFECTS.register("cold_resistance",
            ColdResistanceStatusEffect::new);

    public static void init(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
