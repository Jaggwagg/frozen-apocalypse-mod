package jaggwagg.frozen_apocalypse.client;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import net.fabricmc.api.ClientModInitializer;

public class FrozenApocalypseClient implements ClientModInitializer {
    public static int frozenApocalypseLevelClient = 0;

    @Override
    public void onInitializeClient() {
        FrozenApocalypse.LOGGER.info("Successfully initialized client!");
    }
}
