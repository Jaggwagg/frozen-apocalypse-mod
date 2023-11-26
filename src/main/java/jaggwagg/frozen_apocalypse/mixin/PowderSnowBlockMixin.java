package jaggwagg.frozen_apocalypse.mixin;

import jaggwagg.frozen_apocalypse.entity.effect.FrozenApocalypseStatusEffects;
import jaggwagg.frozen_apocalypse.item.FrozenApocalypseItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public abstract class PowderSnowBlockMixin {
    @Inject(method = "canWalkOnPowderSnow", at = @At("HEAD"), cancellable = true)
    private static void canWalkOnPowderSnow(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof  LivingEntity livingEntity) {
            if (livingEntity.getEquippedStack(EquipmentSlot.FEET).isOf(FrozenApocalypseItems.Armor.IRON_THERMAL_BOOTS.item)) {
                cir.setReturnValue(true);
            }

            if (livingEntity.hasStatusEffect(FrozenApocalypseStatusEffects.FROST_RESISTANCE)) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "onEntityCollision", at = @At("TAIL"))
    private void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (entity instanceof  LivingEntity livingEntity) {
            if (livingEntity.hasStatusEffect(FrozenApocalypseStatusEffects.FROST_RESISTANCE)) {
                entity.inPowderSnow = false;
            }

            if (livingEntity instanceof PlayerEntity playerEntity) {
                DefaultedList<ItemStack> playerArmor = playerEntity.getInventory().armor;
                boolean wearingThermalBoots = false;
                boolean wearingThermalLeggings = false;
                boolean wearingThermalChestplate = false;
                boolean wearingThermalHelmet = false;

                if (playerArmor.get(0).getItem().getClass() == FrozenApocalypseItems.Armor.IRON_THERMAL_BOOTS.item.getClass()) {
                    wearingThermalBoots = true;
                }

                if (playerArmor.get(1).getItem().getClass() == FrozenApocalypseItems.Armor.IRON_THERMAL_LEGGINGS.item.getClass()) {
                    wearingThermalLeggings = true;
                }

                if (playerArmor.get(2).getItem().getClass() == FrozenApocalypseItems.Armor.IRON_THERMAL_CHESTPLATE.item.getClass()) {
                    wearingThermalChestplate = true;
                }

                if (playerArmor.get(3).getItem().getClass() == FrozenApocalypseItems.Armor.IRON_THERMAL_HELMET.item.getClass()) {
                    wearingThermalHelmet = true;
                }

                if (wearingThermalBoots && wearingThermalLeggings && wearingThermalChestplate && wearingThermalHelmet) {
                    entity.inPowderSnow = false;
                }
            }
        }
    }
}
