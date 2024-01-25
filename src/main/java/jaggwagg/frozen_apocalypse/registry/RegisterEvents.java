package jaggwagg.frozen_apocalypse.registry;

import jaggwagg.frozen_apocalypse.event.ModServerPlayerAfterRespawnEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;

import java.util.Arrays;

public class RegisterEvents {
    public static void init() {
        Arrays.stream(ModServerPlayerAfterRespawnEvents.values()).forEach(value -> ServerPlayerEvents.AFTER_RESPAWN.register(value.getEvent()));
    }
}
