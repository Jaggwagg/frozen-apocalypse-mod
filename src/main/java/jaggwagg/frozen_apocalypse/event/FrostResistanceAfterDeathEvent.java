package jaggwagg.frozen_apocalypse.event;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.registry.ModGameRules;
import jaggwagg.frozen_apocalypse.registry.ModStatusEffects;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class FrostResistanceAfterDeathEvent implements ServerPlayerEvents.AfterRespawn {
    private static final int MINIMUM_FROZEN_APOCALYPSE_LEVEL = 1;
    private static final int MINIMUM_DEATH_PROTECTION_DURATION = 1;

    @Override
    public void afterRespawn(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        if (FrozenApocalypse.apocalypseLevel.getApocalypseLevel() < MINIMUM_FROZEN_APOCALYPSE_LEVEL) {
            return;
        }

        if (!newPlayer.getWorld().getGameRules().getBoolean(ModGameRules.RegisteredBooleanGameRules.FROZEN_APOCALYPSE_DEATH_PROTECTION.getKey())) {
            return;
        }

        if (!newPlayer.isCreative() || !newPlayer.isSpectator()) {
            int length = newPlayer.getWorld().getGameRules().getInt(ModGameRules.RegisteredIntegerGameRules.FROZEN_APOCALYPSE_DEATH_PROTECTION_DURATION.getKey());

            if (length < MINIMUM_DEATH_PROTECTION_DURATION) {
                ServerWorld serverWorld = newPlayer.getServerWorld();

                serverWorld.getGameRules().get(ModGameRules.RegisteredIntegerGameRules.FROZEN_APOCALYPSE_DEATH_PROTECTION_DURATION.getKey()).set(MINIMUM_DEATH_PROTECTION_DURATION, newPlayer.getWorld().getServer());
                length = newPlayer.getWorld().getGameRules().getInt(ModGameRules.RegisteredIntegerGameRules.FROZEN_APOCALYPSE_DEATH_PROTECTION_DURATION.getKey());
            }

            newPlayer.addStatusEffect(new StatusEffectInstance(ModStatusEffects.RegisteredStatusEffects.FROST_RESISTANCE.getStatusEffect(), length));
        }
    }
}