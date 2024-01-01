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
    @Unique
    private long heatSourceCheckDelay = FrozenApocalypse.CONFIG.getHeatSourceCheckDelay();
    @Unique
    private boolean nearHeatSource = true;

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovement(CallbackInfo ci) {
        LivingEntity livingEntity = ((LivingEntity) (Object) this);
        World world = livingEntity.getWorld();

        if (LivingEntityEffects.shouldSkipFreezingChecks(world, livingEntity)) {
            this.nearHeatSource = true;
            return;
        }

        freezeLivingEntity(world, livingEntity);
    }

    @Unique
    private void freezeLivingEntity(World world, LivingEntity livingEntity) {
        this.heatSourceCheckDelay--;

        if (this.heatSourceCheckDelay == 0L) {
            this.heatSourceCheckDelay = FrozenApocalypse.CONFIG.getHeatSourceCheckDelay();
            this.nearHeatSource = LivingEntityEffects.isNearHeatSource(world, livingEntity);
        }

        if (!this.nearHeatSource) {
            if (FrozenApocalypse.frozenApocalypseLevel.canFreezeEntities() && livingEntity.getY() >= FrozenApocalypse.frozenApocalypseLevel.getFreezingYLevel()) {
                if (!livingEntity.inPowderSnow) {
                    livingEntity.setInPowderSnow(true);
                }

                if (world.getRandom().nextInt(FrozenApocalypse.frozenApocalypseLevel.getFreezeDamageDelay() + 1) == 1) {
                    livingEntity.damage(world.getDamageSources().freeze(), FrozenApocalypse.frozenApocalypseLevel.getFreezeDamage());
                }

                if (!world.isClient) {
                    livingEntity.setOnFire(false);
                }
            }
        }
    }
}
