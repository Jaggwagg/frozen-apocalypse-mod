package jaggwagg.frozen_apocalypse.entity.mob;

import jaggwagg.frozen_apocalypse.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class IceweaverEntity extends SpiderEntity implements SlownessAfflicting {
    public IceweaverEntity(EntityType<? extends IceweaverEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean attacked = super.tryAttack(target);

        if (attacked && this.getMainHandStack().isEmpty() && target instanceof LivingEntity) {
            BlockPos targetBlockPos = target.getBlockPos();
            BlockState icyCobwebDefaultState = ModBlocks.RegisteredBlocks.ICY_COBWEB.getBlock().getDefaultState();

            if (this.getWorld().getRandom().nextInt(4) == 0) {
                if (icyCobwebDefaultState.canPlaceAt(target.getWorld(), targetBlockPos)) {
                    target.getWorld().setBlockState(target.getBlockPos(), icyCobwebDefaultState);
                }
            }

            this.inflictSlowness(this, (LivingEntity) target);
        }

        return attacked;
    }

    @Override
    public void slowMovement(BlockState state, Vec3d multiplier) {
        if (state.isOf(Blocks.COBWEB) || state.isOf(ModBlocks.RegisteredBlocks.ICY_COBWEB.getBlock())) {
            return;
        }

        super.slowMovement(state, multiplier);
    }
}
