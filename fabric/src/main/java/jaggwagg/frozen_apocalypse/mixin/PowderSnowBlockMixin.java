package jaggwagg.frozen_apocalypse.mixin;

import jaggwagg.frozen_apocalypse.item.FrozenApocalypseItems;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public abstract class PowderSnowBlockMixin {
    @Inject(method = "canWalkOnPowderSnow", at = @At("HEAD"), cancellable = true)
    private static void canWalkOnPowderSnow(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof  LivingEntity livingEntity) {
            if (livingEntity.getEquippedStack(EquipmentSlot.FEET).isOf(FrozenApocalypseItems.Armor.THERMAL_BOOTS.item)) {
                cir.setReturnValue(true);
            }
        }
    }
}
