package jaggwagg.frozen_apocalypse.client;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.client.network.FrozenApocalypseClientRecievers;
import jaggwagg.frozen_apocalypse.client.render.block.ModBlockRenderLayerMaps;
import net.fabricmc.api.ClientModInitializer;

public class FrozenApocalypseClient implements ClientModInitializer {
    public static int frozenApocalypseLevelClient;

    @Override
    public void onInitializeClient() {
        FrozenApocalypseClientRecievers.init();
        ModBlockRenderLayerMaps.init();

        FrozenApocalypse.LOGGER.info(FrozenApocalypse.MOD_ID + ": Initialized client successfully");
    }
}
