package jaggwagg.frozen_apocalypse.registry;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.event.FrostResistanceAfterDeathEvent;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;

import java.util.Arrays;

public class ModEvents {
    public static void init() {
        Arrays.stream(RegisteredServerPlayerAfterRespawnEvents.values()).forEach(value -> ServerPlayerEvents.AFTER_RESPAWN.register(value.getEvent()));

        FrozenApocalypse.loggerInfo("Initialized events");
    }

    public enum RegisteredServerPlayerAfterRespawnEvents {
        FROST_RESISTANCE_AFTER_DEATH(new FrostResistanceAfterDeathEvent());

        private final ServerPlayerEvents.AfterRespawn event;

        RegisteredServerPlayerAfterRespawnEvents(ServerPlayerEvents.AfterRespawn event) {
            this.event = event;
        }

        public ServerPlayerEvents.AfterRespawn getEvent() {
            return this.event;
        }
    }
}
