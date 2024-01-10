package jaggwagg.frozen_apocalypse.mixin.entity.mob;

import jaggwagg.frozen_apocalypse.FrozenApocalypse;
import jaggwagg.frozen_apocalypse.registry.ModEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovement(CallbackInfo ci) {
        HostileEntity hostileEntity = (HostileEntity) (Object) this;

        if (!FrozenApocalypse.CONFIG.isConvertingMobsEnabled()) {
            return;
        }

        if (hostileEntity.getWorld().isClient || !hostileEntity.isAlive() || hostileEntity.isAiDisabled()) {
            return;
        }

        if (!FrozenApocalypse.apocalypseLevel.canFreezeEntities() || hostileEntity.getBlockPos().getY() < FrozenApocalypse.apocalypseLevel.getFreezingYLevel()) {
            return;
        }

        if (hostileEntity.getType().equals(EntityType.CREEPER)) {
            hostileEntity.convertTo(ModEntityTypes.RegisteredMobEntityTypes.CRYOBOOMER.getEntityType(), true);
        } else if (hostileEntity.getType().equals(EntityType.ENDERMAN)) {
            hostileEntity.convertTo(ModEntityTypes.RegisteredMobEntityTypes.SHIVERSTARE.getEntityType(), true);
        } else if (hostileEntity.getType().equals(EntityType.SKELETON)) {
            hostileEntity.convertTo(EntityType.STRAY, true);
        } else if (hostileEntity.getType().equals(EntityType.SPIDER)) {
            hostileEntity.convertTo(ModEntityTypes.RegisteredMobEntityTypes.ICEWEAVER.getEntityType(), true);
        } else if (hostileEntity.getType().equals(EntityType.ZOMBIE)) {
            hostileEntity.convertTo(ModEntityTypes.RegisteredMobEntityTypes.FROSTBITE.getEntityType(), true);
        }
    }
}
