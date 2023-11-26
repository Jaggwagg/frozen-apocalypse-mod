package jaggwagg.frozen_apocalypse.mixin;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.entity.effect.FrozenApocalypseStatusEffects;
import jaggwagg.frozen_apocalypse.item.FrozenApocalypseItems;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.StrayEntity;
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
        LivingEntity livingEntity = ((LivingEntity)(Object)this);
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
                return;
            }
        }

        if (apocalypseLevel == 1) {
            freezeLivingEntity(150, 7, 1.0f, 32, livingEntity, world);
        } else if (apocalypseLevel == 2) {
            freezeLivingEntity(100, 7, 1.0f, 32, livingEntity, world);
        } else if (apocalypseLevel == 3) {
            freezeLivingEntity(60, 5, 1.0f, 32, livingEntity, world);
        } else if (apocalypseLevel == 4) {
            freezeLivingEntity(50, 5, 2.0f, 32, livingEntity, world);
        } else if (apocalypseLevel == 5) {
            freezeLivingEntity(40, 3, 2.0f, 16, livingEntity, world);
        } else if (apocalypseLevel > 5) {
            freezeLivingEntity(30, 3, 2.0f, 16, livingEntity, world);
        }
    }

    @Unique
    public boolean notNearHeatSource(int size, World world, LivingEntity livingEntity) {
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
    public void freezeLivingEntity(int aboveY, int heatSize, float damage, int random, LivingEntity livingEntity, World world) {
        if (livingEntity.getY() > aboveY) {
            if (notNearHeatSource(heatSize, world, livingEntity)) {
                if (livingEntity instanceof PlayerEntity playerEntity) {
                    if (playerEntity.isCreative() || playerEntity.isSpectator()) {
                        return;
                    }
                }

                if (livingEntity instanceof SkeletonEntity || livingEntity instanceof StrayEntity) {
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
