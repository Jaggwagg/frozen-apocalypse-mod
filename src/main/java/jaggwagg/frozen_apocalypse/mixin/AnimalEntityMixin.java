package jaggwagg.frozen_apocalypse.mixin;

import jaggwagg.frozen_apocalypse.entity.FrozenApocalypseSpawningOverride;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnimalEntity.class)
public abstract class AnimalEntityMixin {
    @Inject(method = "isValidNaturalSpawn", at = @At("RETURN"), cancellable = true)
    private static void isValidNaturalSpawn(EntityType<? extends AnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        if (FrozenApocalypseSpawningOverride.canMobNotSpawn(world, spawnReason, pos)) {
            cir.setReturnValue(false);
        }
    }
}
