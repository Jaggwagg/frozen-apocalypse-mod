package jaggwagg.frozen_apocalypse;

import net.fabricmc.api.ClientModInitializer;

public class FrozenApocalypseClient implements ClientModInitializer {
    public static int frozenApocalypseLevelClient = 0;

    @Override
    public void onInitializeClient() {
        FrozenApocalypse.LOGGER.info(FrozenApocalypse.MOD_ID + ": successfully initialized client");
    }
}
