package jaggwagg.frozen_apocalypse.apocalypse;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.config.FreezingImmuneEntity;
import jaggwagg.frozen_apocalypse.item.ThermalArmorItem;
import jaggwagg.frozen_apocalypse.registry.FrozenApocalypseStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class LivingEntityEffects {
    public static boolean isNearHeatSource(World world, LivingEntity livingEntity) {
        int livingEntityBlockPosLightLevel = world.getLightLevel(LightType.BLOCK, livingEntity.getBlockPos());

        return livingEntityBlockPosLightLevel > FrozenApocalypse.apocalypseLevel.getApocalypseLevel();
    }

    public static boolean shouldSkipFreezingChecks(World world, LivingEntity livingEntity) {
        return AllEffects.isSafeDimension(world) ||
                isImmuneToFreezing(livingEntity) ||
                isFreezingImmuneEntity(livingEntity) ||
                isNearHeatSource(world, livingEntity);
    }

    public static boolean isImmuneToFreezing(LivingEntity livingEntity) {
        return !FrozenApocalypse.CONFIG.isFrozenApocalypseEnabled() ||
                livingEntity.hasStatusEffect(FrozenApocalypseStatusEffects.StatusEffects.FROST_RESISTANCE.getStatusEffect()) ||
                isCreativeOrSpectator(livingEntity) ||
                isWearingThermalArmor(livingEntity);
    }

    public static boolean isWearingThermalArmor(LivingEntity livingEntity) {
        return ThermalArmorItem.wearingThermalArmor(livingEntity);
    }

    public static boolean isCreativeOrSpectator(LivingEntity livingEntity) {
        return livingEntity instanceof PlayerEntity playerEntity &&
                (playerEntity.isCreative() || playerEntity.isSpectator());
    }

    public static boolean isFreezingImmuneEntity(LivingEntity livingEntity) {
        String livingEntityId = String.valueOf(Registries.ENTITY_TYPE.getId(livingEntity.getType()));

        for (FreezingImmuneEntity freezingImmuneEntity : FrozenApocalypse.CONFIG.getFreezingImmuneEntities()) {
            if (livingEntityId.equals(freezingImmuneEntity.getId())) {
                return true;
            }
        }

        return false;
    }
}
