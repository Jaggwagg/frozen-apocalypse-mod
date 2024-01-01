package jaggwagg.frozen_apocalypse.mixin.entity;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.apocalypse.LivingEntityEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovement(CallbackInfo ci) {
        LivingEntity livingEntity = ((LivingEntity) (Object) this);
        World world = livingEntity.getWorld();

        if (LivingEntityEffects.shouldSkipFreezingChecks(world, livingEntity)) {
            return;
        }

        freezeLivingEntity(world, livingEntity);
    }

    @Unique
    private void freezeLivingEntity(World world, LivingEntity livingEntity) {
        if (!LivingEntityEffects.isNearHeatSource(world, livingEntity)) {
            if (FrozenApocalypse.apocalypseLevel.canFreezeEntities() && livingEntity.getY() >= FrozenApocalypse.apocalypseLevel.getFreezingYLevel()) {
                if (!livingEntity.inPowderSnow) {
                    livingEntity.setInPowderSnow(true);
                }

                if (world.getRandom().nextInt(FrozenApocalypse.apocalypseLevel.getFreezeDamageDelay() + 1) == 1) {
                    livingEntity.damage(world.getDamageSources().freeze(), FrozenApocalypse.apocalypseLevel.getFreezeDamage());
                }

                if (!world.isClient) {
                    livingEntity.setOnFire(false);
                }
            }
        }
    }
}
