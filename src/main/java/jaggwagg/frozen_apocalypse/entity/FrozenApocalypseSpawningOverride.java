package jaggwagg.frozen_apocalypse.entity;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.config.ApocalypseLevel;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Unique;

public class FrozenApocalypseSpawningOverride {
    @Unique
    public static boolean canMobNotSpawn(WorldAccess world, SpawnReason spawnReason, BlockPos pos) {
        if (!world.getDimension().bedWorks()) {
            return false;
        }

        if (spawnReason.equals(SpawnReason.NATURAL) || spawnReason.equals(SpawnReason.CHUNK_GENERATION)) {
            for (ApocalypseLevel apocalypseLevel : FrozenApocalypse.CONFIG.FROZEN_APOCALYPSE_LEVELS) {
                if (apocalypseLevel.APOCALYPSE_LEVEL == FrozenApocalypse.frozenApocalypseLevel) {
                    if (apocalypseLevel.FREEZE_ENTITIES) {
                        return pos.getY() > apocalypseLevel.FREEZING_Y_LEVEL;
                    }
                }
            }
        }

        return false;
    }
}
