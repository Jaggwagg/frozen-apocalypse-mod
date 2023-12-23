package jaggwagg.frozen_apocalypse.mixin;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.config.ApocalypseLevel;
import jaggwagg.frozen_apocalypse.config.HeatBlock;
import jaggwagg.frozen_apocalypse.entity.effect.FrozenApocalypseStatusEffects;
import jaggwagg.frozen_apocalypse.item.ThermalArmorItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

@Mixin(LivingEntity.class)
public class LivingEntityFreezeMixin {
    @Unique
    private static final long HEAT_SOURCE_DELAY = 30L;
    @Unique
    private boolean nearHeatSource = true;
    @Unique
    private long checkNearHeatSourceDelay = HEAT_SOURCE_DELAY;

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovement(CallbackInfo ci) {
        LivingEntity livingEntity = ((LivingEntity) (Object) this);
        World world = livingEntity.getWorld();

        if (!FrozenApocalypse.CONFIG.FROZEN_APOCALYPSE_ENABLED) {
            this.nearHeatSource = true;
            return;
        }

        if (!world.getDimension().bedWorks()) {
            this.nearHeatSource = true;
            return;
        }

        if (livingEntity.hasStatusEffect(FrozenApocalypseStatusEffects.FROST_RESISTANCE)) {
            this.nearHeatSource = true;
            return;
        }

        if (ThermalArmorItem.wearingThermalArmor(livingEntity)) {
            this.nearHeatSource = true;
            return;
        }

        if (livingEntity instanceof PlayerEntity playerEntity) {
            if (playerEntity.isCreative() || playerEntity.isSpectator()) {
                this.nearHeatSource = true;
                return;
            }
        }

        if (livingEntity instanceof SkeletonEntity || livingEntity instanceof StrayEntity || livingEntity instanceof ZombieEntity) {
            this.nearHeatSource = true;
            return;
        }

        for (ApocalypseLevel frozenApocalypseLevel : FrozenApocalypse.CONFIG.FROZEN_APOCALYPSE_LEVELS) {
            if (frozenApocalypseLevel.APOCALYPSE_LEVEL == FrozenApocalypse.frozenApocalypseLevel) {
                if (!frozenApocalypseLevel.FREEZE_ENTITIES) {
                    this.nearHeatSource = true;
                    return;
                }

                if (livingEntity.getY() < frozenApocalypseLevel.FREEZING_Y_LEVEL) {
                    this.nearHeatSource = true;
                    return;
                }

                freezeLivingEntity(frozenApocalypseLevel.FREEZE_DAMAGE, frozenApocalypseLevel.FREEZE_DAMAGE_DELAY + 1, livingEntity, world);
                break;
            }
        }
    }

    @Unique
    private boolean checkNearHeatSource(World world, LivingEntity livingEntity) {
        BlockPos startingBlockPos = livingEntity.getBlockPos();
        int lightLevel = world.getLightLevel(LightType.BLOCK, startingBlockPos);
        HashSet<BlockPos> visitedBlockPositions = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();

        if (world.getLightLevel(LightType.BLOCK, startingBlockPos) < 1) {
            return false;
        }

        queue.add(startingBlockPos);

        while (!queue.isEmpty()) {
            BlockPos blockPos = queue.poll();

            if (world.getLightLevel(LightType.BLOCK, blockPos) < lightLevel) {
                continue;
            }

            if (visitedBlockPositions.contains(blockPos)) {
                continue;
            }

            for (HeatBlock heatBlock : FrozenApocalypse.CONFIG.HEAT_BLOCKS) {
                if (heatBlock.getBlock().equals(world.getBlockState(blockPos).getBlock())) {
                    return world.getLightLevel(LightType.BLOCK, startingBlockPos) >= world.getLightLevel(LightType.BLOCK, blockPos) - heatBlock.MAX_DISTANCE + 1;
                }
            }

            lightLevel += 1;
            visitedBlockPositions.add(blockPos);

            queue.add(new BlockPos(blockPos.getX() - 1, blockPos.getY(), blockPos.getZ()));
            queue.add(new BlockPos(blockPos.getX() + 1, blockPos.getY(), blockPos.getZ()));
            queue.add(new BlockPos(blockPos.getX(), blockPos.getY() - 1, blockPos.getZ()));
            queue.add(new BlockPos(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ()));
            queue.add(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ() - 1));
            queue.add(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ() + 1));
        }

        return false;
    }

    @Unique
    private void freezeLivingEntity(float damage, int random, LivingEntity livingEntity, World world) {
        if (!this.nearHeatSource) {
            if (!livingEntity.inPowderSnow) {
                livingEntity.setInPowderSnow(true);
            }

            if (world.getRandom().nextInt(random) == 1) {
                livingEntity.damage(world.getDamageSources().freeze(), damage);
            }

            if (!world.isClient) {
                livingEntity.setOnFire(false);
            }
        }

        this.checkNearHeatSourceDelay--;

        if (this.checkNearHeatSourceDelay != 0L) {
            return;
        }

        this.checkNearHeatSourceDelay = HEAT_SOURCE_DELAY;

        this.nearHeatSource = checkNearHeatSource(world, livingEntity);
    }
}
