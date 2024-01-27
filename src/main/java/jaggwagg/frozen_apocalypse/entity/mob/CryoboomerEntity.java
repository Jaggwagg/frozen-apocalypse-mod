package jaggwagg.frozen_apocalypse.entity.mob;

import net.minecraft.block.Blocks;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Collection;

public class CryoboomerEntity extends CreeperEntity {
    private static final TrackedData<Integer> FUSE_SPEED = DataTracker.registerData(CryoboomerEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> CHARGED = DataTracker.registerData(CryoboomerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> IGNITED = DataTracker.registerData(CryoboomerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private int currentFuseTime;
    @SuppressWarnings("all")
    private int lastFuseTime;

    public CryoboomerEntity(EntityType<? extends CryoboomerEntity> entityType, World world) {
        super(entityType, world);
    }

    private void spawnEffectsCloud() {
        Collection<StatusEffectInstance> collection = this.getStatusEffects();
        AreaEffectCloudEntity slownessAreaEffectCloudEntity = createAreaEffectCloudEntity(7.0f);

        slownessAreaEffectCloudEntity.addEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 1));
        this.getWorld().spawnEntity(slownessAreaEffectCloudEntity);

        if (!collection.isEmpty()) {
            AreaEffectCloudEntity areaEffectCloudEntity = createAreaEffectCloudEntity(2.5f);

            for (StatusEffectInstance statusEffectInstance : collection) {
                areaEffectCloudEntity.addEffect(new StatusEffectInstance(statusEffectInstance));
            }

            this.getWorld().spawnEntity(areaEffectCloudEntity);
        }
    }

    private AreaEffectCloudEntity createAreaEffectCloudEntity(float radius) {
        AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.getWorld(), this.getX(), this.getY(), this.getZ());
        areaEffectCloudEntity.setRadius(radius);
        areaEffectCloudEntity.setRadiusOnUse(-0.5f);
        areaEffectCloudEntity.setWaitTime(10);
        areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration() / 2);
        areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float) areaEffectCloudEntity.getDuration());
        return areaEffectCloudEntity;
    }

    private void placePowderedSnow(BlockPos blockPos) {
        World world = this.getWorld();
        int radius = 10;
        int center = (int) (radius * 0.5);
        int squareDistance; 

        if (!world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            return;
        }

        for (int x = center - radius; x < radius - center; x++) {
            for (int y = center - radius; y < radius - center; y++) {
                for (int z = center - radius; z < radius - center; z++) {
                    squareDistance = x * x + y * y + z * z;

                    if (squareDistance < (radius - center) * (radius - center)) {
                        BlockPos placingBlockPos = new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);

                        if (!world.getBlockState(placingBlockPos).isAir()) {
                            if ((world.getBlockState(placingBlockPos.up()).isAir() || world.getBlockState(placingBlockPos.up()).isOf(Blocks.SNOW))) {
                                if (!world.getBlockState(placingBlockPos).isOf(Blocks.POWDER_SNOW)) {
                                    world.setBlockState(placingBlockPos.up(), Blocks.POWDER_SNOW.getDefaultState());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void explode() {
        if (!this.getWorld().isClient) {
            float charged = this.shouldRenderOverlay() ? 2.0F : 1.0F;
            this.dead = true;
            int explosionRadius = 3;
            this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), (float) explosionRadius * charged, World.ExplosionSourceType.MOB);
            this.discard();
            this.spawnEffectsCloud();
            this.placePowderedSnow(this.getBlockPos());
        }
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FUSE_SPEED, -1);
        this.dataTracker.startTracking(CHARGED, false);
        this.dataTracker.startTracking(IGNITED, false);
    }

    @Override
    public void tick() {
        if (this.isAlive()) {
            this.lastFuseTime = this.currentFuseTime;
            if (this.isIgnited()) {
                this.setFuseSpeed(1);
            }

            int i = this.getFuseSpeed();
            if (i > 0 && this.currentFuseTime == 0) {
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
                this.emitGameEvent(GameEvent.PRIME_FUSE);
            }

            this.currentFuseTime += i;
            if (this.currentFuseTime < 0) {
                this.currentFuseTime = 0;
            }

            int fuseTime = 30;
            if (this.currentFuseTime >= fuseTime) {
                this.currentFuseTime = fuseTime;
                this.explode();
            }
        }

        super.tick();
    }
}
