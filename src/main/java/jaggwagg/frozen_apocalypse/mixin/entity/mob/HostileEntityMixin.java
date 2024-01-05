package jaggwagg.frozen_apocalypse.mixin.entity.mob;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HostileEntity.class)
public abstract class HostileEntityMixin {
    @Inject(method = "isSpawnDark", at = @At("RETURN"), cancellable = true)
    private static void isSpawnDark(ServerWorldAccess world, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        if (FrozenApocalypse.apocalypseLevel.canMobsSpawnDaylight()) {
            if (world.getLightLevel(LightType.SKY, pos) > 0 && world.getLightLevel(LightType.BLOCK, pos) <= world.getDimension().monsterSpawnLightTest().getMax()) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "getPathfindingFavor", at = @At("RETURN"), cancellable = true)
    private void getPathfindingFavor(BlockPos pos, WorldView world, CallbackInfoReturnable<Float> cir) {
        if (FrozenApocalypse.apocalypseLevel.canMobsSurviveDaylight()) {
            if (world.getLightLevel(LightType.SKY, pos) > 0) {
                cir.setReturnValue(1.0f);
            }
        }
    }
}
