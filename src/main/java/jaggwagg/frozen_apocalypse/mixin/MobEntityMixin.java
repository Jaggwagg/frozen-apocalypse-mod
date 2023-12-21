package jaggwagg.frozen_apocalypse.mixin;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.entity.FrozenApocalypseSpawningOverride;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {
    @Inject(method = "isAffectedByDaylight", at = @At("HEAD"), cancellable = true)
    private void isAffectedByDaylight(CallbackInfoReturnable<Boolean> cir) {
        if (!FrozenApocalypse.FROZEN_APOCALYPSE_ENABLED) {
            return;
        }

        if (FrozenApocalypse.frozenApocalypseLevel > 1) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "canMobSpawn", at = @At("RETURN"), cancellable = true)
    private static void canMobSpawn(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        if (type.equals(EntityType.ZOMBIE) || type.equals(EntityType.SKELETON) || type.equals(EntityType.STRAY)) {
            return;
        }

        if (FrozenApocalypseSpawningOverride.canMobNotSpawn(world, spawnReason, pos)) {
            cir.setReturnValue(false);
        }
    }
}
