package jaggwagg.frozen_apocalypse.mixin.client;

import jaggwagg.frozen_apocalypse.client.FrozenApocalypseClient;
import jaggwagg.frozen_apocalypse.network.FrozenApocalypseNetworking;
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
        ClientPlayNetworking.registerGlobalReceiver(FrozenApocalypseNetworking.FROZEN_APOCALYPSE_LEVEL_ID, (client, handler, buf, responseSender) -> {
            int apocalypseLevel = buf.readInt();

            client.execute(() -> FrozenApocalypseClient.frozenApocalypseLevelClient = apocalypseLevel);
        });
    }
}
