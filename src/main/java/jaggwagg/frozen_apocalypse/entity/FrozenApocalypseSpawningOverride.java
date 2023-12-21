package jaggwagg.frozen_apocalypse.entity;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.config.FrozenApocalypseLevel;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;

public class FrozenApocalypseSpawningOverride {
    @Unique
    public static boolean canMobNotSpawn(WorldAccess world, SpawnReason spawnReason, BlockPos pos) {
        if (!world.getDimension().bedWorks()) {
            return false;
        }

        if (!spawnReason.equals(SpawnReason.NATURAL)) {
            return false;
        }

        for (FrozenApocalypseLevel frozenApocalypseLevel : FrozenApocalypse.CONFIG.FROZEN_APOCALYPSE_LEVELS) {
            if (frozenApocalypseLevel.APOCALYPSE_LEVEL == FrozenApocalypse.frozenApocalypseLevel) {
                return shouldNotSpawn(pos.getY(), frozenApocalypseLevel.FREEZING_Y_LEVEL);
            }
        }

        return false;
    }

    @Unique
    public static boolean shouldNotSpawn(int posY, int yLevelFreezingPoint) {
        return posY > yLevelFreezingPoint;
    }
}
