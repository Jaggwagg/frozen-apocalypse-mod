package jaggwagg.frozen_apocalypse.mixin.entity;

import jaggwagg.frozen_apocalypse.apocalypse.LivingEntityEffects;
import jaggwagg.frozen_apocalypse.item.ThermalHorseArmorItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovement(CallbackInfo ci) {
        LivingEntity livingEntity = ((LivingEntity) (Object) this);
        World world = livingEntity.getWorld();

        if (LivingEntityEffects.shouldSkipFreezingChecks(world, livingEntity)) {
            return;
        }

        if (livingEntity instanceof HorseEntity horseEntity) {
            if (horseEntity.getArmorType().getItem() instanceof ThermalHorseArmorItem) {
                return;
            }
        }

        LivingEntityEffects.freezeLivingEntity(world, livingEntity);
    }
}
