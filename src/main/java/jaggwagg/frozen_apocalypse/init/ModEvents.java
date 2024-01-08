package jaggwagg.frozen_apocalypse.init;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.event.FrostResistanceAfterDeath;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;

import java.util.Arrays;
import java.util.Locale;
import java.util.StringJoiner;

public class ModEvents {
    public static void init() {
        StringJoiner joiner = new StringJoiner(", ");

        Arrays.stream(RegisteredServerPlayerAfterRespawnEvents.values()).forEach(value -> {
            ServerPlayerEvents.AFTER_RESPAWN.register(value.getEvent());
            joiner.add(value.getName());
        });

        FrozenApocalypse.LOGGER.info(FrozenApocalypse.MOD_ID + ": Initialized events: " + joiner);
    }

    public enum RegisteredServerPlayerAfterRespawnEvents {
        FROST_RESISTANCE_AFTER_DEATH(new FrostResistanceAfterDeath());

        private final String name;
        private final ServerPlayerEvents.AfterRespawn event;

        RegisteredServerPlayerAfterRespawnEvents(ServerPlayerEvents.AfterRespawn event) {
            this.name = this.toString().toLowerCase(Locale.ROOT);
            this.event = event;
        }

        public String getName() {
            return this.name;
        }

        public ServerPlayerEvents.AfterRespawn getEvent() {
            return this.event;
        }
    }
}
