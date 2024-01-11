package jaggwagg.frozen_apocalypse.entity.mob;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public interface SlownessAfflicting {
    default void inflictSlowness(LivingEntity inflictingLivingEntity, LivingEntity inflictedLivingEntity) {
        int duration = (int) inflictingLivingEntity.getWorld().getLocalDifficulty(inflictingLivingEntity.getBlockPos()).getLocalDifficulty();

        System.out.println(inflictingLivingEntity.getWorld().getLocalDifficulty(inflictingLivingEntity.getBlockPos()).getLocalDifficulty());

        inflictedLivingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 140 * duration), inflictingLivingEntity);
    }
}
