package jaggwagg.frozen_apocalypse.mixin;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.entity.effect.FrozenApocalypseStatusEffects;
import jaggwagg.frozen_apocalypse.item.ThermalArmorItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityFreezeMixin {
    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovement(CallbackInfo ci) {
        LivingEntity livingEntity = ((LivingEntity) (Object) this);
        World world = livingEntity.getWorld();

        if (!FrozenApocalypse.FROZEN_APOCALYPSE_ENABLED) {
            return;
        }

        if (!world.getDimension().bedWorks()) {
            return;
        }

        if (livingEntity.hasStatusEffect(FrozenApocalypseStatusEffects.FROST_RESISTANCE)) {
            return;
        }

        if (ThermalArmorItem.wearingThermalArmor(livingEntity)) {
            return;
        }

        switch(FrozenApocalypse.frozenApocalypseLevel) {
            case 0:
                return;
            case 1:
                freezeLivingEntity(150, 7, 1.0f, 32, livingEntity, world);
                break;
            case 2:
                freezeLivingEntity(112, 7, 1.0f, 32, livingEntity, world);
                break;
            case 3:
                freezeLivingEntity(84, 7, 1.0f, 32, livingEntity, world);
                break;
            case 4:
                freezeLivingEntity(62, 7, 1.0f, 32, livingEntity, world);
                break;
            case 5:
                freezeLivingEntity(45, 7, 1.0f, 32, livingEntity, world);
                break;
            case 6:
                freezeLivingEntity(30, 7, 1.0f, 32, livingEntity, world);
                break;
            default:
                freezeLivingEntity(20, 3, 2.5f, 16, livingEntity, world);
        }
    }

    @Unique
    private boolean notNearHeatSource(int size, World world, LivingEntity livingEntity) {
        BlockPos livingEntityBlockPos = livingEntity.getBlockPos();

        for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
                for (int y = -size; y <= size; y++) {
                    BlockPos blockPos = new BlockPos(livingEntityBlockPos.getX() + x, livingEntityBlockPos.getY() + y, livingEntityBlockPos.getZ() + z);

                    if (FrozenApocalypse.CONFIG.getHeatBlocks().contains(world.getBlockState(blockPos).getBlock())) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Unique
    private void freezeLivingEntity(int aboveY, int heatSize, float damage, int random, LivingEntity livingEntity, World world) {
        if (livingEntity.getY() > aboveY) {
            if (notNearHeatSource(heatSize, world, livingEntity)) {
                if (livingEntity instanceof PlayerEntity playerEntity) {
                    if (playerEntity.isCreative() || playerEntity.isSpectator()) {
                        return;
                    }
                }

                if (livingEntity instanceof SkeletonEntity || livingEntity instanceof StrayEntity || livingEntity instanceof ZombieEntity) {
                    return;
                }

                if (world.getRandom().nextInt(random) == 0) {
                    livingEntity.damage(world.getDamageSources().freeze(), damage);
                }

                livingEntity.setInPowderSnow(true);

                if (!world.isClient) {
                    livingEntity.setOnFire(false);
                }
            }
        }
    }
}
