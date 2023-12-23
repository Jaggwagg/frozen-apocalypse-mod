package jaggwagg.frozen_apocalypse.mixin;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.config.ApocalypseLevel;
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
        for (ApocalypseLevel apocalypseLevel : FrozenApocalypse.CONFIG.FROZEN_APOCALYPSE_LEVELS) {
            if (apocalypseLevel.APOCALYPSE_LEVEL == FrozenApocalypse.frozenApocalypseLevel) {
                if (apocalypseLevel.ALL_BIOMES_CAN_SNOW) {
                    cir.setReturnValue(false);
                }

                break;
            }
        }
    }
}
