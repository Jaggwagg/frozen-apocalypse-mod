package jaggwagg.frozen_apocalypse.network;

import jaggwagg.frozen_apocalypse.FrozenApocalypseClient;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class FrozenApocalypseClientRecievers {
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(FrozenApocalypseNetwork.FROZEN_APOCALYPSE_LEVEL_ID, (client, handler, buf, responseSender) -> {
            int apocalypseLevel = buf.readVarInt();

            client.execute(() -> FrozenApocalypseClient.frozenApocalypseLevelClient = apocalypseLevel);
        });
    }
}
