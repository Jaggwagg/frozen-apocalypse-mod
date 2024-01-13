package jaggwagg.frozen_apocalypse.apocalypse;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.config.AffectedDimension;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.WorldAccess;

public class AllEffects {
    public static boolean isSafeDimension(WorldAccess worldAccess) {
        if (worldAccess.getServer() != null) {
            for (AffectedDimension affectedDimension : FrozenApocalypse.CONFIG.getAffectedDimensions()) {
                ServerWorld serverWorld = worldAccess.getServer().getWorld(RegistryKey.of(RegistryKeys.WORLD, new Identifier(affectedDimension.getId())));

                if (serverWorld != null) {
                    String worldId = serverWorld.getRegistryKey().getValue().toString();

                    if (affectedDimension.getId().equals(worldId)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
