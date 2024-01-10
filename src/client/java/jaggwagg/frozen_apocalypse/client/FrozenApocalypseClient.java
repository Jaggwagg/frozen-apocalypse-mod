package jaggwagg.frozen_apocalypse.client;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.client.network.ModClientRecievers;
import jaggwagg.frozen_apocalypse.client.registry.ModBlockRenderLayerMaps;
import jaggwagg.frozen_apocalypse.client.registry.ModEntityRenderers;
import net.fabricmc.api.ClientModInitializer;

public class FrozenApocalypseClient implements ClientModInitializer {
    public static int frozenApocalypseLevelClient;

    @Override
    public void onInitializeClient() {
        ModBlockRenderLayerMaps.init();
        ModClientRecievers.init();
        ModEntityRenderers.init();

        FrozenApocalypse.LOGGER.info(FrozenApocalypse.MOD_ID + ": Initialized client successfully");
    }
}
