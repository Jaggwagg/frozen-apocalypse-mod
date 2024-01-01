package jaggwagg.frozen_apocalypse.mixin.client.world;

import jaggwagg.frozen_apocalypse.FrozenApocalypseClient;
import jaggwagg.frozen_apocalypse.network.FrozenApocalypseNetwork;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Environment(EnvType.CLIENT)
@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        ClientPlayNetworking.registerGlobalReceiver(FrozenApocalypseNetwork.FROZEN_APOCALYPSE_LEVEL_ID, (client, handler, buf, responseSender) -> {
            int apocalypseLevel = buf.readVarInt();

            client.execute(() -> FrozenApocalypseClient.frozenApocalypseLevelClient = apocalypseLevel);
        });
    }
}
