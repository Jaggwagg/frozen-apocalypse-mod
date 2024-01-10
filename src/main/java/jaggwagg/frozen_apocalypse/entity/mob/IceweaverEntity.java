package jaggwagg.frozen_apocalypse.entity.mob;

import jaggwagg.frozen_apocalypse.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class IceweaverEntity extends SpiderEntity {
    public IceweaverEntity(EntityType<? extends IceweaverEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean attacked = super.tryAttack(target);

        if (attacked && this.getMainHandStack().isEmpty() && target instanceof LivingEntity) {
            BlockState targetBlockState = target.getWorld().getBlockState(target.getBlockPos());
            int duration = (int) this.getWorld().getLocalDifficulty(this.getBlockPos()).getLocalDifficulty();
            ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 140 * duration), this);

            if (this.getWorld().getRandom().nextInt(8) == 0) {
                if (targetBlockState.isAir() || targetBlockState.isOf(Blocks.SNOW)) {
                    target.getWorld().setBlockState(target.getBlockPos(), ModBlocks.RegisteredBlocks.ICY_COBWEB.getBlock().getDefaultState());
                }
            }
        }

        return attacked;
    }

    @Override
    public void slowMovement(BlockState state, Vec3d multiplier) {
        if (!state.isOf(Blocks.COBWEB) || !state.isOf(ModBlocks.RegisteredBlocks.ICY_COBWEB.getBlock())) {
            super.slowMovement(state, multiplier);
        }
    }
}
