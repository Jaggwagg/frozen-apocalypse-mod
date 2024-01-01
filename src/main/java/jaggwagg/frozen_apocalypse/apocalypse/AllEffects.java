package jaggwagg.frozen_apocalypse.apocalypse;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.config.AffectedDimension;
import net.minecraft.world.WorldAccess;

public class AllEffects {
    public static boolean isSafeDimension(WorldAccess world) {
        for (AffectedDimension affectedDimension : FrozenApocalypse.CONFIG.getAffectedDimensions()) {
            if (affectedDimension.getId().equals(world.getDimension().effects().toString())) {
                return false;
            }
        }

        return true;
    }
}
