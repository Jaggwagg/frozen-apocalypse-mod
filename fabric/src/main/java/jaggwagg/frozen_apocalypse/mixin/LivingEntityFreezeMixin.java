package jaggwagg.frozen_apocalypse.mixin;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.entity.effect.FrozenApocalypseStatusEffects;
import jaggwagg.frozen_apocalypse.item.FrozenApocalypseItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityFreezeMixin {
    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void hookTickMovement(CallbackInfo callbackInfo) {
        LivingEntity livingEntity = ((LivingEntity)(Object)this);
        World world = livingEntity.getWorld();

        if (!FrozenApocalypse.CONFIG.getFrozenApocalypseEnabled()) {
            return;
        }

        if (!world.getDimension().bedWorks()) {
            return;
        }

        if (livingEntity.hasStatusEffect(FrozenApocalypseStatusEffects.COLD_RESISTANCE)) {
            return;
        }

        if (livingEntity instanceof PlayerEntity playerEntity) {
            DefaultedList<ItemStack> playerArmor = playerEntity.getInventory().armor;

            if (playerArmor.get(0).isOf(FrozenApocalypseItems.Armor.THERMAL_BOOTS.item)
                    && playerArmor.get(1).isOf(FrozenApocalypseItems.Armor.THERMAL_LEGGINGS.item)
                    && playerArmor.get(2).isOf(FrozenApocalypseItems.Armor.THERMAL_CHESTPLATE.item)
                    && playerArmor.get(3).isOf(FrozenApocalypseItems.Armor.THERMAL_HELMET.item)) {
                return;
            }
        }

        if (world.getGameRules().getInt(FrozenApocalypse.FROZEN_APOCALYPSE_LEVEL) > 1) {
            if (livingEntity.getY() > 100) {
                freezeLivingEntity(world, livingEntity);
            }
        }

        if (world.getGameRules().getInt(FrozenApocalypse.FROZEN_APOCALYPSE_LEVEL) > 2) {
            if (livingEntity.getY() > 80) {
                freezeLivingEntity(world, livingEntity);
            }
        }

        if (world.getGameRules().getInt(FrozenApocalypse.FROZEN_APOCALYPSE_LEVEL) > 3) {
            if (livingEntity.getY() > 60) {
                freezeLivingEntity(world, livingEntity);
            }
        }

        if (world.getGameRules().getInt(FrozenApocalypse.FROZEN_APOCALYPSE_LEVEL) > 4) {
            if (livingEntity.getY() > 50) {
                freezeLivingEntity(world, livingEntity);
            }
        }
    }

    public void freezeLivingEntity(World world, LivingEntity livingEntity) {
        if (livingEntity instanceof PlayerEntity playerEntity) {
            if (playerEntity.isCreative() || playerEntity.isSpectator()) {
                return;
            }
        }

        if (livingEntity instanceof SkeletonEntity || livingEntity instanceof StrayEntity) {
            return;
        }

        if (world.getRandom().nextInt(32) == 0) {
            livingEntity.damage(DamageSource.FREEZE, 1.0f);
        }

        livingEntity.setInPowderSnow(true);

        if (!world.isClient) {
            livingEntity.setOnFire(false);
        }
    }
}
