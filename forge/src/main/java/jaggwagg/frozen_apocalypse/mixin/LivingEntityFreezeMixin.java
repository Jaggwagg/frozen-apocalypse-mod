package jaggwagg.frozen_apocalypse.mixin;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.entity.effect.FrozenApocalypseStatusEffects;
import jaggwagg.frozen_apocalypse.gamerules.FrozenApocalypseGameRules;
import jaggwagg.frozen_apocalypse.item.FrozenApocalypseItems;
import net.minecraft.core.NonNullList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityFreezeMixin {
    @Inject(method = "aiStep", at = @At("HEAD"))
    private void hookTickMovement(CallbackInfo ci) {
        LivingEntity livingEntity = ((LivingEntity)(Object)this);
        Level world = livingEntity.getLevel();

        if (!FrozenApocalypse.CONFIG.getFrozenApocalypseEnabled()) {
            return;
        }

        if (!world.dimensionType().bedWorks()) {
            return;
        }

        if (livingEntity.hasEffect(FrozenApocalypseStatusEffects.COLD_RESISTANCE.get())) {
            return;
        }

        if (livingEntity instanceof Player playerEntity) {
            NonNullList<ItemStack> playerArmor = playerEntity.getInventory().armor;

            if (playerArmor.get(0).is(FrozenApocalypseItems.Armor.THERMAL_BOOTS.item.get())
                    && playerArmor.get(1).is(FrozenApocalypseItems.Armor.THERMAL_LEGGINGS.item.get())
                    && playerArmor.get(2).is(FrozenApocalypseItems.Armor.THERMAL_CHESTPLATE.item.get())
                    && playerArmor.get(3).is(FrozenApocalypseItems.Armor.THERMAL_HELMET.item.get())) {
                return;
            }
        }

        int REPLACEMELATER = 10;

        if (REPLACEMELATER > 1) {
            if (livingEntity.getY() > 100) {
                freezeLivingEntity(world, livingEntity);
            }
        }

        if (REPLACEMELATER > 2) {
            if (livingEntity.getY() > 80) {
                freezeLivingEntity(world, livingEntity);
            }
        }

        if (REPLACEMELATER > 3) {
            if (livingEntity.getY() > 60) {
                freezeLivingEntity(world, livingEntity);
            }
        }

        if (REPLACEMELATER > 4) {
            if (livingEntity.getY() > 50) {
                freezeLivingEntity(world, livingEntity);
            }
        }
    }

    public void freezeLivingEntity(Level world, LivingEntity livingEntity) {
        if (livingEntity instanceof Player playerEntity) {
            if (playerEntity.isCreative() || playerEntity.isSpectator()) {
                return;
            }
        }

        if (livingEntity instanceof Skeleton || livingEntity instanceof Stray) {
            return;
        }

        if (world.getRandom().nextInt(32) == 0) {
            livingEntity.hurt(DamageSource.FREEZE, 1.0f);
        }

        livingEntity.setIsInPowderSnow(true);

        if (!world.isClientSide()) {
            livingEntity.extinguishFire();
        }
    }
}
