package jaggwagg.frozen_apocalypse.mixin.block;

import jaggwagg.frozen_apocalypse.item.ThermalArmorItem;
import jaggwagg.frozen_apocalypse.registry.ModItems;
import jaggwagg.frozen_apocalypse.registry.ModStatusEffects;
import net.minecraft.block.BlockState;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public abstract class PowderSnowBlockMixin {
    @Unique
    private static void hasFrostResistance(LivingEntity livingEntity) {
        if (livingEntity.hasStatusEffect(ModStatusEffects.RegisteredStatusEffects.FROST_RESISTANCE.getStatusEffect())) {
            livingEntity.inPowderSnow = false;
        }
    }

    @Inject(method = "canWalkOnPowderSnow", at = @At("HEAD"), cancellable = true)
    private static void canWalkOnPowderSnow(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof LivingEntity livingEntity) {
            hasFrostResistance(livingEntity);

            if (livingEntity.getEquippedStack(EquipmentSlot.FEET).isOf(ModItems.RegisteredItems.IRON_THERMAL_BOOTS.getItem())) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "onEntityCollision", at = @At("TAIL"))
    private void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (entity instanceof LivingEntity livingEntity) {
            hasFrostResistance(livingEntity);

            if (ThermalArmorItem.wearingThermalArmor(livingEntity)) {
                entity.inPowderSnow = false;
            }
        }
    }
}
