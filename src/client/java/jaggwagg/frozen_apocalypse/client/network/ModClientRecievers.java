package jaggwagg.frozen_apocalypse.client.network;

import jaggwagg.frozen_apocalypse.client.FrozenApocalypseClient;
import jaggwagg.frozen_apocalypse.network.ModNetwork;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ModClientRecievers {
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(ModNetwork.FROZEN_APOCALYPSE_LEVEL_ID, (client, handler, buf, responseSender) -> {
            int apocalypseLevel = buf.readVarInt();

            client.execute(() -> FrozenApocalypseClient.frozenApocalypseLevelClient = apocalypseLevel);
        });
    }
}
