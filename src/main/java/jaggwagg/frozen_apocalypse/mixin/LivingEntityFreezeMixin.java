package jaggwagg.frozen_apocalypse.mixin;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.config.FrozenApocalypseLevel;
import jaggwagg.frozen_apocalypse.entity.effect.FrozenApocalypseStatusEffects;
import jaggwagg.frozen_apocalypse.item.ThermalArmorItem;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.LinkedHashSet;

@Mixin(LivingEntity.class)
public abstract class LivingEntityFreezeMixin {
    @Unique
    private static boolean hasInitializedHeatBlocks = false;

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovement(CallbackInfo ci) {
        LivingEntity livingEntity = ((LivingEntity) (Object) this);
        World world = livingEntity.getWorld();

        if (!FrozenApocalypse.CONFIG.FROZEN_APOCALYPSE_ENABLED) {
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

        for (FrozenApocalypseLevel frozenApocalypseLevel : FrozenApocalypse.CONFIG.FROZEN_APOCALYPSE_LEVELS) {
            if (frozenApocalypseLevel.APOCALYPSE_LEVEL == FrozenApocalypse.frozenApocalypseLevel) {
                freezeLivingEntity(frozenApocalypseLevel.FREEZING_Y_LEVEL, frozenApocalypseLevel.FREEZE_DAMAGE, frozenApocalypseLevel.FREEZE_DAMAGE_DELAY, livingEntity, world);
                break;
            }
        }
    }

    @Unique
    private void initializeHeatBlocks() {
        LinkedHashSet<Block> heatBlocks = new LinkedHashSet<>();

        FrozenApocalypse.CONFIG.HEAT_BLOCK_IDS.forEach(value -> {
            Identifier blockId = new Identifier(value);

            if (Registries.BLOCK.containsId(blockId)) {
                heatBlocks.add(Registries.BLOCK.get(new Identifier(value)));
            } else {
                FrozenApocalypse.LOGGER.warn(value + " does not exist");
            }
        });

        FrozenApocalypse.CONFIG.setHeatBlocks(heatBlocks);
    }

    @Unique
    private boolean notNearHeatSource(World world, LivingEntity livingEntity) {
        BlockPos livingEntityBlockPos = livingEntity.getBlockPos();

        for (int x = -5; x <= 5; x++) {
            for (int z = -5; z <= 5; z++) {
                for (int y = -5; y <= 5; y++) {
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
    private void freezeLivingEntity(int aboveY, float damage, int random, LivingEntity livingEntity, World world) {
        if (!hasInitializedHeatBlocks) {
            initializeHeatBlocks();
            hasInitializedHeatBlocks = true;
        }

        if (livingEntity.getY() > aboveY) {
            if (notNearHeatSource(world, livingEntity)) {
                if (livingEntity instanceof PlayerEntity playerEntity) {
                    if (playerEntity.isCreative() || playerEntity.isSpectator()) {
                        return;
                    }
                }

                if (livingEntity instanceof SkeletonEntity || livingEntity instanceof StrayEntity || livingEntity instanceof ZombieEntity) {
                    return;
                }

                if (random < 1) {
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
