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

        if (world.getGameRules().getInt(FrozenApocalypse.FROZEN_APOCALYPSE_LEVEL) > 1) {
            if (livingEntity.getY() > 100) {
                if (notNearHeatSource(5, world, livingEntity)) {
                    freezeLivingEntity(world, livingEntity);
                }
            }
        }

        if (world.getGameRules().getInt(FrozenApocalypse.FROZEN_APOCALYPSE_LEVEL) > 2) {
            if (livingEntity.getY() > 80) {
                if (notNearHeatSource(5, world, livingEntity)) {
                    freezeLivingEntity(world, livingEntity);
                }
            }
        }

        if (world.getGameRules().getInt(FrozenApocalypse.FROZEN_APOCALYPSE_LEVEL) > 3) {
            if (livingEntity.getY() > 60) {
                if (notNearHeatSource(4, world, livingEntity)) {
                    freezeLivingEntity(world, livingEntity);
                }
            }
        }

        if (world.getGameRules().getInt(FrozenApocalypse.FROZEN_APOCALYPSE_LEVEL) > 4) {
            if (livingEntity.getY() > 50) {
                if (notNearHeatSource(3, world, livingEntity)) {
                    freezeLivingEntity(world, livingEntity);
                }
            }
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
            livingEntity.damage(world.getDamageSources().freeze(), 1.0f);
        }

        livingEntity.setInPowderSnow(true);

        if (!world.isClient) {
            livingEntity.setOnFire(false);
        }
    }
}
