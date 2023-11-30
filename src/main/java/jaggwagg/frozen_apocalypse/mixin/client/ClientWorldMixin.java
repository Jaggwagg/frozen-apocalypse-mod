package jaggwagg.frozen_apocalypse.mixin.client;

import jaggwagg.frozen_apocalypse.client.FrozenApocalypseClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Environment(EnvType.CLIENT)
@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    @Unique
    private static int calculateDay(World world) {
        return (int) Math.floor(world.getTimeOfDay() / 24000.0f);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        ClientWorld clientWorld = ((ClientWorld) (Object) this);
        FrozenApocalypseClient.frozenApocalypseLevelClient = calculateDay(clientWorld);
    }
}
