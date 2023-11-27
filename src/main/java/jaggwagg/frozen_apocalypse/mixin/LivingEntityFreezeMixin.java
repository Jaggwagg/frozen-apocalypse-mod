package jaggwagg.frozen_apocalypse.mixin;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.entity.effect.FrozenApocalypseStatusEffects;
import jaggwagg.frozen_apocalypse.item.FrozenApocalypseItems;
import jaggwagg.frozen_apocalypse.item.ThermalArmorItem;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;

@Mixin(LivingEntity.class)
public abstract class LivingEntityFreezeMixin {
    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovement(CallbackInfo ci) {
        LivingEntity livingEntity = ((LivingEntity) (Object) this);
        World world = livingEntity.getWorld();
        int apocalypseLevel = world.getGameRules().getInt(FrozenApocalypse.FROZEN_APOCALYPSE_LEVEL);

        if (!FrozenApocalypse.CONFIG.getFrozenApocalypseEnabled()) {
            return;
        }

        if (!world.getDimension().bedWorks()) {
            return;
        }

        if (livingEntity.hasStatusEffect(FrozenApocalypseStatusEffects.FROST_RESISTANCE)) {
            return;
        }

        if (livingEntity instanceof PlayerEntity playerEntity) {
            if (ThermalArmorItem.wearingThermalArmor(playerEntity)) {
                return;
            }
        }

        if (apocalypseLevel == 1) {
            freezeLivingEntity(150, 7, 1.0f, 32, livingEntity, world);
        } else if (apocalypseLevel == 2) {
            freezeLivingEntity(112, 7, 1.0f, 32, livingEntity, world);
        } else if (apocalypseLevel == 3) {
            freezeLivingEntity(84, 5, 1.0f, 32, livingEntity, world);
        } else if (apocalypseLevel == 4) {
            freezeLivingEntity(62, 5, 1.0f, 32, livingEntity, world);
        } else if (apocalypseLevel == 5) {
            freezeLivingEntity(45, 5, 2.0f, 16, livingEntity, world);
        } else if (apocalypseLevel == 6) {
            freezeLivingEntity(30, 3, 2.0f, 16, livingEntity, world);
        } else if (apocalypseLevel > 6) {
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
                    HashSet<Block> blocks = new HashSet<>();

                    FrozenApocalypse.CONFIG.getHeatBlocks().forEach(value -> blocks.add(Registries.BLOCK.get(new Identifier(value))));

                    if (blocks.contains(world.getBlockState(blockPos).getBlock())) {
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
