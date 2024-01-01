package jaggwagg.frozen_apocalypse.mixin.server.world;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.apocalypse.WorldEffects;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Heightmap;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Inject(method = "tickChunk", at = @At("TAIL"))
    private void tickChunk(WorldChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        ServerWorld serverWorld = (ServerWorld) (Object) this;

        if (WorldEffects.shouldSkipTick(serverWorld)) {
            return;
        }

        WorldEffects.initializeFrozenApocalypseLevel(serverWorld);
        WorldEffects.updateFrozenApocalypseLevel(serverWorld);

        if (FrozenApocalypse.CONFIG.isSunSizeChangesEnabled()) {
            WorldEffects.sendFrozenApocalypseLevelToPlayers(serverWorld);
        }

        int updateSpeed = WorldEffects.calculateUpdateSpeed(serverWorld);

        if (updateSpeed < 1 || serverWorld.getRandom().nextInt(updateSpeed) > 1) {
            return;
        }

        WorldEffects.applyApocalypseEffects(serverWorld, serverWorld.getTopPosition(Heightmap.Type.MOTION_BLOCKING, serverWorld.getRandomPosInChunk(chunk.getPos().getStartX(), 0, chunk.getPos().getStartZ(), 15)));
    }
}
