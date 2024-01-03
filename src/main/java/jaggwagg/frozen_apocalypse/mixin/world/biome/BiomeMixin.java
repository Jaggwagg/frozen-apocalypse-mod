package jaggwagg.frozen_apocalypse.mixin.world.biome;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public class BiomeMixin {
    @Inject(method = "doesNotSnow", at = @At("RETURN"), cancellable = true)
    private void doesNotSnow(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (FrozenApocalypse.apocalypseLevel.canAllBiomesSnow()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "hasPrecipitation", at = @At("RETURN"), cancellable = true)
    public void hasPrecipitation(CallbackInfoReturnable<Boolean> cir) {
        if (FrozenApocalypse.apocalypseLevel.canAllBiomesSnow()) {
            cir.setReturnValue(true);
        }
    }
}
