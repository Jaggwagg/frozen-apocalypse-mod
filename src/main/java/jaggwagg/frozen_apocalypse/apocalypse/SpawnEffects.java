package jaggwagg.frozen_apocalypse.apocalypse;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.config.ApocalypseLevel;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class SpawnEffects {
    public static boolean canMobNotSpawn(WorldAccess world, SpawnReason spawnReason, BlockPos pos) {
        if (!FrozenApocalypse.CONFIG.isFrozenApocalypseEnabled() || AllEffects.isSafeDimension(world)) {
            return false;
        }

        if (spawnReason.equals(SpawnReason.NATURAL) || spawnReason.equals(SpawnReason.CHUNK_GENERATION)) {
            if (FrozenApocalypse.apocalypseLevel.canFreezeEntities()) {
                return pos.getY() > FrozenApocalypse.apocalypseLevel.getFreezingYLevel();
            }
        }

        return false;
    }
}
