package jaggwagg.frozen_apocalypse.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;

public enum ModServerPlayerAfterRespawnEvents {
    FROST_RESISTANCE_AFTER_DEATH(new FrostResistanceAfterDeathEvent());

    private final ServerPlayerEvents.AfterRespawn event;

    ModServerPlayerAfterRespawnEvents(ServerPlayerEvents.AfterRespawn event) {
        this.event = event;
    }

    public ServerPlayerEvents.AfterRespawn getEvent() {
        return this.event;
    }
}
