package jaggwagg.frozen_apocalypse.entity;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
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

        if (!spawnReason.equals(SpawnReason.NATURAL)) {
            return false;
        }

        return switch (FrozenApocalypse.frozenApocalypseLevel) {
            case 0 -> false;
            case 1 -> shouldNotSpawn(pos.getY(), 150);
            case 2 -> shouldNotSpawn(pos.getY(), 112);
            case 3 -> shouldNotSpawn(pos.getY(), 84);
            case 4 -> shouldNotSpawn(pos.getY(), 62);
            case 5 -> shouldNotSpawn(pos.getY(), 45);
            case 6 -> shouldNotSpawn(pos.getY(), 30);
            default -> shouldNotSpawn(pos.getY(), 20);
        };
    }

    @Unique
    public static boolean shouldNotSpawn(int posY, int yLevelFreezingPoint) {
        return posY > yLevelFreezingPoint;
    }
}
