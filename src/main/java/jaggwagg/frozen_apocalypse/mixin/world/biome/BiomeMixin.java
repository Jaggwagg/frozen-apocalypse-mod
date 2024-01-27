package jaggwagg.frozen_apocalypse.mixin.world.biome;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.apocalypse.AllEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public abstract class BiomeMixin {
    @Unique
    private boolean canSnow = false;

    @Inject(method = "canSetSnow", at = @At("HEAD"))
    public void canSetSnow(WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (world.isClient()) {
            return;
        }

        canSnow = !AllEffects.isSafeDimension((WorldAccess) world);
    }

    @Inject(method = "doesNotSnow", at = @At("RETURN"), cancellable = true)
    private void doesNotSnow(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (!FrozenApocalypse.CONFIG.isFrozenApocalypseEnabled()) {
            return;
        }

        if (!canSnow) {
            return;
        }

        if (FrozenApocalypse.apocalypseLevel.canAllBiomesSnow()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "hasPrecipitation", at = @At("RETURN"), cancellable = true)
    public void hasPrecipitation(CallbackInfoReturnable<Boolean> cir) {
        if (!FrozenApocalypse.CONFIG.isFrozenApocalypseEnabled()) {
            return;
        }

        if (!canSnow) {
            return;
        }

        if (FrozenApocalypse.apocalypseLevel.canAllBiomesSnow()) {
            cir.setReturnValue(true);
        }
    }
}
