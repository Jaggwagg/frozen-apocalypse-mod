package jaggwagg.frozen_apocalypse.mixin.entity.mob;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.apocalypse.SpawnEffects;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {
    @Inject(method = "canMobSpawn", at = @At("RETURN"), cancellable = true)
    private static void canMobSpawn(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        Set<EntityType<? extends MobEntity>> allowedEntities = new HashSet<>(List.of(
                EntityType.SKELETON, EntityType.ZOMBIE
        ));

        if (allowedEntities.contains(type)) {
            return;
        }

        if (SpawnEffects.canMobNotSpawn(world, spawnReason, pos)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "isAffectedByDaylight", at = @At("RETURN"), cancellable = true)
    private void isAffectedByDaylight(CallbackInfoReturnable<Boolean> cir) {
        if (FrozenApocalypse.CONFIG.isFrozenApocalypseEnabled()) {
            if (FrozenApocalypse.apocalypseLevel.canMobsSurviveDaylight()) {
                cir.setReturnValue(false);
            }
        }
    }
}
